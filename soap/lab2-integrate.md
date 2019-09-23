
## LAB 2 - Create Fuse Soap Integration Project

Open JBoss Developer Studio application, continue to work on fuse-soap project from lab1. If you haven't completed lab1, you can start with this project https://github.com/adithaha/jboss-fuse-workshop/raw/master/soap/solution/lab1/fuse-soap.zip

1.  Create webservice route. Click Design tab, drag and drop to Route employeeWS
```
Components - CXF
	URI: cxf:bean:employeeWS
Transformation - Remove Header
	Header Name: SOAPAction
Routing - Recipient List
	Expression: simple 
	Expression: direct:${header.operationName}
```

2. Create addEmployee route. Click Design tab, drag and drop to create new Route
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
3. Try your application
```
Clean build: right click your fuse-soap project - run as - maven clean
Build: right click your fuse-soap project - run as - maven build....
	Goals: clean package
	Run
start fuse application: fuse-soap - src/main/java - org.jboss.fuse.workshop.soap - Application.java (right click) - run as - Java Application
```
Open browser, go to at http://localhost:8080/cxf
```
stop fuse application: go to console tab - click red square on the right
```


