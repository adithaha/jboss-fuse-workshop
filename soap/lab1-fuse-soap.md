
## LAB 1 - Installasi JBoss Fuse dengan fabric

0. Window - Show View - Other - Terminal - Terminal - Open
0. Tab terminal - icon terminal - Local Terminal - OK

1. File - New - Project.. - type 'fuse' - Fuse Integration Project - Project-name: fuse-soap - 2.21.0.fuse-710018-redhat-00001 (Fuse 7.1.0 GA) - Simple log using Spring Boot - Spring DSL - Finish - Open Associate Perspective: Yes
2. Change groupId in pom.xml - fuse-soap - pom.xml
 ``` 
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.jboss.fuse.workshop</groupId>
  <artifactId>fuse-soap</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <name>Workshop:: Fuse SOAP</name>
  <description>Workshop:: Fuse SOAP</name>
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
      <artifactId>camel-sql-starter</artifactId>
    </dependency>
  </dependencies>
  
  ```
3. Change default package src/main/java - org.mycompany - right click - refactor - rename
	- New Name: org.jboss.fuse.workshop.soap

3. Create phone model class src/main/java - right click - new - class
	- package org.jboss.fuse.workshop.soap
	- Name Phone
```
package org.jboss.fuse.workshop.soap;

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
6. Create employee model class src/main/java - org.jboss.fuse.workshop.soap - right click - new - class
	- package org.jboss.fuse.workshop.soap
	- Name Employee
```
package org.jboss.fuse.workshop.soap;

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
7. Create employee list model class src/main/java - org.jboss.fuse.workshop.soap - right click - new - class
	- package org.jboss.fuse.workshop.soap
	- Name EmployeeList
```
package org.jboss.fuse.workshop.soap;

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
8. Create Transformer class src/main/java - org.jboss.fuse.workshop.soap - right click - new - class
	- package org.jboss.fuse.workshop.soap
	- Name MyTransformer


```
package org.jboss.fuse.workshop.soap;

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


8. Create SOAP service class src/main/java - org.jboss.fuse.workshop.soap - right click - new - interface
	- package org.jboss.fuse.workshop.soap
	- Name EmployeeWS
```
package org.jboss.fuse.workshop.soap;


public interface EmployeeWS {

	public Employee addEmployee(Employee employee);
	public Employee getEmployee(Integer id);
	public EmployeeList getEmployeeAll();
	
}
```

11. Create datasource - src/main/resources - application.properties - Finish - source - add line below
```
spring.dsEmployee.url=jdbc:postgresql://10.1.2.2:30432/fis2demo
spring.dsEmployee.username=postgres
spring.dsEmployee.password=postgres
spring.dsEmployee.driver-class-name=org.postgresql.Driver
spring.dsEmployee.validation-query=SELECT 1
spring.dsEmployee.max-active=10
spring.dsEmployee.max-idle=8
spring.dsEmployee.min-idle=8
spring.dsEmployee.initial-size=5
```

12. Inject datasource into Spring Boot application - src/main/java - org.jboss.fuse.workshop.soap - Application.java

```
	@Bean(name = "dsEmployee")
	@ConfigurationProperties(prefix = "spring.dsEmployee")
	public DataSource mysqlDataSource() {
		return DataSourceBuilder.create().build();
	}
```

Add import below:
```
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
```
9. Remove default route src/main/resources - spring - camel-context.xml

12. Create route src/main/resources - spring - right click - New - Camel XML File - camel-context.xml (Spring) - Finish - source
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:camel="http://camel.apache.org/schema/spring"
    xmlns:cxf="http://camel.apache.org/schema/cxf"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd        http://camel.apache.org/schema/spring http://camel.apache.org/schema/spring/camel-spring.xsd http://camel.apache.org/schema/cxf     http://camel.apache.org/schema/cxf/camel-cxf.xsd">
    <cxf:cxfEndpoint address="/employeeWS" id="employeeWS" serviceClass="org.jboss.fuse.workshop.soap.EmployeeWS">
        <cxf:properties>
            <entry key="dataFormat" value="POJO"/>
            <entry key="faultStackTraceEnabled" value="true"/>
            <entry key="loggingFeatureEnabled" value="false"/>
        </cxf:properties>
    </cxf:cxfEndpoint>
    <camelContext id="camel" xmlns="http://camel.apache.org/schema/spring">
        <route id="employeeWS"/>
    </camelContext>
