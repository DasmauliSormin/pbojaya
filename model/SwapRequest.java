package model; // class model untuk menyimpan permintaan perubahan jadwal

public class SwapRequest extends Peminjaman { // inheritance dari class Peminjaman

    private Jadwal jadwalLama;   // jadwal lama sebelum diubah
    private String requestId;    // id request swap
    private String hariBaru;     // hari baru yang diminta
    private String jamBaru;      // jam baru yang diminta
    private Ruangan ruanganBaru; // ruangan baru yang diminta
    private String tahunAjaran;  // tahun ajaran
    private String semester;     // semester
    private String status;       // status request (pending, approve, reject)

    // constructor untuk membuat object swap request
    public SwapRequest(String requestId, Jadwal jadwalLama,
                       String hariBaru, String jamBaru, Ruangan ruanganBaru,
                       String tahunAjaran, String semester, String status) {

        super(requestId, ruanganBaru, hariBaru, jamBaru); // memanggil constructor dari class Peminjaman

        this.requestId = requestId;       // menyimpan id request
        this.jadwalLama = jadwalLama;     // menyimpan jadwal lama
        this.hariBaru = hariBaru;         // menyimpan hari baru
        this.jamBaru = jamBaru;           // menyimpan jam baru
        this.ruanganBaru = ruanganBaru;   // menyimpan ruangan baru
        this.tahunAjaran = tahunAjaran;   // menyimpan tahun ajaran
        this.semester = semester;         // menyimpan semester
        this.status = status;             // menyimpan status request
    }

    // getter untuk mengambil id request
    public String getRequestId() { return requestId; }

    // getter untuk mengambil status request
    public String getStatus() { return status; }

    // method untuk mengubah status request
    public void setStatus(String status) { this.status = status; }

    // method untuk menerapkan perubahan jadwal
    public void applyReschedule() {

        jadwalLama.setHari(hariBaru);        // mengganti hari jadwal lama
        jadwalLama.setJam(jamBaru);          // mengganti jam jadwal lama
        jadwalLama.setRuangan(ruanganBaru);  // mengganti ruangan jadwal lama
    }

    // method untuk menampilkan detail request swap
    public String getDetail() {

        return requestId + "|" +
               jadwalLama.getScheduleId() + "|" +
               jadwalLama.getHari() + " " + jadwalLama.getJam() +
               " -> " + hariBaru + " " + jamBaru + "|" +
               jadwalLama.getRuangan().getRoomId() + "->" +
               ruanganBaru.getRoomId() + "|" +
               tahunAjaran + "|" + semester + "|" + status;
    }
}