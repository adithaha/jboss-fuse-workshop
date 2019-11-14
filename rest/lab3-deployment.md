
## LAB 3 - Deployment

### Configure deployment

1. Go to fuse-rest directory
```
$ cd <fuse-rest>
```
2. Login into openshift (https://github.com/adithaha/workshop-agile-integration/blob/master/openshift-url.md)
```
oc login -u <user> <openshift-url>
```
3. Go to fuse project, or create if you don't have any
```
$ oc project fuse-workshop-<user>
```
or
```
$ oc new-project fuse-workshop-<user>
```
3. Create new OpenShift application
Using local path
```
$ oc new-app fuse7-java-openshift:1.2 --code=. --name=fuse-rest-<user> --strategy=source --build-env=MAVEN_MIRROR_URL=<repo>
```

Using git repository (SKIP THIS!)
```
$ oc new-app fuse7-java-openshift:1.2~https://github.com/adithaha/jboss-fuse-workshop.git --context-dir=/rest/solution/fuse-rest --name=fuse-rest-<user> --build-env=MAVEN_MIRROR_URL=<repo>
```

4. Open application port 8080 in service
```
oc edit svc/fuse-rest-<user>
...
spec:
  ...
  ports:
  ...
  - name: 8080-tcp
    port: 8080
    protocol: TCP
    targetPort: 8080
...
```
5. Create route from external to service port 8080
```
oc expose service fuse-rest-<name> --name=fuse-rest-<user> --port=8080
```

6. Open jolokia access so the application can be monitored using fuse console. Add name: jolokia to existing port 8778.
```
oc edit dc/fuse-rest-<user>
...
spec:
  ...
  template:
    ...
    spec:
      containers:
      - image: xxx
        ...
        ports:
        - containerPort: 8778
          name: jolokia
          protocol: TCP
...
```

### Deploy using source code from local client
Source code: local
Build: OpenShift server
```
cd <fuse-rest>
mvn clean
oc start-build fuse-rest-<user> --from-dir=. --follow
```

### Deploy using jar from local client (SKIP THIS!)
Source code: local
Build: local
```
cd <fuse-rest>
mvn clean package
oc start-build fuse-rest-<user> --from-file=target/fuse-rest-1.0.0-SNAPSHOT.jar --follow
```

### Configuring parameter
fuse-rest requires employee SOAP service as a backend. Assumed employeeWS is already set up, below are procedures to configure employeeWS settings. Since we are using Spring Boot, all parameters are configured via application.properties, and mapped to system environment. All we need to do is to configure system properties in application Deployment Config.

1. Get your application route
```
oc get route
```
or, use provided fuse-soap here https://github.com/adithaha/workshop-agile-integration/blob/master/openshift-url.md
Note the url for fuse-soap application 

2. Login to OpenShift Web Console via browser <openshift-url>
3. Go to project fuse-workshop-<user>
4. Choose Deployment Config fuse-rest - Environment tab - add environment parameter
  ```
  Name: URL_EMPLOYEEWS | Value: http://<fuse-soap-url>/cxf/employeeWS
  ```
5. Save
  
Application will be redeployed with configured parameter.

### Try your application

1. Get your application route
```
$ oc get route
```
Note the url for fuse-rest application 

2. Open url from browser, add path /camel/api-docs
