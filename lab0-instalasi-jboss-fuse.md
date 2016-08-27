
## LAB 0 - Installasi JBoss Fuse 6.2

Pastikan anda sudah memiliki (terinstal JDK atau JRE) Java 7 atau yang lebih tinggi
Buka Terminal (Linux) atau Command Line (Windows) kemudian jalankan perintah
   

   	java -version

   
Kemudian juga cek apakah variabel `JAVA_HOME` sudah diset di environment, jika belum set JAVA_HOME
   

   	export JAVA_HOME=/path/to/jdk

   	

Installasi Apache Maven
=======================
Untuk di lokal developer, Apache Maven wajib tersedia. Jika belum ada, lakukan langkah berikut:

1.  Buat folder /jboss/apache-maven/
2.  Download apache maven versi terakhir, dan masukan ke dalam folder di atas
3.  Unzip file apache-maven.zip

Setelah itu set environment M2_HOME


   	export M2_HOME=/path/to/maven

   

Installasi Standalone
==========================

1.  Buat folder /jboss/jboss-fuse/standalone
2.  Taruh file instalasi jboss-fuse-full-6.2.1.redhat-084.zip ke dalam folder tersebut
3.  Unzip file jboss-fuse-full-6.2.1.redhat-084.zip
4.  Buka file (fuse)/etc/users.properties, buka comment line di bawah untuk mengaktifkan user admin:

	```
	admin=admin,admin,manager,viewer,Monitor, Operator, Maintainer, Deployer, Auditor, Administrator, SuperUser
	```

5.  Buka folder <fuse>/bin/
6.  Jalankan fuse.sh atau fuse.bat (fuse akan berjalan foreground, jika ditutup maka fuse akan mati)
7.  Tunggu hingga menyala sempurna
8.  Tekan ctrl-d untuk mematikan fuse

Perintah OSGI sederhana
=======================

JBoss Fuse menggunakan OSGI sebagai base teknologi. Framework OSGI yang digunakan adalah Apache Karaf. Berikut beberapa perintah OSGI sederhana yang bisa dilakukan.

osgi:list - Untuk melihat modul yang sudah terinstall

osgi:install (maven path) - untuk menginstall modul

osgi:uninstall (id) - untuk menguninstall modul

osgi:update (id) - untuk mengupdate modul

log:tail - melihat log server

Akses web console
=================
Fuse akan membuka port 8181 untuk http. Web console bisa diakses melalui alamat http://localhost:8181. Berikutnya login dengan user admin/admin

Koneksi ke fuse dari client
===========================

Untuk akses ke fuse dari klien, bisa dilakukan via ssh dengan port 8101 dengan user admin/admin

Dari bash console



   	ssh -p 8101 admin@localhost

   
Jika dari windows, gunakan putty. Set ke host dan port 8181

Untuk keluar dari client, tekan ctrl-d
