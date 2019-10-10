
## LAB 1 - Create Fuse REST Integration Project

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
  Add below JDG client dependency
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
5. Compile your application
```
Clean build: right click your fuse-soap project - run as - maven clean
Build: right click your fuse-rest project - run as - maven build....
	Goals: clean package
	Run
The application should be compiled successfully
```
6. Create getEmployeeAll with cache

