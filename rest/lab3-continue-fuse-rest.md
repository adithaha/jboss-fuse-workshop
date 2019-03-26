
## LAB 3 - Continue Fuse REST

In this lab, we will create new additional API service to add employee with multiple employee paramater. We will be reusing existing addEmployee route.

1. Create employeelist API service. Click Source tab.
```
        <rest>
            ...
            <post consumes="application/json" 
                outType="org.jboss.fuse.workshop.soap.EmployeeList"
                type="org.jboss.fuse.workshop.soap.EmployeeList" uri="/employeebulk">
                <to uri="direct:addEmployeeBulk"/>
            </post>...
        </rest>
```
2. Create addEmployeeList route. Click Design tab, drag and drop to create new Route
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
$ oc start-build fuse-rest --from-file=target/fuse-rest-1.0.0-SNAPSHOT.jar --follow
```

