
## LAB 1 - Installasi JBoss Fuse dengan fabric

1. File - New - Project.. - type 'fuse' - Fuse Integration Project - fuse-soap - 2.17.0.redhat-630187 - Use Predefined template - JBoss Fuse - Beginner - Content Based Router - Finish
2. Change groupId in pom.xml
 ``` 
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.jboss.fuse.workshop</groupId>
  <artifactId>fuse-rest</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <packaging>bundle</packaging>
  <name>Workshop:: Fuse REST</name>
  ```
    In maven-bundle-plugin add
    ```
	<plugin>
        <groupId>org.apache.felix</groupId>
        <artifactId>maven-bundle-plugin</artifactId>
        <version>${version.maven-bundle-plugin}</version>
        <extensions>true</extensions>
	<! -- copy from here -->
        <configuration>
          <instructions>
            <Bundle-SymbolicName>${project.artifactId}</Bundle-SymbolicName>
            <Import-Package>*</Import-Package>
            <Export-Package></Export-Package>
          </instructions>
        </configuration>
	<! -- end here -->
	```
3. Remove existing test code src/test/java - com.mycompany - right click - delete
4. Remove existing test code src/test/resources - data - right click - delete
5. Remove existing camel route Camel Contexts - blueprint.xml - right click - delete
6. Create java resources in src/main - right click - new - folder
7. Folder name - java
8. Create phone model class src/main/java - right click - new - class
	- package org.jboss.fuse.workshop.rest
	- Name Phone
```
package org.jboss.fuse.workshop.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Phone {

	private String phone;
	private String type;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
```
9. Create employee model class src/main/java - right click - new - class
	- package org.jboss.fuse.workshop.rest
	- Name Employee
```
package org.jboss.fuse.workshop.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="employee")
public class Employee {

	private Integer id;
	private String name;
	private String address;
	private List<Phone> phoneList = new ArrayList<Phone>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Phone> getPhoneList() {
		return phoneList;
	}
	public void setPhoneList(List<Phone> phoneList) {
		this.phoneList = phoneList;
	}
	
	
}
```
10. Create employee list model class src/main/java - right click - new - class
	- package org.jboss.fuse.workshop.rest
	- Name EmployeeList
```
package org.jboss.fuse.workshop.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="employeeList")
public class EmployeeList {

	private List<Employee> employeeList = new ArrayList<Employee>();

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	
	
}
```
11. Create Transformer class src/main/java - right click - new - class
	- package org.jboss.fuse.workshop.rest
	- Name MyTransformer


```
package org.jboss.fuse.workshop.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperty;

public class MyTransformer {
	
	
    public void putPhoneList(
    		@ExchangeProperty(value="employee") Employee employee,
    		@Body List<Phone> phoneList
    		) {
    	
    	employee.setPhoneList(phoneList);
    }

    public EmployeeList createEmployeeList(
    		) {
    	EmployeeList eList = new EmployeeList();
    	return eList;
    }
    
    public void putEmployeeList(
    		@ExchangeProperty(value="employeeList") EmployeeList employeelist,
    		@Body List<Employee> employeeL
    		
    		) {
    	employeelist.setEmployeeList(employeeL);
    }
    
}
```

12. Create datasource src/main/resources - OSGI-INF - blueprint - right click - New - Camel XML File - ds-context.xml (OSGI blueprint) - Finish - source
```
<?xml version="1.0" encoding="UTF-8"?>
<blueprint xmlns="http://www.osgi.org/xmlns/blueprint/v1.0.0" xmlns:cm="http://aries.apache.org/blueprint/xmlns/blueprint-cm/v1.0.0">
    <bean class="org.apache.commons.dbcp.BasicDataSource"
        destroy-method="close" id="dsFis2">
        <property name="driverClassName" value="org.postgresql.Driver"/>
        <property name="url" value="jdbc:postgresql://localhost:5432/fis2demo"/>
        <property name="username" value="postgres"/>
        <property name="password" value="postgres"/>
    </bean>
</blueprint>
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
----------
features:install jdbc 
features:install camel-sql 
osgi:install -s mvn:org.postgresql/postgresql/9.4.1212
osgi:install -s mvn:org.jboss.fuse.workshop/fuse-soap/1.0.0-SNAPSHOT

fabric
-------
Open browser http://localhost:8181, login
wiki - new fabric6 profile (sample-fuse-rest)
parent - jboss-fuse-min
bundle mvn:org.postgresql/postgresql/9.4.1212
bundle mvn:org.jboss.fuse.workshop/fuse-rest/1.0.0-SNAPSHOT
feature jdbc
feature camel-sql 


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
