
## LAB 1 - Using JDG as response cache

Open JBoss Developer Studio application. Continue to work on fuse-rest project from REST lab. If you haven't completed REST lab, you can start with this project https://github.com/adithaha/jboss-fuse-workshop/raw/master/rest/solution/lab4/fuse-rest.zip, import into CodeReady Studio, and do lab3 on https://github.com/adithaha/jboss-fuse-workshop/blob/master/soap/lab3-deployment.md

1. Open pom.xml, source
 
Add JDG client version inside <properties>
```
  ...
  <properties>
    <fuse.version>7.3.1.fuse-731003-redhat-00003</fuse.version>
    <maven-compiler-plugin.version>3.6.0</maven-compiler-plugin.version>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven-surefire-plugin.version>2.19.1</maven-surefire-plugin.version>
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
2. Add JDG URL property. Open src/main/resources - application.properties - source
```
jdg.url=localhost:11222?cacheName=coba
```
3. Compile your application
```
Clean build: right click your fuse-soap project - run as - maven clean
Build: right click your fuse-rest project - run as - maven build....
	Goals: clean package
	Run
The application should be compiled successfully
```
4. Create getEmployeeAllCache 
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
