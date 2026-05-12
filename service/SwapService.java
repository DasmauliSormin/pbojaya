package service; // class ini berada di package service

import model.SwapRequest;   // import class SwapRequest dari package model
import java.util.ArrayList; // import ArrayList untuk menyimpan daftar request swap
import java.util.HashMap;   // import HashMap untuk mapping requestId -> SwapRequest

// ============================================================
// SwapService.java
// Tanggung jawab: logic swap jadwal dan ruangan perkuliahan
// Alur status: MENUNGGU -> VALID -> DISETUJUI
//
// Catatan: SwapService hanya memakai method yang sudah ada di SwapRequest:
//   - getRequestId()  -> ID request
//   - getStatus()     -> status saat ini
//   - setStatus()     -> ubah status
//   - getHari()       -> hari BARU (diwarisi dari Peminjaman, diisi hariBaru)
//   - getJam()        -> jam BARU (diwarisi dari Peminjaman, diisi jamBaru)
//   - getRuangan()    -> ruangan BARU (diwarisi dari Peminjaman, diisi ruanganBaru)
//   - applyReschedule() -> terapkan perubahan ke jadwal lama
//   - getDetail()     -> tampilkan detail sesuai format README
// ============================================================
public class SwapService {

    // ----------------------------------------------------------
    // STRUKTUR DATA UTAMA
    // ----------------------------------------------------------

    // ArrayList untuk menyimpan semua SwapRequest yang masuk
    // Urutan penting: request diproses sesuai urutan masuk (FIFO)
    private ArrayList<SwapRequest> daftarRequest = new ArrayList<>();

    // HashMap untuk akses cepat berdasarkan requestId
    // key = requestId (contoh: "REQ01"), value = objek SwapRequest
    // Berguna agar tidak perlu loop ArrayList setiap kali cari 1 request
    private HashMap<String, SwapRequest> requestMap = new HashMap<>();

    // Referensi ke BookingService untuk cek & update ketersediaan ruangan
    // SwapService bergantung pada BookingService (Dependency Injection)
    private BookingService bookingService;

    // ----------------------------------------------------------
    // CONSTRUCTOR
    // Menerima BookingService dari luar agar tidak membuat instance sendiri
    // ----------------------------------------------------------
    public SwapService(BookingService bookingService) {
        this.bookingService = bookingService; // simpan referensi BookingService
    }

    // ----------------------------------------------------------
    // METHOD: tambahRequest
    // Mendaftarkan permintaan reschedule baru ke sistem
    // Dipanggil saat command: reschedule-request#REQ01#SCH01#...
    //
    // Parameter:
    //   requestId - ID unik request (contoh: "REQ01")
    //   request   - objek SwapRequest yang sudah diisi datanya
    // ----------------------------------------------------------
    public void tambahRequest(String requestId, SwapRequest request) {

        // Cek apakah requestId sudah pernah terdaftar sebelumnya
        if (requestMap.containsKey(requestId)) {
            // Tolak duplikat: ID yang sama tidak boleh didaftarkan dua kali
            System.out.println("[SWAP] Request " + requestId + " sudah terdaftar sebelumnya.");
            return; // keluar dari method
        }

        // Set status awal request menjadi MENUNGGU
        request.setStatus("MENUNGGU");

        // Simpan ke ArrayList (untuk iterasi berurutan / FIFO)
        daftarRequest.add(request);

        // Simpan ke HashMap (untuk akses cepat via requestId)
        requestMap.put(requestId, request);

        System.out.println("[SWAP] Request " + requestId + " berhasil didaftarkan. Status: MENUNGGU");
    }

