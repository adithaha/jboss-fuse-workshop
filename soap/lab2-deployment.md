
## LAB 2 - Deployment

1. Login into openshift
```
$ oc login -u <user> https://
$ oc new-project <user>-fuse-workshop
```

### Deploy with S2I Binary

2. Create new application on ocp
```
$ oc new-app blabla
```

3. Start build on ocp
```
$ oc start build blabla
```
4. Expose 8080 port yaml
5. Service expose 8080
6. Route expose 8080
7. Expose management port 8778
8. Try rebuild

