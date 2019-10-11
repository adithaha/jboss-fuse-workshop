
## LAB 2 - Using AMQ to add employee in bulk asynchronously

We will continue to work on fuse-rest project from REST lab. Create new additional API service to add multiple employee reuse existing addEmployee method, with asynchronous approach.  

Open JBoss Developer Studio application. Continue to work on fuse-rest project from JDG lab. If you haven't completed JDG lab, you can start with this project https://github.com/adithaha/jboss-fuse-workshop/raw/master/jdg/solution/lab1/fuse-rest.zip, import into CodeReady Studio, and do lab3 on https://github.com/adithaha/jboss-fuse-workshop/blob/master/rest/lab3-deployment.md

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

1. Open fuse-rest - pom.xml, source. Add Camel-AMQP component dependency
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
fuse-rest - Camel Contexts - right click - New Camel XML File
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

4. Create addEmployeeBulk route in Camel Contexts - rest-springboot-context.xml. Click Design tab, drag and drop to create new Route
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
	Pattern: InOnly
Transformation - Set Body
	Expression: constant
	Expression: { "message": "Request is being processed" }
```

5. Create addEmployeeConsumer route in Camel Contexts - rest-springboot-context.xml. Click Design tab, drag and drop to create new Route
```
Routing - Route
	ID: addEmployeeConsumer
Component - Direct
	Uri: amqp:queue:employeeQueue?concurrentConsumers=5
Transformation - Convert Body To
	Type: org.jboss.fuse.workshop.soap.Employee
Component - Direct
	Uri: direct:addEmployee
```
6. Open rest-springboot-context.xml. Create employeebulk API service. Click REST tab.
```
REST Operations +
URI: /employeebulk
Operation Type: post
Finish
```
7. Configure employeebulk definition
```
REST Operations - post /employeebulk
Type: org.jboss.fuse.workshop.soap.EmployeeList 
To URI: direct:addEmployeeBulk

* if you cannot select direct:addEmployeeBulk, save rest-springboot-context.xml - close - open again
```
8. Try on local
```
$ cd <fuse-rest>
$ mvn clean spring-boot:run
```
Check if url below can be accessed:

http://localhost:8080/camel/api-docs

9. Redeploy into openshift

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
