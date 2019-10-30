
## LAB 5 - Explore Fuse Application

Open JBoss Developer Studio application,  to work on fuse-soap project from lab4. If you haven't completed lab4, you can start with this project https://github.com/adithaha/jboss-fuse-workshop/raw/master/soap/solution/lab4/fuse-soap.zip and import into CodeReady Studio

#### Import project
If you download fuse-soap.zip, follow procedure below
1. Unzip fuse-soap.zip
2. Open CodeReady Studio
3. File - Import - type 'maven' - choose Existing Maven Project - Browse - Choose fuse-soap folder - check fuse-soap - finish

### Explore Fuse Application
#### Get application WSDL url
1. Get your application route
```
oc get route
```
Note the url  

2. Open url from browser, add path /cxf
3. Click WSDL, copy complete url path from browser, eg. http://fuse-soap-user1-fuse-workshop-user1.apps.1c68.example.opentlc.com/cxf/employeeWS?wsdl

#### Prepare SoapUI testing tools
1. Open SoapUI
2. Close Endpoint Explorer popup
3. File - New SOAP Project
```
Initial WSDL: <wsdl-url>
OK
```
4. Check if EmployeeWSSOAPBinding is imported successfully on left side

#### Try addEmployee
1. Click triangle addEmployee on left side - Request 1
2. Replace request with below
```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap="http://soap.workshop.fuse.jboss.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:addEmployee>
         <soap:arg0>
            <address>jakarta</address>
            <name>bejo</name>
            <phoneList>
               <phone>021111111</phone>
               <type>home</type>
            </phoneList>
         </soap:arg0>
      </soap:addEmployee>
   </soapenv:Body>
</soapenv:Envelope>
```
3. Click green triangle on top left to send request, you should receive response with additional id parameter, eg. 1

#### Try getEmployee
1. Click triangle getEmployee on left side - Request 1
2. Add parameter id as generated from previous response
```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap="http://soap.workshop.fuse.jboss.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:getEmployee>
         <soap:arg0>1</soap:arg0>
      </soap:getEmployee>
   </soapenv:Body>
</soapenv:Envelope>
```
3. Click green triangle on top left to send request, you should receive response with employee <id> data

#### Try getEmployeeAll
1. Click triangle getEmployeeAll on left side - Request 1
2. This method doesn't need additional parameter, leave current request as it is
3. Click green triangle on top left to send request, you should receive response with all employee data
