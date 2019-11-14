
## LAB 1 - Create Fuse JSON Integration Project

Previous REST lab has deployed fuse-rest application, for employee API. There is new requirement to use employee API, but with some attribute masking. In this case, we will remove address and phone type attributes.

Open JBoss Developer Studio application. 

1. File - New - Project.. - type 'fuse' - Fuse Integration Project - Next
 ```
Project-name: fuse-json
Next
Deployment Platform: Standalone
Runtime Environment: Spring Boot
Camel version: 2.21.0.fuse-731003-redhat-00003 (Fuse 7.3.1 GA)
Next
Simple log using Spring Boot - Spring DSL
Finish
Open Associate Perspective: Yes
 ```
2. Open fuse-rest - pom.xml, pom.xml, replace below lines #4 - #9
 ``` 
  <groupId>org.jboss.fuse.workshop</groupId>
  <artifactId>fuse-json</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <name>Workshop:: Fuse JSON</name>
  <description>Workshop:: Fuse JSON</description>
```
  Add below lines #28 starting with <dependency>
  
   ``` 
  <dependencies>
    ...
    <dependency>
      <groupId>org.apache.camel</groupId>
      <artifactId>camel-servlet-starter</artifactId>
    </dependency>
    <dependency>
      <groupId>org.apache.camel</groupId>
      <artifactId>camel-http4-starter</artifactId>
    </dependency>
    <dependency>
      <groupId>org.apache.camel</groupId>
      <artifactId>camel-jolt-starter</artifactId>
    </dependency>
  </dependencies>
  
  ```

3. Change default package fuse-rest - src/main/java - org.mycompany - right click - refactor - rename
	- New Name: org.jboss.fuse.workshop.json
	- OK
	- Continue
4. Remove configuration folder - configuration - right click - Delete

5. Compile your application
```
Clean and Build: right click your fuse-json - run as - maven build....
	Name: fuse-json mvn clean package
	Goals: clean package
	Run
The application should be compiled successfully

*Later if you want to build the application, you can just select maven build - fuse-json clean package
```

