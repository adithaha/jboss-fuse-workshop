
## LAB 3 - Continue Fuse SOAP

1. Create getEmployee route. Click Design tab, drag and drop to create new Route
```
Routing - Route
	ID: addEmployee
Component - Direct
	URI: direct:getEmployee
Transformation - Convert Body To
	Type: Java.lang.Integer
Component - Log
	Message: receive request ${body}
Component - SQL
	URI: sql:select * from employee where id = :#${body}?dataSource=dsEmployee&outputType=SelectOne
Transformation - Set Property
	Expression: simple
	Expression: ${body}
	Property Name: employee
Component - SQL
	URI: sql:select * from phone where employee_id = :#${property.employee.id}?dataSource=dsEmployee&outputType=SelectOne
Component - Bean
	Method: putPhoneList
	Ref: myTransformer
Transformation - Set Body
	Expression: simple
	Expression: ${property.employee}
Component - Log
	Message: send response ${body}
```

2. Create getEmployeeAll route. Click Design tab drag and drop to create new Route

```
Routing - Route
	ID: getEmployeeAll
Component - Direct
	URI: direct: getEmployeeAll
Component - Log
	Message: receive request ${body}
Transformation - Set Property
	Expression: method
	Method: createEmployeeList
	Ref: myTransformer
	Property Name: EmployeeList
Component - SQL - sql:select * from employee?dataSource=dsFis2&amp;outputType=SelectList&amp;outputClass=org.jboss.fis2.demo.soap.Employee
Component - Bean - method: putEmployeeList - ref: myTransformer
Routing - Split - simple - ${property.employeeList.employeeList}
	Transformation - Set Property - simple - employee - expression: ${body}	
	Component - SQL - sql:select * from phone where employee_id = :#${property.employee.id}?dataSource=dsFis2&amp;outputType=SelectList
	Component - Bean - method: putPhoneList - ref: myTransformer	
Transformation - Set Body - simple - ${property.employeeList}
Component - Log - send response ${body}
```

3. Redeploy into openshift
