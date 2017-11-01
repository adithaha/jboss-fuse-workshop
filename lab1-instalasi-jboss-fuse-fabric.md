
## LAB 1 - Installasi JBoss Fuse dengan fabric

Fabric adalah suatu mode pada fuse yang digunakan untuk manajemen aplikasi dan juga clustering.

   	

Installasi Fabric
=================
Untuk instalasi fabric, install fuse seperti biasa. user tidak perlu diaktifkan, karena fabric akan create user baru nantinya

1.  Buat folder /jboss/jboss-fuse/fabric (folder tidak boleh ada karakter khusus atau spasi)
2.  Taruh file instalasi jboss-fuse-full-6.2.1.redhat-084.zip ke dalam folder tersebut
3.  Unzip file jboss-fuse-full-6.2.1.redhat-084.zip
4.  Masuk folder jboss-fuse-full-6.2.1.redhat-084/bin Jalankan fuse.sh atau fuse.bat (fuse akan berjalan foreground, jika ditutup maka fuse akan mati)
5.  Tunggu hingga menyala sempurna

Sampai tahap ini fuse selesai diinstall. Jalankan prosedur berikut untuk membuat fabric
1.  Masuk fuse console
2.  Jalankan command berikut:

	```
	fuse> fabric:create --clean --wait-for-provisioning --new-user admin --new-user-password admin --new-user-role admin --zookeeper-password admin --resolver manualip --manual-ip 127.0.0.1
	```
3.  Tunggu hingga proses selesai (1-3 menit)
4.  Jalankan command berikut untuk melihat apakah proses sudah selesai. Cek provision status.

	```
	fuse> container-list
	
	[id]   [version]  [type]  [connected]  [profiles]              [provision status]
root*  1.0        karaf   yes          fabric                  success           
                                       fabric-ensemble-0000-1                    
                                       jboss-fuse-full                           

	```
5. Ada 3 profile yang terpasang di server fabric root ini. Fabric untuk menandakan dia adalah fabric server, fabric-ensemble maka dia bagian dari ensemble, dan jboss-fuse-full untuk aplikasi.
6. Normalnya tidak boleh ada aplikasi di server fabric, jalankan command berikut untuk menghapus profile jboss-fuse-full

	```
	fuse> container-remove-profile root jboss-fuse-full                      

	```
7. Fabric selesai disetup, siap digunakan

Tips:
Parameter --resolver manualip --manual-ip 127.0.0.1 pada saat fabric:create hanya digunakan di lokal. Untuk setup di server baik dev maupun prod, jangan gunakan parameter ini.

Cluster beberapa server
=======================
Jika ada beberapa server, berikut cara setup menghubungkan antar fabric server (bagian ini tidak perlu dilakukan karena membutuhkan banyak server, cukup sebagai referensi saja)

1.  Pastikan mesin pertama sudah berjalan pada mode fabric, langkah di bawah dilakukan untuk mesin kedua, ketiga dan seterusnya.
2.  Buat folder /jboss/jboss-fuse/fabric (folder tidak boleh ada karakter khusus atau spasi)
2.  Taruh file instalasi jboss-fuse-full-6.2.1.redhat-084.zip ke dalam folder tersebut
3.  Unzip file jboss-fuse-full-6.2.1.redhat-084.zip
4.  Nama fabric server tidak boleh sama untuk satu cluster. Edit file (fuse)/etc/system.properties untuk menggantinya

	```
	karaf.name = root #ganti menjadi root2                    

	```

5.  Jalankan fuse.sh atau fuse.bat (fuse akan berjalan foreground, jika ditutup maka fuse akan mati)
6.  Tunggu hingga menyala sempurna
7.  Join fabric cluster

	```
	fuse> fabric:join --zookeeper-password <password> <fabric host>                       

	```
8.  Lanjutkan untuk semua server

Jika mempunyai server berjumlah ganjil, masukan semua server sebagai ensemble. Jika ada genap, maka ambil yg berjumlah ganjil saja. Misal ada 4, maka ada 3 ensemble dan seterusnya.

1.  Sebelum masuk ke dalam ensemble, pastikan semua server sudah melakukan fabric:join
2.  Masuk ke fabric yang pertama (root)
3.  Masukan root2 dan root3 sebagai ensemble

	```
	root> ensemble-add root2 root3                        

	```
4.  Jalankan container-list untuk melihat apakah fabric-ensemble sudah terpasang pada semua root


Tips
====

1.  Fabric sangat sensitif terhadap jaringan. Pastikan ip dan hostname sudah disetup dengan baik dan tidak ada perubahan lagi. Jika ada perubahan, fabric akan menjadi rusak dan perlu setup lagi dari awal.
2.  Parameter --resolver manualip --manual-ip 127.0.0.1 pada fabric:create berguna untuk membuat ip menjadi statis dan fabric tetap berjalan walaupun ip berubah. Tetapi tidak disarankan di server karena ip menjadi tidak bisa diakses dari luar.
3.  Untuk mendapatkan HA pada fuse, minimal butuh 3 server, karena ensemble mensyaratkan fabric berjumlah ganjil
