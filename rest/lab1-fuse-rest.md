
## LAB 1 - Create Fuse REST Integration Project

Open JBoss Developer Studio application

1. File - New - Project.. - type 'fuse' - Fuse Integration Project - Project-name: fuse-rest - 2.21.0.fuse-710018-redhat-00001 (Fuse 7.1.0 GA) - Simple log using Spring Boot - Spring DSL - Finish - Open Associate Perspective: Yes
2. Change groupId in pom.xml - fuse-rest - pom.xml
 ``` 
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.jboss.fuse.workshop</groupId>
  <artifactId>fuse-rest</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <name>Workshop:: Fuse REST</name>
  <description>Workshop:: Fuse REST</description>
  ```
  Add few dependencies below:
   ``` 
  <dependencies>
    ...
    <dependency>
      <groupId>org.apache.camel</groupId>
      <artifactId>camel-cxf-starter</artifactId>
    </dependency>
    <dependency>
      <groupId>org.apache.camel</groupId>
      <artifactId>camel-servlet-starter</artifactId>
    </dependency>
    <dependency>
      <groupId>org.apache.camel</groupId>
      <artifactId>camel-jaxb-starter</artifactId>
    </dependency>
    <dependency>
      <groupId>org.apache.camel</groupId>
      <artifactId>camel-jackson-starter</artifactId>
    </dependency>
    <dependency>
      <groupId>org.apache.camel</groupId>
      <artifactId>camel-swagger-java-starter</artifactId>
    </dependency>
  </dependencies>
  
  ```

3. Change default package src/main/java - org.mycompany - right click - refactor - rename
	- New Name: org.jboss.fuse.workshop.rest
	- OK
	- Continue

4. Generate REST service from WSDL
```
- File - New - Other... - Camel REST From WSDL
	- WSDL file: put wsdl file from employeeWS
	- Destination Project: fuse-rest
	- Next
	- Finish
- fuse-rest - right click - Run as - Maven generate-sources

Check if classes already generated in target/generated/src/main/java directory
```

5. Configure API documentation, replace <restConfiguration> tag in rest-springboot-context.xml with below

```
	<restConfiguration apiContextPath="api-docs" bindingMode="json"
            component="servlet" contextPath="/camel" enableCORS="true">
            <dataFormatProperty key="prettyPrint" value="true"/>
            <apiProperty key="cors" value="true"/>
            <apiProperty key="api.version" value="1.0.0"/>
            <apiProperty key="api.title" value="Red Hat Fuse - REST"/>
            <apiProperty key="api.description" value="Red Hat Fuse - REST"/>
            <apiProperty key="api.contact.name" value="Red Hat"/>
        </restConfiguration>
        
```
6. For easy configuration, put SOAP address on application.properties - src/main/resources - application.properties - Finish - source
```
...
url.employeeWS=http://localhost:8080/cxf/employeeWS
...
```

7. Configure camel context to get SOAP address from properties - rest-springboot-context. For each cxf endpoint, replace http://localhost:8080/cxf/employeeWS to {{url.employeeWS}} 
```
Uri: cxf://{{url.employeeWS}}...
```

8. Method with empty parameter is not configured correctly. getEmployeeAll service uri must be changed in <rest> tag
From:
```
uri="/employeeall/{arg0}">
```
To:
```
uri="/employeeall">
```

9. since getEmployeeAll doesn't have any parameter, its body need to be set to null before sending to soap backend. Go to design.
```
In route getEmployeeAll, insert below between _log3 and cxf
Transformation - Set Body
	- Expression: Simple
	- Expression: null
```

10. Configure Spring Boot to read generated camel xml - src/main/java - org.jboss.fuse.workshop.rest - Application.java
```
@ImportResource({"classpath:spring/rest-springboot-context.xml"})
```


11. Change port so not conflicting with fuse-soap - src/main/resources - application.properties - Finish - source - add line below

change management port from 8081 to 8091:
```
management.port=8091
```
configure http port to 8090:
```
server.port=8090
```

12. Try your application
```
Clean build: right click your fuse-soap project - run as - maven clean
Build: right click your fuse-soap project - run as - maven build....
	Goals: clean package
	Run
start fuse application: fuse-soap - src/main/java - org.jboss.fuse.workshop.soap - Application.java (right click) - run as - Java Application

Check if classes already generated in target/generated/src/main/java directory
```
Open browser, go to at http://localhost:8090/camel/api-docs

```
stop fuse application: go to console tab - click red square on the right
```
