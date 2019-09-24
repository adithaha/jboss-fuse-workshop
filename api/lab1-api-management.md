
## LAB 1 - Integrate API


3Scale admin URL: https://3scale-admin.api.ocpapps.bkpm.go.id  (admin/admin)
Backend API: http://fuse-rest-#-fuse-workshop-#.ocpapps.bkpm.go.id  
  
1. Login into 3Scale admin portal
2. + New API
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
   ```
4. Integration > Configuration - add the base URL of your API and save the configuration.
   ```
   Private Base URL: http://fuse-rest-#-fuse-workshop-#.ocpapps.bkpm.go.id
   Mapping Rules:
     Delete Get /
     Add Mapping Rules:
       Verb: POST xxx
       Pattern: /rest/v1/vocabularies xxx
       + : 1
       Metric or Method: add_employee
     Add Mapping Rules:
       Verb: POST xxx
       Pattern: /rest/v1/types xxx
       + : 1
       Metric or Method: get_employee
     Add Mapping Rules:
       Verb: GET xxx
       Pattern: /rest/v1/types xxx
       + : 1
       Metric or Method: get_employee_all
   API test GET request: /rest/v1/vocabularies xxx
   Update and Test in Staging Environment
   ```
5. Applications > Application Plan
   ```
   Create Application Plan
     Name: Basic
     System Name: basic
     Create Application Plan
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
   Create Application Plan
     Name: Premium
     System Name: premium
     Create Application Plan
     Click Basic - Metrics, Methods, Limits & Pricing Rules 
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
    ```
       

```
xxxxx
```
