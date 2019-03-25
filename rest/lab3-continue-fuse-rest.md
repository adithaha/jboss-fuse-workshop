
## LAB 3 - Continue Fuse SOAP

In this lab, we will create new additional API service to add employee with multiple employee paramater. We will be reusing existing addEmployee route.

1. Create employeelist API service. Click Source tab.
```
        <rest>
            ...
            <post consumes="application/json" 
                outType="org.jboss.fuse.workshop.soap.EmployeeList"
                type="org.jboss.fuse.workshop.soap.EmployeeList" uri="/employeelist">
                <to uri="direct:addEmployeeList"/>
            </post>...
        </rest>
```
12 Create addEmployeeList route. Click Design tab, drag and drop to create new Route
```
Routing - Route
	ID: addEmployeeBulk
Transformation - Set Property
	Expression: simple
	Expression: ${body}
	Property Name: employeeList


  
Component - Direct
	URI: direct:addEmployeeList
Transformation - Convert Body To
	Type: java.lang.Integer
Component - Log
	Message: receive request ${body}
Component - SQL
	URI: sql:select * from employee where id = :#${body}?dataSource=dsEmployee&outputType=SelectOne&outputClass=org.jboss.fuse.workshop.soap.Employee
Component - SQL
	URI: sql:select * from phone where employee_id = :#${property.employee.id}?dataSource=dsEmployee&outputType=SelectList&outputClass=org.jboss.fuse.workshop.soap.Phone
Component - Bean
	Method: putPhoneList
	Ref: myTransformer
Transformation - Set Body
	Expression: simple
	Expression: ${property.employee}
Component - Log
	Message: send response ${body}
```
