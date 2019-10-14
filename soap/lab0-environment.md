
## LAB 0 - Environment

### Preparing environment
```
oc login -u <user> <openshift-url>
oc new-project fuse-workshop-<user>
oc new-app --template=postgresql-ephemeral --env=POSTGRESQL_DATABASE=dsEmployee
oc get pods
oc rsh postgresql-1-62vdm
cd /tmp/
curl https://raw.githubusercontent.com/adithaha/workshop-agile-integration/master/soap/ddl.sql > ddl.sql
psql -h localhost postgres -f ddl.sql postgres
exit
```