</beans>

```
13. Create webservice route. Click Design tab, drag and drop to Route employeeWS
```
Components - CXF
	URI: cxf:bean:employeeWS
Transformation - Remove Header
	Header Name: SOAPAction
Routing - Recipient List
	Expressions: simple 
	Expressions: direct:${header.operationName}
```

14. Create addEmployee route. Click Design tab, drag and drop to create new Route
```
Routing - Route
	Id: addEmployee
Component - Direct
	Uri: direct:addEmployee
Transformation - Convert Body To
	Type: org.jboss.fuse.workshop.soap.Employee
Component - Log
	Message: receive request ${body}
Transformation - Set Property
	Expression: simple
	Expression: ${body}
	Property Name: employee
Transformation - Set Header
	Expression: constant 
	Expression: true
	Header Name: CamelSqlRetrieveGeneratedKeys
Component - SQL
	Uri: sql:insert into employee (name, address) values (:#${body.name}, :#${body.address})?dataSource=dsEmployee&outputType=SelectOne
Transformation - Transform
	Expression: simple
	Expression: ${property.employee.setId(${header.CamelSqlGeneratedKeyRows[0][id]})}
Routing - Split
	Expression: simple
	Expression: ${property.employee.phoneList}
Component - Log (put inside Split block) 
	Message: phone: ${body}
Component - SQL (put inside Split block) 
	Uri: sql:insert into phone (employee_id, phone, type) values (:#${property.employee.id}, :#${body.phone}, :#${body.type})?dataSource=dsEmployee&outputType=SelectOne
Transformation - Set Body
	Expression: simple
	Expression: ${property.employee}
Component - Log
	Message: send response ${body}
```
15. Deploy into Fuse (assumed fuse is already started)

features:install jdbc 
features:install camel-sql 
osgi:install -s mvn:org.postgresql/postgresql/9.4.1212
osgi:install -s mvn:org.jboss.fuse.workshop/fuse-soap/1.0.0-SNAPSHOT


16. Create getEmployee route. Click Design tab
```
Routing - Route
Component - Direct - direct:getEmployee
Transformation - Convert Body To - java.lang.Integer
Component - Log - receive request ${body}
Component - SQL - sql:select * from employee where id = :#${body}?dataSource=dsFis2&amp;outputType=SelectOne
Transformation - Set Property - simple - employee - expression: ${body}
Component - SQL - sql:select * from phone where employee_id = :#${property.employee.id}?dataSource=dsFis2&amp;outputType=SelectOne
Component - Bean - method: putPhoneList - ref: myTransformer
Transformation - Set Body - simple - expression: ${property.employee}
Component - Log - send response ${body}
```
17. Redeploy into Fuse (assumed fuse is already started)

osgi:list

#get bundle id

osgi:update <id>
osgi:refresh <id>

18. Create getEmployeeAll route. Click Design tab

```
Routing - Route
Component - Direct - direct: getEmployeeAll
Component - Log - receive request ${body}
Transformation - Set Property - method - name:employeeList - ref:myTransformer - method:createEmployeeList
Component - SQL - sql:select * from employee?dataSource=dsFis2&amp;outputType=SelectList&amp;outputClass=org.jboss.fis2.demo.soap.Employee
Component - Bean - method: putEmployeeList - ref: myTransformer
Routing - Split - simple - ${property.employeeList.employeeList}
	Transformation - Set Property - simple - employee - expression: ${body}	
	Component - SQL - sql:select * from phone where employee_id = :#${property.employee.id}?dataSource=dsFis2&amp;outputType=SelectList
	Component - Bean - method: putPhoneList - ref: myTransformer	
Transformation - Set Body - simple - ${property.employeeList}
Component - Log - send response ${body}
```

19. Redeploy into Fuse (assumed fuse is already started)

osgi:list

#get bundle id

osgi:update <id>
osgi:refresh <id>
