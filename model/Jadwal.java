package model; // class model untuk menyimpan data jadwal perkuliahan

public class Jadwal {

    private String scheduleId;  // id jadwal
    private String courseId;    // id mata kuliah
    private String kelas;       // kelas (A,B,C dll)
    private String lecturerId;  // id dosen
    private String hari;        // hari perkuliahan
    private String jam;         // jam perkuliahan
    private Ruangan ruangan;    // object ruangan tempat kuliah

    // constructor untuk membuat object jadwal
    public Jadwal(String scheduleId, String courseId, String kelas,
                  String lecturerId, String hari, String jam, Ruangan ruangan) {

        this.scheduleId = scheduleId; // menyimpan id jadwal
        this.courseId = courseId;     // menyimpan id mata kuliah
        this.kelas = kelas;           // menyimpan kelas
        this.lecturerId = lecturerId; // menyimpan id dosen
        this.hari = hari;             // menyimpan hari kuliah
        this.jam = jam;               // menyimpan jam kuliah
        this.ruangan = ruangan;       // menyimpan object ruangan
    }

    // getter untuk mengambil id jadwal
    public String getScheduleId() { return scheduleId; }

    // getter untuk mengambil id mata kuliah
    public String getCourseId() { return courseId; }

    // getter untuk mengambil kelas
    public String getKelas() { return kelas; }

    // getter untuk mengambil id dosen
    public String getLecturerId() { return lecturerId; }

    // getter untuk mengambil hari
    public String getHari() { return hari; }

    // getter untuk mengambil jam
    public String getJam() { return jam; }

    // getter untuk mengambil object ruangan
    public Ruangan getRuangan() { return ruangan; }

    // method untuk mengubah hari jadwal
    public void setHari(String hari) { this.hari = hari; }

    // method untuk mengubah jam jadwal
    public void setJam(String jam) { this.jam = jam; }

    // method untuk mengubah ruangan
    public void setRuangan(Ruangan r) { this.ruangan = r; }

    // method untuk menampilkan informasi jadwal lengkap
    public String getFullInfo() {
        return scheduleId + "|" + courseId + "|" + kelas + "|" +
               lecturerId + "|" + hari + " " + jam + "|" +
               ruangan.getRoomId();
    }
}