    // ----------------------------------------------------------
    // METHOD: prosesValidasi
    // Memvalidasi SEMUA request yang berstatus MENUNGGU
    // Dipanggil saat command: validation-process
    //
    // Catatan penting tentang getter yang dipakai:
    //   SwapRequest extends Peminjaman, constructor SwapRequest memanggil:
    //   super(requestId, ruanganBaru, hariBaru, jamBaru)
    //   Artinya:
    //   - getRuangan() -> mengembalikan ruanganBaru
    //   - getHari()    -> mengembalikan hariBaru
    //   - getJam()     -> mengembalikan jamBaru
    //   Getter ini diwarisi dari Peminjaman, sudah tersedia tanpa perlu getter baru
    //
    // Logika validasi:
    //   1. Ruangan tujuan tidak boleh bentrok dengan jadwal lain
    //   2. Jika 2 request saling tukar ruangan (REQ01 <-> REQ02),
    //      keduanya VALID karena saling melepas ruangan masing-masing
    // ----------------------------------------------------------
    public void prosesValidasi() {

        System.out.println("[SWAP] Memulai proses validasi semua request MENUNGGU...");

        // HashMap sementara untuk deteksi swap silang (saling tukar ruangan)
        // key   = "roomId_hari_jam" yang akan DILEPAS oleh suatu request
        // value = requestId yang melepas ruangan tersebut
        HashMap<String, String> ruanganAkanDilepas = new HashMap<>();

        // PASS 1: Catat semua slot yang akan DILEPAS
        // Masalah: kita tidak punya getter jadwalLama, tapi kita bisa pakai
        // BookingService untuk tahu slot mana yang sedang terpakai
        // Pendekatan: setiap request punya ruangan BARU (via getRuangan())
        // slot yang dilepas adalah slot LAMA di BookingService
        // Karena kita tidak bisa akses jadwalLama, kita catat ruangan BARU
        // yang diminta setiap request, lalu cek silang antar request
        for (SwapRequest req : daftarRequest) { // loop seluruh ArrayList
            if (req.getStatus().equals("MENUNGGU")) {

                // getRuangan() -> ruanganBaru (dari super() di constructor SwapRequest)
                // getHari()    -> hariBaru
                // getJam()     -> jamBaru
                // Ini adalah slot TUJUAN yang diminta request ini
                String roomIdTujuan = req.getRuangan().getRoomId();
                String hariTujuan   = req.getHari();
                String jamTujuan    = req.getJam();

                // Buat key slot tujuan: "R102_Monday_10:00"
                String keyTujuan = roomIdTujuan + "_" + hariTujuan + "_" + jamTujuan;

                // Catat bahwa request ini MEMBUTUHKAN slot ini
                // Sekaligus ini berarti request lain yang punya slot ini akan melepasnya
                ruanganAkanDilepas.put(keyTujuan, req.getRequestId());
            }
        }

        // PASS 2: Validasi setiap request MENUNGGU
        for (SwapRequest req : daftarRequest) { // loop seluruh ArrayList
            if (!req.getStatus().equals("MENUNGGU")) {
                continue; // lewati request yang sudah diproses
            }

            // Ambil info ruangan TUJUAN yang diminta request ini
            String roomIdTujuan = req.getRuangan().getRoomId(); // ruangan baru
            String hariTujuan   = req.getHari();                // hari baru
            String jamTujuan    = req.getJam();                 // jam baru

            // Buat key slot tujuan untuk dicek
            String keyTujuan = roomIdTujuan + "_" + hariTujuan + "_" + jamTujuan;

            // Cek apakah ada request LAIN yang juga menargetkan slot ini
            // Jika ada, berarti ada yang akan melepas slot ini -> swap silang
            boolean adaYangMelepas = false; // flag default: tidak ada yang melepas
            for (SwapRequest other : daftarRequest) { // loop untuk cek request lain
                if (!other.getRequestId().equals(req.getRequestId())
                        && other.getStatus().equals("MENUNGGU")) {
                    // Ambil slot tujuan request lain
                    // Buat key slot tujuan request lain
                    // Jika slot tujuan request lain == slot tujuan request ini
                    // berarti keduanya saling tukar -> flag true
                    // (logika swap silang: REQ01 mau R102, REQ02 mau R101)
                    // kita cukup cek apakah ada request lain yang menargetkan
                    // slot yang sama dengan slot ASAL kita (yakni slot tujuan request ini
                    // adalah slot tujuan orang lain, dan sebaliknya)
                    if (ruanganAkanDilepas.containsKey(keyTujuan)
                            && !ruanganAkanDilepas.get(keyTujuan).equals(req.getRequestId())) {
                        adaYangMelepas = true; // ada request lain yang menargetkan slot ini
                        break; // tidak perlu lanjut loop
                    }
                }
            }

            // Cek apakah ruangan tujuan saat ini bebas di BookingService
            boolean ruanganBebas = bookingService.isRuanganTersedia(
                    roomIdTujuan, hariTujuan, jamTujuan);

            if (ruanganBebas || adaYangMelepas) {
                // VALID: ruangan kosong ATAU ada swap silang
                req.setStatus("VALID");
                System.out.println("[SWAP] " + req.getRequestId() + " -> VALID");
            } else {
                // DITOLAK: ruangan terpakai dan tidak ada swap silang
                req.setStatus("DITOLAK");
                System.out.println("[SWAP] " + req.getRequestId()
                        + " -> DITOLAK (ruangan " + roomIdTujuan
                        + " bentrok pada " + hariTujuan + " " + jamTujuan + ")");
            }
        }

        System.out.println("[SWAP] Validasi selesai.");
    }

