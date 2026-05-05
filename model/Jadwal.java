package model;

public class Jadwal {
    private String scheduleId;
    private String courseId;
    private String kelas;
    private String lecturerId;
    private String hari;
    private String jam;
    private Ruangan ruangan;

    public Jadwal(String scheduleId, String courseId, String kelas,
                  String lecturerId, String hari, String jam, Ruangan ruangan) {
        this.scheduleId = scheduleId;
        this.courseId = courseId;
        this.kelas = kelas;
        this.lecturerId = lecturerId;
        this.hari = hari;
        this.jam = jam;
        this.ruangan = ruangan;
    }

    public String getScheduleId() { return scheduleId; }
    public String getCourseId() { return courseId; }
    public String getKelas() { return kelas; }
    public String getLecturerId() { return lecturerId; }
    public String getHari() { return hari; }
    public String getJam() { return jam; }
    public Ruangan getRuangan() { return ruangan; }

    public void setHari(String hari) { this.hari = hari; }
    public void setJam(String jam) { this.jam = jam; }
    public void setRuangan(Ruangan r) { this.ruangan = r; }

    public String getFullInfo() {
        return scheduleId + "|" + courseId + "|" + kelas + "|" +
               lecturerId + "|" + hari + " " + jam + "|" +
               ruangan.getRoomId();
    }
}