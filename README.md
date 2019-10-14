# Agile Integration Workshop

Workshop untuk Agile Integration
- Red Hat Fuse
- Red Hat OpenShift Container Platform
- Red Hat Data Grid
- Red Hat AMQ
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


Start LAB:
Fuse SOAP
1. https://github.com/adithaha/workshop-agile-integration/blob/master/soap/lab2-integrate.md
2. https://github.com/adithaha/workshop-agile-integration/blob/master/soap/lab3-deployment.md
3. https://github.com/adithaha/workshop-agile-integration/blob/master/soap/lab4-integrate-contd.md

Fuse REST
1. https://github.com/adithaha/jboss-fuse-workshop/blob/master/rest/lab2-generate-rest.md
2. https://github.com/adithaha/jboss-fuse-workshop/blob/master/rest/lab3-deployment.md

JDG
1. https://github.com/adithaha/workshop-agile-integration/blob/master/jdg/lab1-using-cache.md

3Scale
1. https://github.com/adithaha/jboss-fuse-workshop/blob/master/api/lab1-api-management.md
