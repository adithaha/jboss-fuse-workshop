
## LAB 4 - Explore Fuse Application

Open JBoss Developer Studio application,  to work on fuse-soap project from lab3. If you haven't completed lab3, you can start with this project https://github.com/adithaha/jboss-fuse-workshop/raw/master/rest/solution/lab3/fuse-rest.zip and import into CodeReady Studio

#### Import project
If you download fuse-soap.zip, follow procedure below
1. Unzip fuse-rest.zip
2. Open CodeReady Studio
3. File - Import - type 'maven' - choose Existing Maven Project - Browse - Choose fuse-rest folder - check fuse-soap - finish

### Explore Fuse Application
#### Get application apidocs url
1. Get your application route
```
oc get route
```
Note the fuse-rest url  

2. Open url from browser, add path /camel/api-docs
3. Copy complete url path from browser, eg. http://fuse-rest-user1-fuse-workshop-user1.apps.1c68.example.opentlc.com/cxf/employeeWS?wsdl

#### Prepare Postman testing tools
1. Open Postman
2. Import - Import from link - <apidocs-url> - Import

#### Try addEmployee
1. Click Red Hat Fuse - REST on left side - jaxrs - POST (3rd line)
2. Replace IP on URL from 0.0.0.0 to your fuse-rest domain name, eg. fuse-rest-user1-fuse-workshop-user1.apps.1c68.example.opentlc.com
2. Replace Body with below
```
{
    "address": "bekasi",
    "name": "otong",
    "phoneList": [
        {
            "phone": "0811111111",
            "type": "HP"
        }
    ]
}
```
4. SEND, you should receive response with additional id parameter, eg. 1

#### Try getEmployee
1. Click Red Hat Fuse - REST on left side - jaxrs - GET (2nd line)
2. Replace IP on URL from 0.0.0.0 to your fuse-rest domain name, eg. fuse-soap-user1-fuse-workshop-user1.apps.1c68.example.opentlc.com
3. Replace :arg0 in URL with id, eg. 1
4. SEND, you should receive response with employee <id> data

#### Try getEmployeeAll
1. Click Red Hat Fuse - REST on left side - jaxrs - GET (3rd line)
2. Replace IP on URL from 0.0.0.0 to your fuse-rest domain name, eg. fuse-soap-user1-fuse-workshop-user1.apps.1c68.example.opentlc.com
3. This method doesn't need additional parameter, leave current request as it is
4. SEND, you should receive response with all employee data

#### Explore Fuse Console
Fuse application can be viewed using Fuse console. 
1. Open OpenShift via browser
2. Open fuse-workshop-<user1>
3. Click fuse-rest-<user> (1) pod, you will see container detail
4. Click Open Java Console
5. Explore main console, you can see routes with their statisti runtime data
6. Click Routes - addEmployee, you will be able to see addEmployee camel routes
7. You can explore other tabs/routes to see fuse console capabilities
