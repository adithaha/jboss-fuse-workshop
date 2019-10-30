
## LAB 4 - Integrate Continued

Open JBoss Developer Studio application, continue to work on fuse-soap project from lab3. If you haven't completed lab3, you can start with this project https://github.com/adithaha/jboss-fuse-workshop/raw/master/soap/solution/lab2/fuse-soap.zip, import into CodeReady Studio, and do lab3 on https://github.com/adithaha/jboss-fuse-workshop/blob/master/soap/lab3-deployment.md

1. Open route camel-context.xml  

2. Create getEmployee route. Click Design tab, drag and drop to create new Route
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
	URI: sql:select * from employee where id = :#${body}?dataSource=dsEmployee&outputType=SelectOne&outputClass=org.jboss.fuse.workshop.soap.Employee
Transformation - Set Property
	Expression: simple
	Expression: ${body}
	Property Name: employee
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

3. Create getEmployeeAll route. Click Design tab drag and drop to create new Route

```
Routing - Route
	ID: getEmployeeAll
Component - Direct
	URI: direct:getEmployeeAll
Component - Log
	Message: receive request ${body}
Transformation - Set Property
	Expression: method
	Method: createEmployeeList
	Ref: myTransformer
	Property Name: employeeList
Component - SQL
	URI: sql:select * from employee?dataSource=dsEmployee&outputType=SelectList&outputClass=org.jboss.fuse.workshop.soap.Employee
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
(inside split) 	Component - SQLsql:select
	URI:  sql:select * from phone where employee_id = :#${property.employee.id}?dataSource=dsEmployee&outputType=SelectList&outputClass=org.jboss.fuse.workshop.soap.Phone
(inside split) 	Component - Bean
	Method: putPhoneList
	Ref: myTransformer	
Transformation - Set Body
	Expression: simple
	Expression: ${property.employeeList}
Component - Log
	Message: send response ${body}
```
4. Try your application
```
Build: right click your fuse-soap project - run as - maven build....
	Goals: clean package
	Run
start fuse application: fuse-soap - src/main/java - org.jboss.fuse.workshop.soap - Application.java (right click) - run as - Java Application
```
Open browser, go to at http://localhost:8080/cxf
```
stop fuse application: go to console tab - click red square on the right
```

5. Redeploy into openshift

Deploy using jar from local client  (SKIP THIS!)
Source code: local  
Build: local
```
$ cd <fuse-soap>
$ mvn clean package
$ oc start-build fuse-soap-<name> --from-file=target/fuse-soap-1.0.0-SNAPSHOT.jar --follow
```

OR

Deploy using source code from local client  
Source code: local  
Build: OpenShift server
```
$ cd <fuse-soap>
$ oc start-build fuse-soap-<name> --from-dir=. --follow
```

### Try your application

1. Get your application route
```
$ oc get route
```
Note the url  

2. Open url from browser, add path /cxf