    // ----------------------------------------------------------
    // METHOD: approveRequest
    // Menyetujui satu request yang sudah VALID
    // Dipanggil saat command: approve-request#REQ01
    //
    // Alur:
    //   1. Daftarkan booking ruangan TUJUAN (kunci slot baru)
    //   2. Terapkan perubahan jadwal via applyReschedule()
    //   3. Update status menjadi DISETUJUI
    // ----------------------------------------------------------
    public void approveRequest(String requestId) {

        // Cari request di HashMap (O(1), lebih cepat dari loop ArrayList)
        SwapRequest req = requestMap.get(requestId);

        // Validasi: apakah requestId ditemukan?
        if (req == null) {
            System.out.println("[SWAP] Request " + requestId + " tidak ditemukan.");
            return;
        }

        // Validasi: hanya request VALID yang bisa disetujui
        if (!req.getStatus().equals("VALID")) {
            System.out.println("[SWAP] Request " + requestId
                    + " tidak bisa disetujui. Status saat ini: " + req.getStatus());
            return;
        }

        // Ambil info ruangan tujuan dari SwapRequest
        // getRuangan() -> ruanganBaru (dari super() di constructor SwapRequest)
        // getHari()    -> hariBaru
        // getJam()     -> jamBaru
        String hariTujuan   = req.getHari();                // hari baru
        String jamTujuan    = req.getJam();                 // jam baru

        // --- LANGKAH 1: Daftarkan booking ruangan TUJUAN ---
        // tambahPeminjaman(id, ruangan, hari, jam)
        boolean berhasil = bookingService.tambahPeminjaman(
                req.getRequestId(),  // gunakan requestId sebagai id peminjaman baru
                req.getRuangan(),    // ruangan tujuan (ruanganBaru)
                hariTujuan,          // hari baru
                jamTujuan            // jam baru
        );

        if (!berhasil) {
            // Booking baru gagal (ada konflik mendadak)
            System.out.println("[SWAP] Approve gagal: ruangan tujuan tidak tersedia saat approve.");
            return;
        }

        // --- LANGKAH 2: Terapkan perubahan ke objek Jadwal ---
        // applyReschedule() sudah ada di class SwapRequest
        // Mengubah hari, jam, dan ruangan di jadwalLama secara langsung
        req.applyReschedule();

        // --- LANGKAH 3: Update status menjadi DISETUJUI ---
        req.setStatus("DISETUJUI");
        System.out.println("[SWAP] Request " + requestId + " -> DISETUJUI");
    }

    // ----------------------------------------------------------
    // METHOD: tampilkanDetail
    // Menampilkan detail satu request atau semua request
    // Dipanggil saat command: request-detail#REQ01 atau request-detail#ALL
    //
    // Format output (sesuai README):
    //   REQ01|SCH01|Monday 08:00 -> Monday 10:00|R101->R102|2024/2025|odd|MENUNGGU
    // ----------------------------------------------------------
    public void tampilkanDetail(String requestId) {

        if (requestId.equalsIgnoreCase("ALL")) {
            // Tampilkan SEMUA request dengan loop ArrayList (urutan masuk terjaga)
            for (SwapRequest req : daftarRequest) {
                // getDetail() sudah ada di class SwapRequest
                System.out.println(req.getDetail());
            }
        } else {
            // Tampilkan SATU request: cari via HashMap (cepat, O(1))
            SwapRequest req = requestMap.get(requestId);

            if (req == null) {
                System.out.println("[SWAP] Request " + requestId + " tidak ditemukan.");
                return;
            }

            // getDetail() sudah ada di class SwapRequest
            System.out.println(req.getDetail());
        }
    }

    // ----------------------------------------------------------
    // METHOD: getDaftarRequest
    // Getter untuk ArrayList semua request
    // ----------------------------------------------------------
    public ArrayList<SwapRequest> getDaftarRequest() {
        return daftarRequest; // kembalikan seluruh daftar request
    }

    // ----------------------------------------------------------
    // METHOD: getRequestMap
    // Getter untuk HashMap index request
    // ----------------------------------------------------------
    public HashMap<String, SwapRequest> getRequestMap() {
        return requestMap; // kembalikan HashMap
    }
}