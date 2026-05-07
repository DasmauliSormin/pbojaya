package model;
// package = pengelompokan class agar rapi.
// Class Jadwal berada dalam package "model".

public class Jadwal {
// public = class bisa diakses dari package lain.
// class = blueprint/kerangka untuk membuat object Jadwal.

    private String scheduleId;
    // private = hanya bisa diakses di dalam class Jadwal saja (data dibuat aman).
    // String = tipe data teks.
    // scheduleId = ID unik jadwal (misalnya: JD001).

    private String courseId;
    // menyimpan ID mata kuliah (misalnya IF101).

    private String kelas;
    // menyimpan kelas (misalnya: IF-41-01).

    private String lecturerId;
    // menyimpan ID dosen pengajar.

    private String hari;
    // menyimpan hari kuliah (Senin, Selasa, dll).

    private String jam;
    // menyimpan jam kuliah (contoh: 08.00-10.00).

    private Ruangan ruangan;
    // Ruangan = tipe data OBJECT (class buatan sendiri).
    // Menyimpan objek ruangan tempat kuliah berlangsung.
    // Ini contoh RELASI antar class (Jadwal punya Ruangan).


    // constructor untuk membuat object jadwal
    public Jadwal(String scheduleId, String courseId, String kelas,
                  String lecturerId, String hari, String jam, Ruangan ruangan) {
        // Constructor = dipanggil saat object Jadwal dibuat.
        // Parameter berisi data yang wajib diisi saat membuat jadwal.

        this.scheduleId = scheduleId;
        // this = menunjuk ke atribut milik class.
        // Mengisi atribut scheduleId dengan nilai dari parameter.

        this.courseId = courseId;
        this.kelas = kelas;
        this.lecturerId = lecturerId;
        this.hari = hari;
        this.jam = jam;
        this.ruangan = ruangan;
        // semua atribut diisi saat object dibuat.
    }

    // ===== GETTER (mengambil data) =====

    public String getScheduleId() { return scheduleId; }
    // Getter = method untuk mengambil nilai scheduleId.

    public String getCourseId() { return courseId; }

    public String getKelas() { return kelas; }

    public String getLecturerId() { return lecturerId; }

    public String getHari() { return hari; }

    public String getJam() { return jam; }

    public Ruangan getRuangan() { return ruangan; }
    // Mengembalikan objek Ruangan.

    // ===== SETTER (mengubah data) =====

    // method untuk mengubah hari jadwal
    public void setHari(String hari) { this.hari = hari; }
    // void = tidak mengembalikan nilai.
    // Setter untuk mengubah hari jadwal.

    public void setJam(String jam) { this.jam = jam; }
    // Setter untuk mengubah jam jadwal.

    public void setRuangan(Ruangan r) { this.ruangan = r; }
    // Setter untuk mengganti ruangan kuliah.

    // ===== METHOD MENAMPILKAN INFO LENGKAP =====

    // method untuk menampilkan informasi jadwal lengkap
    public String getFullInfo() {
        // Method ini mengembalikan String berisi semua info jadwal.

        return scheduleId + "|" + courseId + "|" + kelas + "|" +
               lecturerId + "|" + hari + " " + jam + "|" +
               ruangan.getRoomId();
        // Menggabungkan semua data menjadi satu teks.
        // ruangan.getRoomId() = memanggil method dari class Ruangan.
    }
}