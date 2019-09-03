
## LAB 2 - Deployment

### Configure deployment

1. Go to workspace directory
```
$ cd <workspace-dir>
```
2. Login into openshift
```
$ oc login -u <user> https://openshift.com
```
3. Go to fuse project
```
$ oc project fuse-workshop-<name>
```
3. Create new OpenShift application

Using local path
```
$ oc new-app fuse7-java-openshift:1.3 --code=. --name=fuse-soap-<name> --strategy=source
```

Using git repository --DJA DO NOT USE THIS--
```
$ oc new-app fuse7-java-openshift:1.3~https://github.com/adithaha/jboss-fuse-workshop.git --context-dir=/soap/solution/fuse-soap --name=fuse-soap-<name>
```

4. Open application port 8080 in service
```
$ oc edit svc/fuse-soap-<name>
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
$ oc expose service fuse-soap-<name> --name=fuse-soap-<name> --port=8080
```

6. Open jolokia access so the application can be monitored using fuse console. Add name: jolokia to existing port 8778.
```
$ oc edit dc/fuse-soap-<name>
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

### Deploy using source code from local client --DJA DO NOT USE THIS--
Source code: local
Build: OpenShift server
```
$ oc start-build fuse-soap-<name> --from-dir=fuse-soap --follow
```

### Deploy using jar from local client
Source code: local
Build: local
```
$ cd <fuse-soap>
$ mvn clean package
$ oc start-build fuse-soap-<name> --from-file=target/fuse-soap-1.0.0-SNAPSHOT.jar --follow
```

### Configuring parameter
fuse-soap requires postgre database to put and get data. Assumed database is already set up, below are procedures to configure database settings. Since we are using Spring Boot, all parameters are configured via application.properties, and mapped to system environment. All we need to do is to configure system properties in application Deployment Config.

1. Login to OpenShift Web Console via browser https://openshift.com
2. Go to project <project>
3. Choose Deployment Config fuse-soap - Environment tab - add environment parameter  
  Name: SPRING_DSEMPLOYEE_URL | Value: jdbc:postgresql://postgresql:5432/dsEmployee  
  Name: SPRING_DSEMPLOYEE_USERNAME | Value: postgres  
  Name: SPRING_DSEMPLOYEE_PASSWORD | Value: postgres  
4. Save
  
Application will be redeployed with configured parameter.
