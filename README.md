## Latar Belakang Permasalahan

Dalam kegiatan perkuliahan, jadwal yang telah disusun tidak selalu dapat berjalan sesuai rencana. Terdapat berbagai kondisi yang menyebabkan perubahan jadwal, seperti dosen yang berhalangan hadir pada hari tertentu, adanya bentrokan penggunaan kelas dengan program studi lain, maupun proses peralihan waktu perkuliahan ke sesi yang berbeda, seperti pagi, siang, dan sore hari.

Permasalahan ini seringkali menimbulkan kesulitan dalam proses penjadwalan ulang. Dosen atau pihak akademik harus mencari waktu pengganti yang sesuai, sekaligus memastikan bahwa ruangan yang digunakan tersedia dan tidak bertabrakan dengan jadwal kelas lain. Selain itu, perubahan hari perkuliahan juga memerlukan penyesuaian terhadap jadwal mahasiswa agar tidak menimbulkan konflik baru.

Dalam praktiknya, bentrokan penggunaan ruangan antar program studi dapat terjadi akibat kurangnya koordinasi dan tidak adanya sistem yang terintegrasi. Sebagai contoh, suatu kelas yang awalnya dijadwalkan pada sesi sore hari berinisiatif untuk memindahkan jadwal ke sesi pagi karena terdapat ruangan kosong di gedung fakultasnya sendiri. Namun, proses pemindahan tersebut dilakukan tanpa melalui prosedur resmi atau tanpa pengajuan ke pihak administrasi akademik (BAAK).

Di sisi lain, program studi lain yang sebelumnya memiliki jadwal di gedung fakultas berbeda merasa lokasi tersebut kurang efisien, sehingga mencari alternatif ruangan yang lebih dekat. Setelah menemukan ruangan kosong di gedung fakultas yang sama, program studi tersebut mengajukan permohonan resmi kepada pihak BAAK, yang kemudian disetujui. Akibatnya, terjadi bentrokan penggunaan ruangan karena kedua pihak mengklaim penggunaan ruang yang sama pada waktu yang bersamaan.

Kondisi ini menunjukkan bahwa proses pengelolaan jadwal dan ruangan yang masih dilakukan secara manual berpotensi menimbulkan kesalahan, seperti double booking ruangan, konflik antar kelas, serta ketidakefisienan dalam penggunaan fasilitas kampus. Tidak adanya sistem yang terintegrasi untuk membantu proses pencarian jadwal dan ruangan kosong menjadi salah satu kendala utama dalam pengelolaan jadwal perkuliahan.

Berdasarkan kondisi tersebut, diperlukan suatu sistem yang dapat membantu dalam proses penjadwalan ulang perkuliahan secara lebih efisien, terstruktur, dan terintegrasi, dengan mempertimbangkan ketersediaan waktu dan ruangan secara otomatis.

## Tujuan Program 
Berdasarkan permasalahan yang telah diuraikan, tujuan dari pengembangan sistem ini adalah:
1. Mengembangkan sistem yang dapat membantu proses penjadwalan ulang perkuliahan secara lebih efisien dan terstruktur.
2. Mempermudah dosen dan pihak akademik dalam melakukan perubahan jadwal kuliah ke sesi lain (pagi, siang, atau sore) maupun ke hari yang berbeda.
3. Menyediakan informasi ketersediaan ruangan secara otomatis dan akurat untuk menghindari bentrokan penggunaan ruang.
4. Mencegah terjadinya konflik jadwal antar kelas maupun antar program studi melalui sistem yang terintegrasi.
5. Mengurangi kesalahan dalam proses penjadwalan, seperti double booking ruangan akibat proses manual.
6. Mengoptimalkan penggunaan fasilitas kampus, khususnya ruang kelas, agar dapat digunakan secara efektif dan efisien.
7. Mengimplementasikan konsep Pemrograman Berorientasi Objek (OOP) dalam penyelesaian masalah nyata di lingkungan kampus.

## Hal-Hal yang Perlu Diperhatikan:
Dalam proses penjadwalan ulang perkuliahan, terdapat beberapa ketentuan yang harus diperhatikan agar tidak menimbulkan konflik baru, yaitu:
1. Ruangan yang akan digunakan harus dalam kondisi tidak sedang dipakai oleh kelas lain pada waktu yang sama.
2. Jadwal pengganti tidak boleh bertabrakan dengan jadwal mahasiswa maupun dosen yang terlibat.
3. Pengajuan perubahan jadwal harus dilakukan melalui sistem atau prosedur resmi (tidak sepihak).
4. Ruangan yang dipilih harus sesuai dengan kebutuhan kelas, seperti kapasitas dan fasilitas yang tersedia.
5. Setiap perubahan jadwal harus mendapatkan persetujuan dari pihak terkait (misalnya BAAK atau admin akademik).
6. Sistem harus memastikan bahwa tidak terjadi double booking pada ruangan yang sama.
7. Perubahan jadwal harus tetap mempertimbangkan efisiensi waktu dan lokasi agar tidak merugikan pihak lain.

## Input
```bash
lecturer-add#L001#Dr. Andi#andi@kampus.ac.id#Informatics
lecturer-add#L002#Dr. Budi#budi@kampus.ac.id#Informatics

course-add#IF101#Pemrograman Berorientasi Objek#3#Informatics

room-add#R101#GedungA#40
room-add#R102#GedungA#40

schedule-add#SCH01#IF101#A#L001#Monday#08:00#R101
schedule-add#SCH02#IF101#B#L002#Monday#10:00#R102

reschedule-request#REQ01#SCH01#Monday#10:00#R102#2024/2025#odd#MENUNGGU
reschedule-request#REQ02#SCH02#Monday#08:00#R101#2024/2025#odd#MENUNGGU

request-detail#REQ01
request-detail#REQ02
---

validation-process

request-detail#REQ01
request-detail#REQ02
---

approve-request#REQ01
approve-request#REQ02

request-detail#ALL
---

```

## Output
```bash
REQ01|SCH01|Monday 08:00 -> Monday 10:00|R101->R102|2024/2025|odd|MENUNGGU
REQ02|SCH02|Monday 10:00 -> Monday 08:00|R102->R101|2024/2025|odd|MENUNGGU

REQ01|SCH01|Monday 08:00 -> Monday 10:00|R101->R102|2024/2025|odd|VALID
REQ02|SCH02|Monday 10:00 -> Monday 08:00|R102->R101|2024/2025|odd|VALID

REQ01|SCH01|Monday 08:00 -> Monday 10:00|R101->R102|2024/2025|odd|DISETUJUI
REQ02|SCH02|Monday 10:00 -> Monday 08:00|R102->R101|2024/2025|odd|DISETUJUI

```