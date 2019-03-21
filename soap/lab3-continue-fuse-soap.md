
## LAB 3 - Continue Fuse SOAP

1. 

Create getEmployee route. Click Design tab, drag and drop to create new Route
```
Routing - Route
	ID: getEmployee
Component - Direct
	URI: direct:getEmployee
Transformation - Convert Body To
	Type: java.lang.Integer
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
	Property Name: employeeList
Component - SQL
	URI: sql:select * from employee?dataSource=dsFis2&amp;outputType=SelectList&outputClass=org.jboss.fis2.demo.soap.Employee
Component - Bean
	Method: putEmployeeList
	Ref: myTransformer
Routing - Split
	Expression: simple
	Expression: ${property.employeeList.employeeList}
(inside split) Transformation - Set Property
	Expression: simple
	Expression: ${body}
	Property Name: employee
(inside split) 	Component - SQL
	URI: sql:select * from phone where employee_id = :#${property.employee.id}?dataSource=dsFis2&amp;outputType=SelectList
(inside split) 	Component - Bean
	Method: putPhoneList
	Ref: myTransformer	
Transformation - Set Body
	Expression: simple
	Expression: ${property.employeeList}
Component - Log
	Message: send response ${body}
```

3. Redeploy into openshift
