
## LAB 2 - API Masking

We will continue to work on fuse-json project from JSON lab. Put masking on existing API, so it won't show address data. 

Open JBoss Developer Studio application. Continue to work on fuse-json project from JSON lab. If you haven't completed JSON lab1, you can start with this project https://github.com/adithaha/jboss-fuse-workshop/raw/master/json/solution/lab1/fuse-json.zip, import into CodeReady Studio.

### Create http proxy with masking
	
1. Add API backend property, get fuse-rest url from previous REST lab.  
```
oc get route
```
Note the url for fuse-rest application
Open src/main/resources - application.properties - source
```
url.employeeRS=http4://<fuse-rest-url>
```
2. Delete sample route simple-route, open route GUI, Camel Contexts - camel-context.xml
```
Route simple-route - trash icon - Yes
```
3. Create fuse-rest-transformation route, open route GUI, Camel Contexts - camel-context.xml
```
Routing - Route
	Id: fuse-rest-transformation
Component - servlet
	Uri: servlet:fuse-rest?matchOnUriPrefix=true&disableStreamCache=true
Component - Log
	Message: receive request
Component - direct
	Uri: https4://{{url.employeeRS}}?bridgeEndpoint=true&throwExceptionOnFailure=false
Routing - Choice 
(inside Choice) Routing - When
	Expression: simple
	Expression: ${header.CamelHttpMethod} == 'GET' && ${header.CamelHttpPath} regex '\/camel\/jaxrs\/employee\/(.+)'
(inside When) Component - Log
	Message: apply transformation ${header.CamelHttpMethod} ${header.CamelHttpPath}
(inside When) Component - direct
	Uri: jolt:jolt/fuse-rest-getEmployee.json?inputType=JsonString&outputType=JsonString
Component - Log
	Message: send response
```
4. Create JSON transformation file directory - src/main/resources - right click - New - Other - type 'folder'
```
Folder name: jolt
Finish
```

5. Create JSON transformation file using JOLT - src/main/resources - jolt - right click - New - Other - type 'file' - choose Generic/File
```
Folder name: fuse-rest-getEmployee.json
Finish
```
Copy text below in fuse-rest-getEmployee.json file
```
[
  {
    "operation": "remove",
    "spec": {
      "vocabularies": {
        "*": {
          "id": ""
        }
      },
      "uri": ""
    }
  }
]
```
JOLT transformation above will remove element {vocabularies: [array]: {id }} element.  
For JOLT documentation you can go here https://github.com/bazaarvoice/jolt
6. Try your application
```
Build: right click your fuse-json project - run as - maven build - fuse-json clean package - OK
start fuse application: fuse-json - src/main/java - org.jboss.fuse.workshop.json - Application.java (right click) - run as - Java Application
```
Open browser, go to at http://localhost:8080/camel/fuse-rest/camel/jaxrs/employee/1. Compare with output from previous fuse-rest, see if there is any difference.

