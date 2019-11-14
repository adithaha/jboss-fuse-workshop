
## LAB 1 - Integrate API


3Scale admin URL: https://github.com/adithaha/workshop-agile-integration/blob/master/openshift-url.md
  
1. Login into 3Scale admin portal
2. (+) New API
   ```
   Name: fuse-workshop-<name>
   System Name: fuse-workshop-<name>
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
   Private Base URL: http://<fuse-json>
   Mapping Rules:
     Delete Get /
     Add Mapping Rules:
       Verb: POST
       Pattern: /camel/fuse-rest/camel/jaxrs/employee
       + : 1
       Metric or Method: add_employee
     Add Mapping Rules:
       Verb: GET
       Pattern: /camel/fuse-rest/camel/jaxrs/employee/{id}
       + : 1
       Metric or Method: get_employee
     Add Mapping Rules:
       Verb: GET
       Pattern: /camel/fuse-rest/camel/jaxrs/employeeall
       + : 1
       Metric or Method: get_employee_all
     Add Mapping Rules:
       Verb: POST
       Pattern: /camel/fuse-rest/camel/jaxrs/employeebulk
       + : 1
       Metric or Method: add_employee_bulk
   API test GET request: /camel/fuse-rest/camel/jaxrs/employeeall
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
6. Create developer account 
   ```
   Choose dropdown Audience
   Account - Listing - Create
     Username: fuse-workshop-<user>
     Email: fuse-workshop-<user>@email.com
     Password: password
     Organization: fuse-workshop-<user>
   ```    

6. Register developer  
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
7. Call API through API Gateway  
   Note API Gateway URL
   ```
   Choose dropdown at top: fuse-workshop-<user>
   Integration - Configuration
   Note API Gateway staging URL
   ```
   Call API
   ```
   Open API Gateway staging URL via browser, add path below with user key from #6:
   /camel/fuse-rest/camel/jaxrs/employeeall?user_key=<user_key>
   ```
   You should be able to get employee data response

