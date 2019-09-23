
## LAB 1 - Create Fuse Soap Integration Project

Open JBoss Developer Studio application

1. File - New - Project.. - type 'fuse' - Fuse Integration Project - Next
 ```
Project-name: fuse-soap
Next
Deployment Platform: Standalone
Runtime Environment: Spring Boot
Camel version: 2.21.0.fuse-731003-redhat-00003 (Fuse 7.3.1 GA)
Next
Simple log using Spring Boot - Spring DSL
Finish
Open Associate Perspective: Yes
 ```

2. Change groupId in pom.xml - fuse-soap - pom.xml

Replace below lines #4 - #9
 ``` 
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.jboss.fuse.workshop</groupId>
  <artifactId>fuse-soap</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <name>Workshop:: Fuse SOAP</name>
  <description>Workshop:: Fuse SOAP</description>
  ```
  
Add below lines #28 starting with <dependency>
   ``` 
  <project>
    ...
      <dependencies>
      ...
	    <dependency>
	      <groupId>org.springframework.boot</groupId>
	      <artifactId>spring-boot-starter-jdbc</artifactId>
	    </dependency>
	    <dependency>
	      <groupId>org.apache.camel</groupId>
	      <artifactId>camel-cxf-starter</artifactId>
	    </dependency>
	    <dependency>
	      <groupId>org.apache.camel</groupId>
	      <artifactId>camel-jaxb-starter</artifactId>
	    </dependency>
	    <dependency>
	      <groupId>org.apache.camel</groupId>
	      <artifactId>camel-sql-starter</artifactId>
	    </dependency>
	    <dependency>
	      <groupId>org.postgresql</groupId>
	      <artifactId>postgresql</artifactId>
	      <version>9.4.1212</version>
	    </dependency>
  	</dependencies>
  
  ```
3. Build to download dependencies. Go to terminal
  ```
  $ cd <fuse-soap>
  $ mvn clean package
  ```

3. Change default package src/main/java - org.mycompany - right click - refactor - rename
	- New Name: org.jboss.fuse.workshop.soap
	- Continue

4. Create phone model class src/main/java - org.jboss.fuse.workshop.soap - right click - new - class
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
5. Create employee model class src/main/java - org.jboss.fuse.workshop.soap - right click - new - class
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
6. Create employee list model class src/main/java - org.jboss.fuse.workshop.soap - right click - new - class
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
7. Create Transformer class src/main/java - org.jboss.fuse.workshop.soap - right click - new - class
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

9. Add datasource properties - src/main/resources - application.properties(open) - source - add line below
```
spring.dsEmployee.url=jdbc:postgresql://localhost:5432/dsEmployee
spring.dsEmployee.username=postgres
spring.dsEmployee.password=postgres
spring.dsEmployee.driver-class-name=org.postgresql.Driver
spring.dsEmployee.validation-query=SELECT 1
spring.dsEmployee.max-active=10
spring.dsEmployee.max-idle=8
spring.dsEmployee.min-idle=8
spring.dsEmployee.initial-size=5
```

10. Inject datasource and CXF servlet - src/main/java - org.jboss.fuse.workshop.soap - Application.java (open)

Inject datasource into Spring Boot application (line #33)
```
	@Bean(name = "dsEmployee")
	@ConfigurationProperties(prefix = "spring.dsEmployee")
	public DataSource dsEmployeeDataSource() {
		return DataSourceBuilder.create().build();
	}
```

Add import below (line #21):
```
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
```
Register CXF servlet (line #46)

```
	@Bean
    	ServletRegistrationBean servletRegistrationBeanCXF() {
        	ServletRegistrationBean servlet = new ServletRegistrationBean(new CXFServlet(), "/cxf/*");
        	servlet.setName("CXFServlet");
        	return servlet;
    	}
```

Add import below (line #25)
```
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
```

Final content Application.java
```
/**
 *  Copyright 2005-2018 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.jboss.fuse.workshop.soap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

/**
 * A spring-boot application that includes a Camel route builder to setup the Camel routes
 */
@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class Application {

    // must have a main method spring-boot can run
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
	@Bean(name = "dsEmployee")
	@ConfigurationProperties(prefix = "spring.dsEmployee")
	public DataSource dsEmployeeDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	ServletRegistrationBean servletRegistrationBeanCXF() {
    	ServletRegistrationBean servlet = new ServletRegistrationBean(new CXFServlet(), "/cxf/*");
    	servlet.setName("CXFServlet");
    	return servlet;
	}
}
```

12. replace default route src/main/resources - spring - camel-context.xml (open) - source
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
    <bean class="org.jboss.fuse.workshop.soap.MyTransformer" id="myTransformer"/>
    <camelContext id="camel" xmlns="http://camel.apache.org/schema/spring">
        <route id="employeeWS"/>
    </camelContext>
</beans>

```
14. Compile your application
```
Clean build: right click your fuse-soap project - run as - maven clean
Build: right click your fuse-soap project - run as - maven build....
	Goals: clean package
	Run
The application should be compiled successfully
```


