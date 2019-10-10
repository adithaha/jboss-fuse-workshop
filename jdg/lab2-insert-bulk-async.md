
## LAB 2 - Create add employee bulk asynchronous

In this lab, we will create new additional API service to add employee with multiple employee paramater. We will reuse existing addEmployee method, with asynchronous approach.

1. Open rest-springboot-context.xml. Create employeebulk API service. Click REST tab.
```
REST Operations +
URI: /employeebulk
Operation Type: POST
Finish
```
2. Configure employeebulk definition
```
Type: org.jboss.fuse.workshop.soap.EmployeeList 
Out Type: org.jboss.fuse.workshop.soap.EmployeeList
To URI: direct:addEmployeeBulk
```
2. Create addEmployeeBulk route. Click Design tab, drag and drop to create new Route
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
		Uri: direct:addEmployee
Transformation - Set Body
	Expression: simple
	Expression: ${property.employeeList}
```
3. Redeploy application
```
$ cd <fuse-rest>
$ mvn clean package
$ oc start-build fuse-rest-<name> --from-file=target/fuse-rest-1.0.0-SNAPSHOT.jar --follow
```

