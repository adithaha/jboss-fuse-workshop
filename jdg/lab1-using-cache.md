
## LAB 1 - Using JDG as response cache

We will Continue to work on fuse-rest project from REST lab. Add cache capability for method getEmployeeAll using JBoss Data Grid.  

Open JBoss Developer Studio application. Continue to work on fuse-rest project from REST lab. If you haven't completed REST lab, you can start with this project https://github.com/adithaha/jboss-fuse-workshop/raw/master/rest/solution/lab2/fuse-rest.zip, import into CodeReady Studio, and do lab3 on https://github.com/adithaha/jboss-fuse-workshop/blob/master/rest/lab3-deployment.md

### Deploy JBoss Data Grid

1. Go to openshift web console, login with your user
2. Go to project 
```
fuse-workshop-<name>
```
3. Deploy JBoss Data Grid using template
```
Add to Project - Browse Catalog - filter - type 'grid 7.2' - choose Red Hat JBoss Data Grid 7.2 (Ephemeral, no https) 
Next
Cache Names: fuse-workshop
Next
Create
```	
4. JBoss Data Grid will be deployed. Notice there are 4 network service exposed, we will be using hotrod connecter later. Note the service name and port - datagrid-app-hotrod:11333

### Create getEmployeeAllCache method
	
1. Open fuse-rest - pom.xml, pom.xml
 
Add JDG client version inside <properties>
```
  ...
  <properties>
    ...
    <org.jboss.datagrid.version>6.5.1.Final-redhat-1</org.jboss.datagrid.version>
  </properties>
```
Add  JDG client dependency
``` 
      ...
      <artifactId>camel-swagger-java-starter</artifactId>
    </dependency>
    <dependency>
      <groupId>org.apache.camel</groupId>
      <artifactId>camel-jbossdatagrid</artifactId>
      <version>${org.jboss.datagrid.version}</version>
      <exclusions>
      	<exclusion>
          <groupId>org.infinispan</groupId>
          <artifactId>infinispan-embedded</artifactId>
        </exclusion>
        <exclusion>
          <groupId>org.infinispan</groupId>
          <artifactId>infinispan-query-dsl</artifactId>
        </exclusion>
        <exclusion>
          <groupId>org.infinispan</groupId>
          <artifactId>infinispan-commons</artifactId>
        </exclusion>
      </exclusions>
    </dependency>
  
```
2. Add JDG URL property, put hotrod url from previous step. Open src/main/resources - application.properties - source
```
jdg.url=datagrid-app-hotrod:11333?cacheName=fuse-workshop
```
3. Compile your application
```
Build: right click your fuse-rest project - run as - maven build....
	Goals: clean package
	Run
The application should be compiled successfully
```
4. Create getEmployeeAllCache route, Camel Contexts - rest-springboot-context.xml
```
Routing - Route
	Id: getEmployeeAllCache
Component - Direct
	Uri: direct:getEmployeeAllCache
Transformation - Set Header
	Expression: constant 
	Expression: CamelInfinispanOperationGet
	Header Name: CamelInfinispanOperation
Transformation - Set Header
	Expression: constant 
	Expression: getEmployeeAll
	Header Name: CamelInfinispanKey
Component - Direct
	Uri: infinispan://{{jdg.url}}
Routing - Choice 
(inside Choice) Routing - When
	Expression: simple
	Expression: ${header.CamelInfinispanOperationResult} == null
(inside When) Component - Log
	Message: not found in cache, calling SOAP webservice
(inside When) Component - Direct
	Uri: direct:getEmployeeAll
(inside When) Transformation - Convert Body To
	Type: java.lang.String
(inside When) Transformation - Set Header
	Expression: constant 
	Expression: CamelInfinispanOperationPut
	Header Name: CamelInfinispanOperation
(inside When) Transformation - Set Header
	Expression: simple 
	Expression: ${body}
	Header Name: CamelInfinispanValue
(inside When) Component - Direct
	Uri: infinispan://{{jdg.url}}
(inside Choice) Routing - Otherwise
(inside Otherwise)  Component - Log
	Message: found in cache
(inside Otherwise) Transformation - Set Body
	Expression: simple
	Expression: ${header.CamelInfinispanOperationResult}
Transformation - Convert Body To
	Type: org.jboss.fuse.workshop.soap.EmployeeList

```
5. Make getEmployeeAllCache as default. Go to rest configuration - rest-springboot-context.xml - REST
```
REST Operations - get /employeall
To URI: direct:getEmployeeAllCache

* if you cannot select direct:getEmployeeAllCache, save rest-springboot-context.xml - close - open again
```
6. Redeploy into openshift

Deploy using built jar from local client  
Source code: local  
Build: local
```
$ cd <fuse-rest>
$ mvn clean package
$ oc start-build fuse-rest-<name> --from-file=target/fuse-rest-1.0.0-SNAPSHOT.jar --follow
```

OR
(Pegadaian skip)
Deploy using source code from local client  
Source code: local  
Build: OpenShift server
```
$ oc start-build fuse-rest-<name> --from-dir=fuse-rest --follow
```

### Try your application

1. Get your application route
```
$ oc get route
```
Note the url  

2. Open url from browser, add path /camel/jaxrs/employeeall
