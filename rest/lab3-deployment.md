
## LAB 2 - Deployment

### Configure deployment

1. Go to fuse-rest directory
```
$ cd <fuse-rest>
```
2. Login into openshift
```
$ oc login -u <user> https://openshift.com
```
3. Create new OpenShift application
Using local path
```
$ oc new-app fuse7-java-openshift:1.3 --code=. --name=fuse-rest-<name> --strategy=source
```

Using git repository  --DJA DO NOT USE THIS--
```
$ oc new-app fuse7-java-openshift:1.3~https://github.com/adithaha/jboss-fuse-workshop.git --context-dir=/rest/solution/fuse-rest --name=fuse-rest-<name>
```

4. Open application port 8080 in service
```
$ oc edit svc/fuse-rest
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
$ oc expose service fuse-rest --name=fuse-rest --port=8080
```

6. Open jolokia access so the application can be monitored using fuse console. Add name: jolokia to existing port 8778.
```
$ oc edit dc/fuse-rest
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

### Deploy using source code from local client --BKPM Skip--
Source code: local
Build: OpenShift server
```
$ oc start-build fuse-rest --from-dir=fuse-rest --follow
```

### Deploy using jar from local client
Source code: local
Build: local
```
$ mvn clean package
$ oc start-build fuse-rest-<name> --from-file=target/fuse-rest-1.0.0-SNAPSHOT.jar --follow
```

### Configuring parameter
fuse-rest requires employee SOAP service as a backend. Assumed employeeWS is already set up, below are procedures to configure employeeWS settings. Since we are using Spring Boot, all parameters are configured via application.properties, and mapped to system environment. All we need to do is to configure system properties in application Deployment Config.

1. Login to OpenShift Web Console via browser https://openshift.com
2. Go to project <project>
3. Choose Deployment Config fuse-rest - Environment tab - add environment parameter
  ```
  Name: URL_EMPLOYEEWS | Value: http://<fuse-soap-route>/cxf/employeeWS
  ```
4. Save
  
Application will be redeployed with configured parameter.

### Try your application

1. Get your application route
```
$ oc get route
```
Note the url for fuse-rest application 

2. Open url from browser, add path /camel/api-docs
