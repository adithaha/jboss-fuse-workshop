
## LAB 2 - Using AMQ to add employee in bulk asynchronously

In this lab, we will create new additional API service to add employee with multiple employee paramater. We will reuse existing addEmployee method, with asynchronous approach.


### Deploy JBoss AMQ Broker

1. Go to openshift web console, login with your user
2. Go to project 
```
fuse-workshop-<name>
```
3. Deploy JBoss AMQ Broker using template
```
Add to Project - Browse Catalog - filter - type 'amq 7.3' - choose Red Hat AMQ Broker 7.3 (Ephemeral, no SSL)
Next
Queues: employeeQueue
AMQ Username: amq
AMQ Password: amq
Create
```	
4. JBoss AMQ Broker will be deployed. Notice there are 5 network service exposed, we will be using amqp later. Note the service name and port - broker-amq-amqp:5672

### Create add employee bulk asynchronous

1. Open pom.xml, source. Add Camel-AMQP component dependency
``` 
      ...
      <artifactId>camel-swagger-java-starter</artifactId>
    </dependency>
    <dependency>
      <groupId>org.apache.camel</groupId>
      <artifactId>camel-amqp-starter</artifactId>
    </dependency>
    ...
```   
  

2. Create amqp connector. 
```
src/main/resources - spring - right click - New - Camel XML File
File name: amqp-context.xml
Finish
```
Open that file, tab source. Replace all its content with below
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
 <bean id="amqp" class="org.apache.camel.component.jms.JmsComponent">
        <property name="connectionFactory">
            <bean class="org.apache.qpid.jms.JmsConnectionFactory" >
	            <constructor-arg index="0"  value="${amq.user}" />
	            <constructor-arg index="1" value="${amq.pass}" />
	            <constructor-arg index="2" value="${amq.url}" />
            </bean>
        </property>
    </bean>
	
</beans>

```
3. Add AMQ connection properties, put url from previous step. Open src/main/resources - application.properties - source
```
amq.user=amq
amq.password=amq
amq.url=amqp://broker-amq-amqp:5672
```

4. Create addEmployeeBulk route. Click Design tab, drag and drop to create new Route
```
Routing - Route
	ID: addEmployeeBulk
Component - Direct
	Uri: direct:addEmployeeBulk
Component - Log
	Message: addEmployeeBulk
Transformation - Set Property
	Expression: simple
	Expression: ${body}
	Property Name: employeeList
Routing - Split
	Expression: simple
	Expression: ${property.employeeList.employeeList}
(inside split) Component - Direct
		Uri: amqp:queue:employeeQueue
Transformation - Set Body
	Expression: constant
	Expression: { "message": "Request is being processed" }
```

5. Open rest-springboot-context.xml. Create employeebulk API service. Click REST tab.
```
REST Operations +
URI: /employeebulk
Operation Type: POST
Finish
```
6. Configure employeebulk definition
```
Type: org.jboss.fuse.workshop.soap.EmployeeList 
To URI: direct:addEmployeeBulk
```
