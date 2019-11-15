
## LAB 1 - Integrate API


3Scale admin URL: https://anugraha-admin.3scale.net
  
1. Login into 3Scale admin portal
2. (+) New API
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
   Test might fail as we haven't registered user yet, we can ignore that for now
   ```
5. Applications > Application Plan
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
   Click Premium - Metrics, Methods, Limits & Pricing Rules 
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
6. Subscriptions > Service Plans - Publish
<!--
7. Create developer account 
   ```
   Choose dropdown Audience
   Account - Listing - Create
     Username: fuse-workshop-<user>
     Email: fuse-workshop-<user>@email.com
     Password: password
     Organization: fuse-workshop-<user>
   ```    

8. Register developer  
   We will use the account to be registered with application plan  
   Register with Basic plan
   ```
   Choose dropdown Audience
   Account - Listing
   Click fuse-workshop-<user> - Applications - (+) Create Application
     Application Plan: Choose - fuse-workshop-<name> - Basic 
     Name: fuse-workshop-<user>-basic
     Description: fuse-workshop-<user>-basic
     Create Application
   Note the User Key eg. 4567d96a9a0d34b590d1b93f92397a79
   ```
-->
8. Subscribe API using existing developer
   Go to developer portal, login using existing user (from yesterday demo) https://anugraha.3scale.net
   ```
   Tab Services
   Subscribe to fuse-workshop-<user>
   Click fuse-workshop-<user> - Applications - (+) Create Application
     Application Plan: Choose - fuse-workshop-<name> - Basic 
     Name: fuse-workshop-<user>-basic
     Description: fuse-workshop-<user>-basic
     Create Application
   Note the User Key eg. 4567d96a9a0d34b590d1b93f92397a79
   ```
   Create application plan
   ```
   APPLICATIONS -> Create new applications -> select finto
   Plan: choose Basic or Premium
   Name: <user>-api
   Description: <user>-api
   Create Application
   Note down your unique user key (eg. bb629d06ad6bf40c736a735a315836cba)
   ```

9. Call API through API Gateway  
   Open browser, open link below:
   
   https://fuse-workshop-<user>-2445581864856.staging.gw.apicast.io/camel/jaxrs/employeeall?user_key=<user-key>
   
   <!--
   Go back to Admin Portal, note API Gateway URL
   ```
   Choose dropdown at top: fuse-workshop-<user>
   Integration - Configuration
   Note API Gateway staging URL
   ```
   Call API
   ```
   Open API Gateway staging URL via browser, add path below with user key from #6:
   /camel/jaxrs/employeeall?user_key=<user_key>
   ```
   You should be able to get employee data response
-->
