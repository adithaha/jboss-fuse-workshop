
## LAB 0 - Environment

### Preparing environment
1. Create project namespace
```
oc login -u <user> <openshift-url>
oc new-project fuse-workshop-<user>
```
2. Deploy postgre database
```
oc new-app --template=postgresql-ephemeral --env=POSTGRESQL_DATABASE=dsEmployee
```

3. Import database ddl
```
oc get pods | grep postgresql | grep Running
```
Note down pod id, eg. postgresql-1-2sr6g
```
oc rsh postgresql-1-<id>
cd /tmp/
curl https://raw.githubusercontent.com/adithaha/workshop-agile-integration/master/soap/ddl.sql > ddl.sql
psql -h localhost postgres -f ddl.sql postgres
exit
```
