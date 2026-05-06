package model;
// package model = class ini berada dalam kelompok class model.

public class SwapRequest extends Peminjaman {
    // extends Peminjaman = INHERITANCE
    // SwapRequest adalah turunan dari class Peminjaman.
    // Artinya SwapRequest otomatis punya:
    // id, ruangan, hari, jam dari class Peminjaman.

    private Jadwal jadwalLama;
    // private = hanya bisa diakses di dalam class ini (Encapsulation).
    // Jadwal = tipe data OBJECT (class buatan sendiri).
    // Menyimpan jadwal kuliah yang ingin diubah.

    private String requestId;
    // String = teks.
    // requestId = ID permintaan reschedule (contoh: REQ01).

    private String hariBaru;
    // String = menyimpan hari pengganti (contoh: Monday).

    private String jamBaru;
    // String = menyimpan jam pengganti (contoh: 10:00).

    private Ruangan ruanganBaru;
    // Ruangan = OBJECT.
    // Menyimpan ruangan baru yang diinginkan.

    private String tahunAjaran;
    // String = teks.
    // Contoh: 2024/2025.

    private String semester;
    // String = teks.
    // Contoh: odd / even.

    private String status;
    // String = status request:
    // MENUNGGU → VALID → DISETUJUI

    public SwapRequest(String requestId, Jadwal jadwalLama,
                       String hariBaru, String jamBaru, Ruangan ruanganBaru,
                       String tahunAjaran, String semester, String status) {

        super(requestId, ruanganBaru, hariBaru, jamBaru);
        // Memanggil constructor class induk (Peminjaman).
        // Karena SwapRequest adalah jenis peminjaman ruangan baru.

        this.requestId = requestId;
        this.jadwalLama = jadwalLama;
        this.hariBaru = hariBaru;
        this.jamBaru = jamBaru;
        this.ruanganBaru = ruanganBaru;
        this.tahunAjaran = tahunAjaran;
        this.semester = semester;
        this.status = status;
        // Menyimpan semua data request ke atribut class.
    }

    public String getRequestId() { return requestId; }
    // Getter untuk mengambil ID request.

    public String getStatus() { return status; }
    // Getter untuk melihat status request.

    public void setStatus(String status) { this.status = status; }
    // Setter = method untuk mengubah status request.
    // Dipakai oleh Admin saat VALIDASI dan APPROVE.

    public void applyReschedule() {
        // Method PALING PENTING 🔥
        // Method ini benar-benar mengubah jadwal lama.

        jadwalLama.setHari(hariBaru);
        // Mengubah hari jadwal lama menjadi hari baru.

        jadwalLama.setJam(jamBaru);
        // Mengubah jam jadwal lama menjadi jam baru.

        jadwalLama.setRuangan(ruanganBaru);
        // Mengubah ruangan jadwal lama menjadi ruangan baru.
    }

    public String getDetail() {
        // Method untuk menampilkan detail request sesuai format output CLI.

        return requestId + "|" +
               jadwalLama.getScheduleId() + "|" +
               jadwalLama.getHari() + " " + jadwalLama.getJam() +
               " -> " + hariBaru + " " + jamBaru + "|" +
               jadwalLama.getRuangan().getRoomId() + "->" +
               ruanganBaru.getRoomId() + "|" +
               tahunAjaran + "|" + semester + "|" + status;

        // Format contoh:
        // REQ01|SCH01|Monday 08:00 -> Monday 10:00|R101->R102|2024/2025|odd|MENUNGGU
    }
}