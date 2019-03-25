
## LAB 2 - Deployment

### Configure deployment

1. Go to workspace directory
```
$ cd <workspace-dir>
```
2. Login into openshift
```
$ oc login -u <user> https://
```
3. Create new OpenShift application
```
$ oc new-app fuse7-java-openshift:1.1 --code . --name=fuse-soap --strategy=source
```
4. Open application port 8080 in service
```
$ oc edit svc/fuse-soap
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
$ oc expose service fuse-soap --name=fuse-soap --port=8080
```

6. Open jolokia access so the application can be monitored using fuse console. Add name: jolokia to existing port 8778.
```
$ oc edit dc/fuse-soap
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
$ oc start-build fuse-soap --from-dir=fuse-soap --follow
```

### Deploy using jar from local client
Source code: local
Build: local
```
$ oc start-build fuse-soap --from-file=target/fuse-soap-1.0.0-SNAPSHOT.jar --follow
```
