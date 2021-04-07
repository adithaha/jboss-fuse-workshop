
# prepare maven
```
gsutil cp fuse-soap-1.0.0-SNAPSHOT.jar gs://nugraha-51412-gs/maven/
gsutil cp fuse-rest-1.0.0-SNAPSHOT.jar gs://nugraha-51412-gs/maven/
```
# create network
```
gcloud compute networks create devnet --subnet-mode=custom
gcloud compute networks subnets create subnet-us --network=devnet --region=us-central1 --range=10.130.1.0/24
gcloud compute networks subnets create subnet-eu --network=devnet --region=europe-west4 --range=10.130.2.0/24

gcloud compute networks list
```
# create firewall
```
gcloud compute firewall-rules create dev-allow-icmp-ssh-rdp --direction=INGRESS --priority=1000 --network=devnet --action=ALLOW --rules=icmp,tcp:22,tcp:3389 --source-ranges=0.0.0.0/0
gcloud compute firewall-rules list --sort-by=NETWORK
```
# create CloudSQL
Create Cloud SQL
```
gcloud sql instances create cameld4 --database-version=POSTGRES_9_6 --cpu=2 --memory=4 --storage-type=SSD --storage-size=10gb --network=devnet --availability-type=zonal --zone=us-central1-b
```
Create csql proxy
```
gcloud compute instances create cameldb-proxy --machine-type e2-medium --zone us-central1-a --network devnet --subnet subnet-us
sudo apt-get update
sudo apt install wget
wget https://dl.google.com/cloudsql/cloud_sql_proxy.linux.amd64 -O cloud_sql_proxy
chmod +x cloud_sql_proxy
```
Start CSQL Proxy
```
./cloud_sql_proxy -instances=nugraha-51412:us-central1:cameldb=tcp:0.0.0.0:1234 -ip_address_types=PRIVATE &
```
startup-script
```
#! /bin/bash
cd /home/nugraha
sudo -u nugraha ./cloud_sql_proxy -instances=nugraha-51412:us-central1:cameldb=tcp:0.0.0.0:1234 -ip_address_types=PRIVATE &
EOF
```

# create vm fuse-soap
```
gcloud compute instances create fuse-soap --machine-type e2-medium --zone us-central1-a --network devnet --subnet subnet-us
gcloud compute ssh fuse-soap --zone us-central1-a
sudo apt-get update
sudo apt install default-jdk
mkdir fuse-soap
cd fuse-soap
gsutil cp gs://nugraha-51412-gs/maven/fuse-soap-1.0.0-SNAPSHOT.jar .
export SPRING_DSEMPLOYEE_URL=jdbc:postgresql://cameldb-proxy:1234/dsEmployee
```
Start fuse-soap
```
nohup java -jar fuse-soap-1.0.0-SNAPSHOT.jar &
```
startup-script
```
#! /bin/bash
export SPRING_DSEMPLOYEE_URL=jdbc:postgresql://cameldb-proxy:1234/dsEmployee
cd /home/nugraha/fuse-soap/
nohup java -jar fuse-soap-1.0.0-SNAPSHOT.jar &
EOF
```

# create vm fuse-rest
```
gcloud compute instances create fuse-rest --machine-type e2-medium --zone us-central1-a --network devnet --subnet subnet-us
gcloud compute ssh fuse-rest --zone us-central1-a
sudo apt-get update
sudo apt install default-jdk
mkdir fuse-rest
cd fuse-rest
gsutil cp gs://nugraha-51412-gs/maven/fuse-rest-1.0.0-SNAPSHOT.jar .
export URL_EMPLOYEEWS="http://fuse-soap-instance-group-3h1g:8080/cxf/employeeWS"
```
Start fuse-rest
```
nohup java -jar fuse-rest-1.0.0-SNAPSHOT.jar &
```
startup-script
```
#! /bin/bash
export URL_EMPLOYEEWS="http://fuse-soap-instance-group-3h1g:8080/cxf/employeeWS"
cd /home/nugraha/fuse-rest/
nohup java -jar fuse-rest-1.0.0-SNAPSHOT.jar &
EOF
```



2104017785295


# test
Get data
```
curl http://fuse-rest:8080/camel/jaxrs/employeeall
```
Insert data
```
curl --header "Content-Type: application/json"   --request POST   --data '{ "name": "adit", "address": "jakarta", "phoneList": [{ "phone" : "08119366661", "type" : "HP" }, { "phone" : "02112345676", "type" : "home" }]  }'   http://fuse-rest:8080/camel/jaxrs/employee
```

# delete vm
```
gcloud compute instances delete fuse-soap-template-1 --zone us-central1-a
```
-------------

