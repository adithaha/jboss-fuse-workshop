
## LAB 2 - Try addEmployeeBulk

In this lab, we will try out addEmployeeBulk method we created in lab1 earlier.

#### Get application apidocs url
1. Get your application route
```
oc get route
```
Note the fuse-rest url  

2. Open url from browser, add path /camel/api-docs
3. Copy complete url path from browser, eg. http://fuse-rest-user1-fuse-workshop-user1.apps.1c68.example.opentlc.com/camel/api-docs

#### Prepare Postman testing tools
1. Open Postman
2. Import - Import from link - <apidocs-url> - Import

#### Try addEmployeeBulk
1. Click Red Hat Fuse - REST on left side - jaxrs - POST (4th line)
2. Replace IP on URL from 0.0.0.0 to your fuse-rest domain name, eg. fuse-rest-user1-fuse-workshop-user1.apps.1c68.example.opentlc.com
2. Replace Body with below
```
{
    "employeeList": [
        {
            "address": "bandung",
            "name": "oji",
            "phoneList": [
                {
                    "phone": "0815555555",
                    "type": "hp"
                }
            ]
        },
        {
            "address": "bogor",
            "name": "siti",
            "phoneList": [
                {
                    "phone": "0812222222",
                    "type": "hp"
                }
            ]
        }
    ]
}
```
4. SEND, you should receive response with ACK message
