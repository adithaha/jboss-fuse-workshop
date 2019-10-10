
## LAB 3 - Consume request from AMQ

In this lab, we will create new AMQ consumer to add employee using existing addEmployee method.

### Create message consumer

1. Create new camel route
```
src/main/resources - spring - right click - New - Camel XML File
File name: employee-amq-context.xml
Finish

4. Create addEmployeeConsumer route. Click Design tab, drag and drop to create new Route
```
Routing - Route
	ID: addEmployeeConsumer
Component - Direct
	Uri: amqp:queue:employeeQueue
Transformation - Convert Body To
	Type: org.jboss.fuse.workshop.soap.Employee
  
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
