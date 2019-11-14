
## LAB 1 - Create Fuse REST Integration Project

Open JBoss Developer Studio application. Continue to work on fuse-soap project from SOAP lab. If you haven't completed SOAP lab, you can start with this project https://github.com/adithaha/jboss-fuse-workshop/raw/master/soap/solution/lab4/fuse-soap.zip, import into CodeReady Studio, and do lab3 on https://github.com/adithaha/jboss-fuse-workshop/blob/master/soap/lab3-deployment.md

Download wsdl file here https://raw.githubusercontent.com/adithaha/jboss-fuse-workshop/master/rest/employeeWS.wsdl

1. File - New - Project.. - type 'fuse' - Fuse Integration Project - Next
 ```
Project-name: fuse-rest
Next
Deployment Platform: Standalone
Runtime Environment: Spring Boot
Camel version: 2.21.0.fuse-731003-redhat-00003 (Fuse 7.3.1 GA)
Next
Simple log using Spring Boot - Spring DSL
Finish
Open Associate Perspective: Yes
 ```
 If this is the first time you create fuse project, expect around 15 minutes to download dependency from internet
 
2. Open fuse-rest - pom.xml, pom.xml, replace below lines #4 - #9
 ``` 
  <groupId>org.jboss.fuse.workshop</groupId>
  <artifactId>fuse-rest</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <name>Workshop:: Fuse REST</name>
  <description>Workshop:: Fuse REST</description>
```
  Add below lines #28 starting with <dependency>
  
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

3. Change default package fuse-rest - src/main/java - org.mycompany - right click - refactor - rename
	- New Name: org.jboss.fuse.workshop.rest
	- OK
	- Continue
4. Remove default route fuse-rest - Camel Contexts - camel-context.xml - right click - Delete - OK

5. Remove configuration folder - configuration - right click - Delete

6. Compile your application
```
Clean and Build: right click your fuse-rest - run as - maven build....
	Name: fuse-rest mvn clean package
	Goals: clean package
	Run
if this is the first time you build fuse project, expect to have around 10 minutes to download all libraries from internet
The application should be compiled successfully
*Later if you want to build the application, you can just select maven build - fuse-rest clean package
```

