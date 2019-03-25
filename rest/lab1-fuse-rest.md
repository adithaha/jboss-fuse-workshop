
## LAB 1 - Create Fuse Soap Integration Project

Open JBoss Developer Studio application

1. File - New - Project.. - type 'fuse' - Fuse Integration Project - Project-name: fuse-rest - 2.21.0.fuse-710018-redhat-00001 (Fuse 7.1.0 GA) - Simple log using Spring Boot - Spring DSL - Finish - Open Associate Perspective: Yes
2. Change groupId in pom.xml - fuse-soap - pom.xml
 ``` 
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.jboss.fuse.workshop</groupId>
  <artifactId>fuse-rest</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <name>Workshop:: Fuse REST</name>
  <description>Workshop:: Fuse REST</description>
  ```
  
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
      <artifactId>camel-jackson-starter</artifactId>
    </dependency>
    <dependency>
      <groupId>org.apache.camel</groupId>
      <artifactId>camel-swagger-java-starter</artifactId>
    </dependency>
  </dependencies>
  
  ```

3. Create jaxb binding to generate classes from wsdl - fuse-rest - right click - New - Other... - XML file - Next
	- Location: fuse-rest
	- File name: jaxb-bindings.xml
	- Finish
```	
<jaxb:bindings version="2.0"
               xmlns:jaxb="http://java.sun.com/xml/ns/jaxb" 
               xmlns:xjc="http://java.sun.com/xml/ns/jaxb/xjc"
               xmlns:xs="http://www.w3.org/2001/XMLSchema"
				xmlns:annox="http://annox.dev.java.net">
    <jaxb:bindings>
        <jaxb:globalBindings generateElementProperty="false">
        	<xjc:simple />
        	<!-- <xjc:serializable uid="1" /> -->
        </jaxb:globalBindings>
    </jaxb:bindings>
</jaxb:bindings>
 ```
3. Change default package src/main/java - org.mycompany - right click - refactor - rename
	- New Name: org.jboss.fuse.workshop.rest
	- OK
	- Continue

4. Put wsdl into fuse-rest. Go to previous application fuse-soap, download http://localhost:8080/cxf/employeeWS?wsdl, put into fuse-rest - src/main/resources/employeeWS.wsdl

4. Generate java class for corresponding wsdl - fuse-rest - pom.xml
```
<build>
    ...
    <plugins>
      ...
      <plugin>
        <groupId>org.apache.cxf</groupId>
        <artifactId>cxf-codegen-plugin</artifactId>
        <executions>
          <execution>
            <id>generate-sources</id>
            <phase>generate-sources</phase>
            <goals>
              <goal>wsdl2java</goal>
            </goals>
            <configuration>
              <sourceRoot>${basedir}/target/generated/src/main/java</sourceRoot>
              <defaultOptions>
                <frontEnd>jaxws21</frontEnd>
              </defaultOptions>
              <wsdlOptions>
                <wsdlOption>
                  <wsdl>${basedir}/src/main/resources/employeeWS.wsdl</wsdl>
                  <extraargs>
                    <extraarg>-b</extraarg>
                    <extraarg>${basedir}/jaxb-bindings.xml</extraarg>
                  </extraargs>
                </wsdlOption>
              </wsdlOptions>
            </configuration>
          </execution>
        </executions>
      </plugin>
```

12. Generate java classes from WSDL
```
- fuse-rest - right click - Run as - Maven clean
- fuse-rest - right click - Run as - Maven generate-sources

Check if classes already generated in target/generated/src/main/java directory
```



12. Create json mapper class fuse-rest - src/main/resources - spring - right click - New - XML File - json.xml - Finish - source
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">


    <bean id="jacksonObjectMapper" class="org.codehaus.jackson.map.ObjectMapper">
	    <property name="serializationInclusion" value="NON_NULL"/>
	</bean>
	
	<bean id="jsonProvider" class="org.codehaus.jackson.jaxrs.JacksonJsonProvider">
	    <property name="mapper" ref="jacksonObjectMapper"/>
	</bean>

</beans>
```

