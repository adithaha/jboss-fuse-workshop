
## LAB 1 - Integrate API


### Configure API Management
3Scale admin URL: https://anugraha-admin.3scale.net
  
1. Login into 3Scale admin portal
2. (+) New API (trainer may have created this for you)
   ```
   Name: fuse-workshop-<user>
   System Name: fuse-workshop-<user>
   Add API
   ```
3. Integration > Methods & Metrics 
   ```
   Method - New method  
     Friendly name: Add Employee  
     System name: add_employee  
     Create Method  
   Method - New method  
     Friendly name: Get Employee  
     System name: get_employee  
     Create Method  
   Method - New method  
     Friendly name: Get Employee All  
     System name: get_employee_all  
     Create Method  
   Method - New method  
     Friendly name: Add Employee Bulk  
     System name: add_employee_bulk 
     Create Method  
   ```
4. Integration > Configuration - add the base URL of your API and save the configuration.
   ```
   Private Base URL: https://fuse-rest-user99-fuse-workshop-user99.apps.be01.example.opentlc.com
   Mapping Rules:
     Delete Get /
     Add Mapping Rules:
       Verb: POST
       Pattern: /camel/jaxrs/employee
       + : 1
       Metric or Method: add_employee
     Add Mapping Rules:
       Verb: GET
       Pattern: /camel/jaxrs/employee/{id}
       + : 1
       Metric or Method: get_employee
     Add Mapping Rules:
       Verb: GET
       Pattern: /camel/jaxrs/employeeall
       + : 1
       Metric or Method: get_employee_all
     Add Mapping Rules:
       Verb: POST
       Pattern: /camel/jaxrs/employeebulk
       + : 1
       Metric or Method: add_employee_bulk
   API test GET request: /camel/jaxrs/employeeall
   Update and Test in Staging Environment 
   
   Test will fail as we haven't registered user yet, we can ignore that for now
   ```
5. Publish default service plan
   ```
   Subscriptions > Service Plans > Default - Publish
   ```
6. Applications > Application Plan
   Create Basic plan
   ```
   Create Application Plan
     Name: Basic
     System Name: basic
     Create Application Plan
   Publish
   Click Basic - Metrics, Methods, Limits & Pricing Rules 
     get_employee - Limits - New usage limit
       Period: Minute
       Max. value: 60
       Create usage limit
     get_employee_all - Limits - New usage limit
       Period: Minute
       Max. value: 60
       Create usage limit
     add_employee - disable
   ```
   Create Premium plan
   ```
   Create Application Plan
     Name: Premium
     System Name: premium
     Create Application Plan
   Publish
   Click Premium
   > Applications require approval
   > Metrics, Methods, Limits & Pricing Rules 
     get_employee - Limits - New usage limit
       Period: Minute
       Max. value: 600
       Create usage limit
     get_employee_all - Limits - New usage limit
       Period: Minute
       Max. value: 600
       Create usage limit
     add_employee - Limits - New usage limit
       Period: Minute
       Max. value: 60
       Create usage limit
     add_employee_bulk - Limits - New usage limit
       Period: Minute
       Max. value: 30
       Create usage limit
    ```
7. Promote to Production
```
Integration > Configuration > Promote to Production 
```
