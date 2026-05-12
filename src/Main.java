package src;

import mapper.JadwalMapper;
import mapper.RuanganMapper;
import mapper.SwapMapper;
import mapper.UserMapper;
import model.Jadwal;
import model.Ruangan;
import model.SwapRequest;
import model.User;
import service.BookingService;
import service.SwapService;
import dao.SwapRequestDao;

import java.util.HashMap;
import java.util.Scanner;

// ============================================================
// Main.java
// Titik masuk program (entry point)
// Membaca input dari keyboard lalu memanggil service yang sesuai
//
// Alur input yang didukung:
//   lecturer-add       -> tambah dosen ke daftarUser
//   course-add         -> dicatat, tidak diproses lebih lanjut
//   room-add           -> tambah ruangan ke daftarRuangan
//   schedule-add       -> tambah jadwal + booking slot ruangan
//   reschedule-request -> buat SwapRequest, daftarkan ke SwapService
//   request-detail     -> tampilkan detail request
//   validation-process -> validasi semua request MENUNGGU
//   approve-request    -> setujui request yang VALID
//   ---                -> cetak baris kosong sebagai pemisah output
// ============================================================
public class Main {

    public static void main(String[] args) {

        // ----------------------------------------------------------
        // INISIALISASI SERVICE
        // BookingService dibuat duluan karena SwapService butuh referensinya
        // ----------------------------------------------------------
        BookingService bookingService = new BookingService();
        // BookingService = mengelola logic peminjaman ruangan (ArrayList + HashMap)

        SwapService swapService = new SwapService(bookingService);
        // SwapService = mengelola logic swap jadwal (ArrayList + HashMap)

        // ----------------------------------------------------------
        // INISIALISASI DAO
        // ----------------------------------------------------------
        SwapRequestDao swapDao = new SwapRequestDao();
        // SwapRequestDao = menyimpan swap request ke database

        // ----------------------------------------------------------
        // STRUKTUR DATA IN-MEMORY (HashMap)
        // Menyimpan data sementara selama program berjalan
        // Pakai HashMap agar pencarian cepat O(1) via ID
        // ----------------------------------------------------------

        // key = userId (contoh: "L001"), value = objek User
        HashMap<String, User> daftarUser = new HashMap<>();

        // key = roomId (contoh: "R101"), value = objek Ruangan
        HashMap<String, Ruangan> daftarRuangan = new HashMap<>();

        // key = scheduleId (contoh: "SCH01"), value = objek Jadwal
        HashMap<String, Jadwal> daftarJadwal = new HashMap<>();

        // ----------------------------------------------------------
        // BACA INPUT DARI KEYBOARD
        // Scanner membaca baris per baris dari System.in
        // ----------------------------------------------------------
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            // trim() = hapus spasi di depan dan belakang baris

            // Lewati baris kosong
            if (line.isEmpty()) continue;

            // "---" = pemisah blok output, cetak baris kosong
            if (line.equals("---")) {
                System.out.println();
                continue;
            }

            // Pecah baris input berdasarkan "#"
            // Contoh: "lecturer-add#L001#Dr. Andi#andi@kampus.ac.id#Informatics"
            // Hasil : ["lecturer-add", "L001", "Dr. Andi", "andi@kampus.ac.id", "Informatics"]
            String[] parts = line.split("#");
            String command = parts[0].trim(); // ambil command dari elemen pertama

            // ======================================================
            // COMMAND: lecturer-add
            // Format: lecturer-add#id#nama#email#programStudi
            // Contoh: lecturer-add#L001#Dr. Andi#andi@kampus.ac.id#Informatics
            // ======================================================
            if (command.equals("lecturer-add")) {
                // Susun data untuk UserMapper: id, nama, email
                String[] data = { parts[1], parts[2], parts[3] };

                // Buat object User via UserMapper lalu simpan ke HashMap
                User user = UserMapper.fromArray(data);
                daftarUser.put(user.getId(), user);
                // Tidak ada output untuk lecturer-add
            }

            // ======================================================
            // COMMAND: course-add
            // Format: course-add#id#nama#sks#programStudi
            // Dicatat saja, tidak diproses lebih lanjut
            // ======================================================
            else if (command.equals("course-add")) {
                // course-add tidak diproses, sistem fokus pada jadwal dan swap
            }

            // ======================================================
            // COMMAND: room-add
            // Format: room-add#roomId#gedung#kapasitas
            // Contoh: room-add#R101#GedungA#40
            // ======================================================
            else if (command.equals("room-add")) {
                // Susun data untuk RuanganMapper: roomId, gedung, kapasitas
                String[] data = { parts[1], parts[2], parts[3] };

                // Buat object Ruangan via RuanganMapper lalu simpan ke HashMap
                Ruangan ruangan = RuanganMapper.fromArray(data);
                daftarRuangan.put(ruangan.getRoomId(), ruangan);
                // Tidak ada output untuk room-add
            }

            // ======================================================
            // COMMAND: schedule-add
            // Format: schedule-add#scheduleId#courseId#kelas#lecturerId#hari#jam#roomId
            // Contoh: schedule-add#SCH01#IF101#A#L001#Monday#08:00#R101
            // ======================================================
            else if (command.equals("schedule-add")) {
                // Cari object Ruangan dari HashMap via roomId (parts[7])
                String roomId   = parts[7].trim();
                Ruangan ruangan = daftarRuangan.get(roomId);
                // get() dari HashMap -> O(1)

                if (ruangan == null) {
                    System.out.println("[ERROR] Ruangan " + roomId + " tidak ditemukan.");
                    continue;
                }

                // Susun data untuk JadwalMapper
                // [0]=scheduleId, [1]=courseId, [2]=kelas, [3]=lecturerId, [4]=hari, [5]=jam
                String[] data = { parts[1], parts[2], parts[3], parts[4], parts[5], parts[6] };

                // Buat object Jadwal via JadwalMapper lalu simpan ke HashMap
                Jadwal jadwal = JadwalMapper.fromArray(data, ruangan);
                daftarJadwal.put(jadwal.getScheduleId(), jadwal);

                // Daftarkan slot ruangan ke BookingService
                // Agar sistem tahu ruangan ini sudah terpakai di hari dan jam tersebut
                bookingService.tambahPeminjaman(
                        jadwal.getScheduleId(), // id peminjaman = scheduleId
                        ruangan,                // object ruangan
                        jadwal.getHari(),       // hari jadwal
                        jadwal.getJam()         // jam jadwal
                );
                // Tidak ada output untuk schedule-add
            }

            // ======================================================
            // COMMAND: reschedule-request
            // Format: reschedule-request#reqId#scheduleId#hari#jam#roomId#tahun#semester#status
            // Contoh: reschedule-request#REQ01#SCH01#Monday#10:00#R102#2024/2025#odd#MENUNGGU
            // ======================================================
            else if (command.equals("reschedule-request")) {
                String reqId      = parts[1].trim(); // ID request
                String scheduleId = parts[2].trim(); // ID jadwal yang ingin diubah
                String roomIdBaru = parts[5].trim(); // ID ruangan tujuan

                // Cari objek Jadwal lama dari HashMap
                Jadwal jadwalLama = daftarJadwal.get(scheduleId);
                if (jadwalLama == null) {
                    System.out.println("[ERROR] Jadwal " + scheduleId + " tidak ditemukan.");
                    continue;
                }

                // Cari objek Ruangan baru dari HashMap
                Ruangan ruanganBaru = daftarRuangan.get(roomIdBaru);
                if (ruanganBaru == null) {
                    System.out.println("[ERROR] Ruangan " + roomIdBaru + " tidak ditemukan.");
                    continue;
                }

                // Susun data untuk SwapMapper
                // [0]=reqId, [1]=scheduleId, [2]=hari, [3]=jam, [4]=roomId,
                // [5]=tahunAjaran, [6]=semester, [7]=status
                String[] data = {
                    parts[1], parts[2], parts[3], parts[4],
                    parts[5], parts[6], parts[7], parts[8]
                };

                // Buat object SwapRequest via SwapMapper
                SwapRequest swapRequest = SwapMapper.fromArray(data, jadwalLama, ruanganBaru);

                // Daftarkan ke SwapService (status akan di-set MENUNGGU)
                swapService.tambahRequest(reqId, swapRequest);

                // Simpan ke database via DAO
                swapDao.saveSwapRequest(swapRequest);
                // Tidak ada output untuk reschedule-request
            }

            // ======================================================
            // COMMAND: request-detail
            // Format: request-detail#REQ01 atau request-detail#ALL
            // Output: REQ01|SCH01|Monday 08:00 -> Monday 10:00|R101->R102|2024/2025|odd|MENUNGGU
            // ======================================================
            else if (command.equals("request-detail")) {
                String reqId = parts[1].trim(); // bisa "REQ01" atau "ALL"

                // Panggil SwapService untuk tampilkan detail
                swapService.tampilkanDetail(reqId);
            }

            // ======================================================
            // COMMAND: validation-process
            // Memvalidasi semua request MENUNGGU -> VALID atau DITOLAK
            // ======================================================
            else if (command.equals("validation-process")) {
                swapService.prosesValidasi();
            }

            // ======================================================
            // COMMAND: approve-request
            // Format: approve-request#REQ01
            // Menyetujui request VALID -> DISETUJUI
            // ======================================================
            else if (command.equals("approve-request")) {
                String reqId = parts[1].trim(); // ID request yang disetujui
                swapService.approveRequest(reqId);
            }

            // ======================================================
            // COMMAND tidak dikenal
            // ======================================================
            else {
                System.out.println("[ERROR] Command tidak dikenal: " + command);
            }
        }

        // Tutup scanner setelah selesai membaca input
        scanner.close();
    }
}