# create instance groups
Create image based on existing
```
gcloud compute images create fuse-soap-image --source-disk=fuse-soap --source-disk-zone=us-central1-a --force
gcloud compute images create fuse-rest-image --source-disk=fuse-rest --source-disk-zone=us-central1-a --force
```
Create instance templates
```
gcloud compute instance-templates create fuse-soap-template --machine-type=e2-micro --subnet=projects/nugraha-51412/regions/us-central1/subnetworks/subnet-us --metadata=startup-script=\#\!\ /bin/bash$'\n'cd\ /home/nugraha/fuse-soap/$'\n'sudo\ -u\ nugraha\ nohup\ java\ -jar\ fuse-soap-1.0.0-SNAPSHOT.jar\ \&$'\n'EOF --region=us-central1 --image=fuse-rest-image --boot-disk-size=10GB --boot-disk-type=pd-balanced --boot-disk-device-name=fuse-soap-template
gcloud compute instance-templates create fuse-rest-template --machine-type=e2-micro --subnet=projects/nugraha-51412/regions/us-central1/subnetworks/subnet-us --metadata=startup-script=\#\!\ /bin/bash$'\n'cd\ /home/nugraha/fuse-rest/$'\n'sudo\ -u\ nugraha\ nohup\ java\ -jar\ fuse-rest-1.0.0-SNAPSHOT.jar\ \&$'\n'EOF --region=us-central1 --image=fuse-rest-image --boot-disk-size=10GB --boot-disk-type=pd-balanced --boot-disk-device-name=fuse-rest-template
```
Create instance group
```
gcloud compute instance-groups managed create fuse-soap-instance-group --base-instance-name=fuse-soap-instance-group --template=fuse-soap-template --size=2 --zones=us-central1-b,us-central1-c,us-central1-f --instance-redistribution-type=PROACTIVE --metadata=startup-script=\#\!\ /bin/bash$'\n'cd\ /home/nugraha/fuse-soap/$'\n'sudo\ -u\ nugraha\ nohup\ java\ -jar\ fuse-soap-1.0.0-SNAPSHOT.jar\ \&$'\n'EOF 
gcloud compute instance-groups managed create fuse-rest-instance-group --base-instance-name=fuse-rest-instance-group --template=fuse-rest-template --size=2 --zones=us-central1-b,us-central1-c,us-central1-f --instance-redistribution-type=PROACTIVE --metadata=startup-script=\#\!\ /bin/bash$'\n'cd\ /home/nugraha/fuse-rest/$'\n'sudo\ -u\ nugraha\ nohup\ java\ -jar\ fuse-rest-1.0.0-SNAPSHOT.jar\ \&$'\n'EOF 
```

---------

gcloud beta compute --project=nugraha-51412 instance-templates create fuse-rest-template --machine-type=e2-micro 
--subnet=projects/nugraha-51412/regions/us-central1/subnetworks/subnet-us --network-tier=PREMIUM 
--metadata=startup-script=\#\!\ /bin/bash$'\n'cd\ /home/nugraha/fuse-rest/$'\n'sudo\ -u\ nugraha\ nohup\ java\ -jar\ fuse-rest-1.0.0-SNAPSHOT.jar\ \&$'\n'EOF 
--maintenance-policy=MIGRATE --service-account=939262183916-compute@developer.gserviceaccount.com --scopes=https://www.googleapis.com/auth/devstorage.read_only,https://www.googleapis.com/auth/logging.write,https://www.googleapis.com/auth/monitoring.write,https://www.googleapis.com/auth/servicecontrol,https://www.googleapis.com/auth/service.management.readonly,https://www.googleapis.com/auth/trace.append --region=us-central1 --image=fuse-rest-image --image-project=nugraha-51412 --boot-disk-size=10GB --boot-disk-type=pd-balanced --boot-disk-device-name=fuse-rest-template --no-shielded-secure-boot --shielded-vtpm --shielded-integrity-monitoring --reservation-affinity=any




# create vm maven-repo
gcloud compute disks create maven-repo --type=pd-balanced --size=10GB --zone=us-central1-a --image=ubuntu-1804-bionic-v20210325 --image-project=ubuntu-os-cloud
gcloud compute instances create maven-repo --machine-type e2-medium --zone us-central1-a --network devnet --subnet subnet-us --disk=name=maven-repo,device-name=maven-repo,mode=rw,boot=yes

# install artifactory
wget -qO - https://releases.jfrog.io/artifactory/api/gpg/key/public | sudo apt-key add -;
echo "deb https://releases.jfrog.io/artifactory/artifactory-debs bionic main" | sudo tee -a /etc/apt/sources.list;
sudo apt-get update && sudo apt-get install jfrog-artifactory-oss

# start artifactory
sudo systemctl start artifactory.service

# create vm apache
gcloud compute instances create apache --machine-type e2-medium --zone us-central1-a --network devnet --subnet subnet-us


nugraha-51412-gs/maven
