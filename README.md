# Agile Integration Workshop

Workshop untuk Agile Integration
- Red Hat Fuse
- Red Hat OpenShift Container Platform
- Red Hat AMQ
- Red Hat Data Grid
- Red Hat 3Scale API Management

### Requirement

JDK 8
-----
install:  
<java>.exe - click

check:  
cmd-"java -version"

output:  
java version "1.8.0_151"  
Java(TM) SE Runtime Environment (build 1.8.0_151-b12)  
Java HotSpot(TM) 64-Bit Server VM (build 25.151-b12, mixed mode)  

Maven
-----
link maven:   
http://www-us.apache.org/dist/maven/maven-3/3.6.2/binaries/apache-maven-3.6.2-bin.zip

install:  
unzip <maven>.zip  
tambah ke system path: <maven>/bin  

check:  
cmd -> "mvn -version"

output:  
Apache Maven 3.6.1 (d66c9c0b3152b2e69ee9bac180bb8fcc8e6af555; 2019-04-05T02:00:29+07:00)
Maven home: /Users/adityanugraha/workspace/tools/apache-maven-3.6.1
Java version: 1.8.0_151, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.13.6", arch: "x86_64", family: "mac"

CodeReady Studio
----------------
install:  
cmd - "java -jar jboss.jar" - select "Red Hat Fuse Tooling"

check:  
open Codeready Studio


OpenShift Client
----------------
install:  
mkdir oc-client
unzip oc-3.11.98-windows.zip  
tambah ke system path: oc-client

check:  
cmd -> "oc version"

output:
oc v3.11.98
kubernetes v1.11.0+d4cacc0

SoapUI
install: SoapUI-x64-5.5.0.exe

postman:
install: Postman-win64-7.9.0-Setup.exe


Start LAB:
Fuse SOAP  
1. https://github.com/adithaha/workshop-agile-integration/blob/master/soap/lab0-environment.md
2. https://github.com/adithaha/workshop-agile-integration/blob/master/soap/lab1-initiate-fuse-project.md
3. https://github.com/adithaha/workshop-agile-integration/blob/master/soap/lab2-integrate.md
4. https://github.com/adithaha/workshop-agile-integration/blob/master/soap/lab3-deployment.md
5. https://github.com/adithaha/workshop-agile-integration/blob/master/soap/lab4-integrate-contd.md
6. https://github.com/adithaha/workshop-agile-integration/blob/master/soap/lab5-explore-application.md

Fuse REST
1. https://github.com/adithaha/workshop-agile-integration/blob/master/rest/lab1-initiate.md
2. https://github.com/adithaha/workshop-agile-integration/blob/master/rest/lab2-generate-rest.md
3. https://github.com/adithaha/workshop-agile-integration/blob/master/rest/lab3-deployment.md
4. https://github.com/adithaha/workshop-agile-integration/blob/master/rest/lab4-explore-application.md

AMQ Broker
1. https://github.com/adithaha/workshop-agile-integration/blob/master/amq/lab1-bulk-async.md
2. https://github.com/adithaha/workshop-agile-integration/blob/master/amq/lab2-try-amq.md

JDG
1. https://github.com/adithaha/workshop-agile-integration/blob/master/jdg/lab1-using-cache.md

3Scale
1. https://github.com/adithaha/jboss-fuse-workshop/blob/master/api/lab1-api-management.md