13. Create route src/main/resources - OSGI-INF - blueprint - right click - New - Camel XML File - rest-context.xml (OSGI blueprint) - Finish - source
```
<?xml version="1.0" encoding="UTF-8"?>
<blueprint xmlns="http://www.osgi.org/xmlns/blueprint/v1.0.0"
    xmlns:cm="http://aries.apache.org/blueprint/xmlns/blueprint-cm/v1.0.0"
    xmlns:cxf="http://camel.apache.org/schema/blueprint/cxf"
    xmlns:cxf-core="http://cxf.apache.org/blueprint/core"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="              http://www.osgi.org/xmlns/blueprint/v1.0.0              http://www.osgi.org/xmlns/blueprint/v1.0.0/blueprint.xsd              http://camel.apache.org/schema/blueprint/cxf               http://camel.apache.org/schema/blueprint/cxf/camel-cxf.xsd              http://camel.apache.org/schema/blueprint               http://camel.apache.org/schema/blueprint/camel-blueprint.xsd">
    <cxf:cxfEndpoint address="/employeeWS" id="employeeWS" serviceClass="org.jboss.fuse.workshop.soap.EmployeeWS">
        <cxf:properties>
            <entry key="dataFormat" value="POJO"/>
            <entry key="faultStackTraceEnabled" value="true"/>
            <entry key="loggingFeatureEnabled" value="false"/>
        </cxf:properties>
    </cxf:cxfEndpoint>
    <camelContext id="fuse-soap"
        trace="false" xmlns="http://camel.apache.org/schema/blueprint">
        <route id="fuse-soap-service"/>
    </camelContext>
</blueprint>
```
14. Create rest route. Click Design tab
```
Components - CXF - cxf:bean:employeeWS
Routing - Recipient List - simple - direct:${header.operationName}
```

15. Create addEmployee route. Click Design tab
```
Routing - Route
Component - Direct - direct:addEmployee
Transformation - Convert Body To - org.jboss.fuse.workshop.soap.Employee
Component - Log - receive request ${body}
Transformation - Set Property - simple - employee - ${body}
Transformation - Set Header - constant - CamelSqlRetrieveGeneratedKeys - true
Component - SQL - sql:insert into employee (name, address) values (:#${body.name}, :#${body.address})?dataSource=dsFis2&outputType=SelectOne
Transformation - Transform - simple - ${property.employee.setId(${header.CamelSqlGeneratedKeyRows[0][id]})}
Routing - Split - simple - ${property.employee.phoneList}
	Component - Log - phone: ${body}
	Component - SQL - sql:insert into phone (employee_id, phone, type) values (:#${property.employee.id}, :#${body.phone}, :#${body.type})?dataSource=dsFis2&outputType=SelectOne
Transformation - Set Body - simple - ${property.employee}
Component - Log - send response ${body}
```
16. Build
pilih project - klik kanan - run as - mvn clean
pilih project - klik kanan - run as - mvn install


17. Deploy into Fuse (assumed fuse is already started)

Standalone
```
features:install jdbc 
features:install camel-sql 
osgi:install -s mvn:org.postgresql/postgresql/9.4.1212
osgi:install -s mvn:org.jboss.fuse.workshop/fuse-soap/1.0.0-SNAPSHOT
```

fabric
```
Open browser http://localhost:8181, login
wiki - new fabric6 profile (sample-fuse-rest)
parent - jboss-fuse-min
Deployment - add - mvn:org.postgresql/postgresql/9.4.1212
Deployment - add - mvn:org.jboss.fuse.workshop/fuse-rest/1.0.0-SNAPSHOT
Deployment - add - mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-dbcp/1.4_3
features - add - jdbc
features - add - camel-sql 
features - add - fabric-cxf
features - add - cxf
features - add - camel-cxf
```

18. Create getEmployee route. Click Design tab
```
Routing - Route
Component - Direct - direct:getEmployee
Transformation - Convert Body To - java.lang.Integer
Component - Log - receive request ${body}
Component - SQL - select * from employee where id = :#${body}?dataSource=dsFis2&amp;outputType=SelectOne
Transformation - Set Property - simple - employee - ${body}
Component - SQL - select * from phone where employee_id = :#${property.employee.id}?dataSource=dsFis2&amp;outputType=SelectOne
Component - Bean - putPhoneList - myTransformer
Transformation - Set Body - simple - ${property.employee}
Component - Log - send response ${body}
```
19. Redeploy into Fuse (assumed fuse is already started)

osgi:list

#get bundle id

osgi:update <id>
osgi:refresh <id>

20. Create getEmployeeAll route. Click Design tab

```
Routing - Route
Component - Direct - direct: getEmployeeAll
Component - Log - receive request ${body}
Transformation - Set Property - method - name:employeeList - ref:myTransformer - method:createEmployeeList
Component - SQL - select * from employee?dataSource=dsFis2&outputType=SelectList&outputClass=org.jboss.fis2.demo.soap.Employee
Component - Bean - putEmployeeList - myTransformer
Routing - Split - simple - ${property.employeeList.employeeList}
	Transformation - Set Property - simple - employee - ${body}	
	Component - SQL - sql:select * from phone where employee_id = :#${property.employee.id}?dataSource=dsFis2&outputType=SelectList
	Component - Bean - putPhoneList - myTransformer	
Transformation - Set Body - simple - ${property.employeeList}
Component - Log - send response ${body}
```

20. Redeploy into Fuse (assumed fuse is already started)

osgi:list

#get bundle id

osgi:update <id>
osgi:refresh <id>
