
## LAB 3 - Deployment

Open JBoss Developer Studio application,  to work on fuse-soap project from lab1. If you haven't completed lab1, you can start with this project https://github.com/adithaha/jboss-fuse-workshop/raw/master/soap/solution/lab2/fuse-soap.zip and import into CodeReady Studio

#### Import project
If you download fuse-soap.zip, follow procedure below
1. Unzip fuse-soap.zip
2. Open CodeReady Studio
3. File - Import - type 'maven' - choose Existing Maven Project - Browse - Choose fuse-soap folder - check fuse-soap - finish

### Configure deployment

1. Go to workspace directory
```
$ cd fuse-soap
```
2. Login into openshift (https://github.com/adithaha/workshop-agile-integration/blob/master/openshift-url.md)
```
$ oc login -u <user> <openshift-url>
```
3. Go to fuse project
```
$ oc project fuse-workshop-<user>
```
3. Create new OpenShift application. Use <repo> in https://github.com/adithaha/workshop-agile-integration/blob/master/openshift-url.md

Using local path
```
$ oc new-app fuse7-java-openshift:1.2 --code=. --name=fuse-soap-<user> --strategy=source --build-env=MAVEN_MIRROR_URL=<repo>
```
Ignore git error as we are not using git server

OR

Using git repository (SKIP THIS!)
```
$ oc new-app fuse7-java-openshift:1.2~https://github.com/adithaha/jboss-fuse-workshop.git --context-dir=/soap/solution/fuse-soap --name=fuse-soap-<user> --build-env=MAVEN_MIRROR_URL=<repo>
```

4. Open service yaml configuration, add port 8080. Note: be careful with identation, must be correct and always use space.
```
$ oc edit svc/fuse-soap-<user>
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
$ oc expose service fuse-soap-<user> --name=fuse-soap-<user> --port=8080
```

6. Open jolokia access so the application can be monitored using fuse console. Add name: 'jolokia' to existing port 8778. Note: be careful with identation, must be correct and always use space.
```
$ oc edit dc/fuse-soap-<user>
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

### Deploy using jar from local client (SKIP THIS!)
Source code: local
Build: local
```
$ cd <fuse-soap>
$ mvn clean package
$ oc start-build fuse-soap-<user> --from-file=target/fuse-soap-1.0.0-SNAPSHOT.jar --follow
```

### Or, Deploy using source code from local client
Source code: local
Build: OpenShift server
```
cd <fuse-soap>
mvn clean
oc start-build fuse-soap-<user> --from-dir=. --follow
```

### Configuring parameter
fuse-soap requires postgre database to put and get data. Assumed database is already set up, below are procedures to configure database settings. Since we are using Spring Boot, all parameters are configured via application.properties, and mapped to system environment. All we need to do is to configure system properties in application Deployment Config.

1. Login to OpenShift Web Console via browser <openshift-url>
2. Go to project <project>
3. Choose Deployment Config fuse-soap - Environment tab - add environment parameter  
  Name: SPRING_DSEMPLOYEE_URL | Value: jdbc:postgresql://postgresql:5432/dsEmployee  
  Name: SPRING_DSEMPLOYEE_USERNAME | Value: postgres  
  Name: SPRING_DSEMPLOYEE_PASSWORD | Value: postgres  
4. Save
  
Application will be redeployed with configured parameter.

### Try your application

1. Get your application route
```
$ oc get route
```
Note the url  

2. Open url from browser, add path /cxf
