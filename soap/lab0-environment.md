
## LAB 0 - Environment

### VM Preparation
1. Install VirtualBox 5.1.x
2. New
	- Name: JBossFuse
	- Type: Linux
	- Version: Fedora (64-bit)	
3. Memory 3096MB
4. Use an existing virtual hard disk file JBossFuse.vdi
5. Create


### Preparing environment

ssh into the machine, user user/pass below: jboss/jboss
```
#start postgresql
$ sudo su
<password>
$ docker start postgres94
```

Start JBoss Developer Studio application

### Import database table

```
$ psql -h localhost postgres -f ddl.sql postgres
```
