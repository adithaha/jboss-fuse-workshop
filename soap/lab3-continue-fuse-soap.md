
## LAB 3 - Continue Fuse SOAP

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

19. Redeploy into openshift
