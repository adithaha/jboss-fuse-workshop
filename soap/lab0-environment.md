
## LAB 0 - Environment

-- prepare vm --
1. Install VirtualBox 5.1.x
2. New
	- Name: JBossFuse
	- Type: Linux
	- Version: Fedora (64-bit)	
3. Memory 3096MB
4. Use an existing virtual hard disk file JBossFuse.vdi
5. Create



jboss/jboss

-- Start postgresql --
$ sudo su
<password>
$ docker start postgres94

1. Go into terminal as jboss user
2. cd ~/workshop-fuse
3. unzip jboss-fuse-karaf-6.3.0.redhat-187
4. cd jboss-fuse-6.3.0.redhat-187/bin
5. ./fuse
