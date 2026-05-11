package service; // class ini berada di package service

import model.Peminjaman;    // import class Peminjaman dari package model
import model.Ruangan;       // import class Ruangan dari package model
import java.util.ArrayList; // import ArrayList untuk menyimpan daftar peminjaman
import java.util.HashMap;   // import HashMap untuk mapping roomId -> daftar waktu terpakai

// ============================================================
// BookingService.java
// Tanggung jawab: logic peminjaman ruangan
// Memastikan tidak terjadi double booking pada ruangan yang sama
// ============================================================
public class BookingService {

    // ----------------------------------------------------------
    // STRUKTUR DATA UTAMA
    // ----------------------------------------------------------

    // ArrayList untuk menyimpan semua objek Peminjaman yang sudah terdaftar
    // Setiap elemen adalah satu sesi peminjaman ruangan
    private ArrayList<Peminjaman> daftarPeminjaman = new ArrayList<>();

    // HashMap untuk index cepat: key = roomId, value = ArrayList waktu yang sudah terpakai
    // Format waktu: "Monday_08:00"  (hari + "_" + jam)
    // Berguna agar cek konflik tidak perlu loop seluruh daftarPeminjaman setiap kali
    private HashMap<String, ArrayList<String>> bookingIndex = new HashMap<>();

    // ----------------------------------------------------------
    // METHOD: tambahPeminjaman
    // Mendaftarkan peminjaman baru ke sistem
    // Parameter:
    //   id      - ID unik peminjaman (contoh: "REQ01")
    //   ruangan - objek Ruangan yang ingin dipinjam
    //   hari    - hari peminjaman (contoh: "Monday")
    //   jam     - jam peminjaman (contoh: "10:00")
    // Return:
    //   true  -> berhasil didaftarkan (tidak ada konflik)
    //   false -> gagal (ruangan sudah dipakai di waktu tersebut)
    // ----------------------------------------------------------
    public boolean tambahPeminjaman(String id, Ruangan ruangan, String hari, String jam) {

        // Ambil id ruangan dari objek ruangan
        String roomId = ruangan.getRoomId();

        // Buat key waktu dari hari dan jam peminjaman
        // Contoh: "Monday_10:00"
        String waktuKey = hari + "_" + jam;

        // Cek apakah roomId sudah punya entri di bookingIndex
        // Jika belum, buat ArrayList baru untuk ruangan ini
        if (!bookingIndex.containsKey(roomId)) {
            bookingIndex.put(roomId, new ArrayList<>());
        }

        // Ambil daftar waktu yang sudah terpakai untuk ruangan ini
        ArrayList<String> waktuTerpakai = bookingIndex.get(roomId);

        // Cek konflik: apakah waktu yang diminta sudah ada di daftar terpakai?
        if (waktuTerpakai.contains(waktuKey)) {
            // Ruangan sudah dipakai di waktu tersebut -> tolak peminjaman
            System.out.println("[BOOKING] GAGAL: Ruangan " + roomId
                    + " sudah dipakai pada " + waktuKey);
            return false; // kembalikan false menandakan gagal
        }

        // Tidak ada konflik -> buat objek Peminjaman baru
        // Constructor Peminjaman: (String id, Ruangan ruangan, String hari, String jam)
        Peminjaman peminjaman = new Peminjaman(id, ruangan, hari, jam);

        // Simpan objek Peminjaman ke ArrayList
        daftarPeminjaman.add(peminjaman);

        // Tandai waktu ini sebagai terpakai di HashMap index
        waktuTerpakai.add(waktuKey);
        bookingIndex.put(roomId, waktuTerpakai); // update HashMap dengan daftar terbaru

        System.out.println("[BOOKING] BERHASIL: Ruangan " + roomId
                + " dipinjam pada " + waktuKey);
        return true; // kembalikan true menandakan berhasil
    }

    // ----------------------------------------------------------
    // METHOD: batalPeminjaman
    // Membatalkan peminjaman yang sudah terdaftar
    // Digunakan saat swap disetujui: jadwal lama harus dibebaskan
    // Parameter:
    //   roomId - id ruangan yang ingin dibebaskan
    //   hari   - hari peminjaman lama
    //   jam    - jam peminjaman lama
    // Return:
    //   true  -> berhasil dibatalkan
    //   false -> tidak ditemukan entri yang cocok
    // ----------------------------------------------------------
    public boolean batalPeminjaman(String roomId, String hari, String jam) {

        // Buat key waktu yang ingin dibatalkan
        String waktuKey = hari + "_" + jam;

        // Cek apakah roomId ada di index
        if (!bookingIndex.containsKey(roomId)) {
            System.out.println("[BOOKING] Ruangan " + roomId + " tidak ditemukan di index.");
            return false;
        }

        // Ambil daftar waktu terpakai untuk ruangan ini
        ArrayList<String> waktuTerpakai = bookingIndex.get(roomId);

        // Coba hapus waktu yang ingin dibatalkan dari daftar
        boolean dihapusDariIndex = waktuTerpakai.remove(waktuKey);

        if (!dihapusDariIndex) {
            System.out.println("[BOOKING] Waktu " + waktuKey
                    + " tidak ditemukan untuk ruangan " + roomId);
            return false;
        }

        // Hapus juga objek Peminjaman yang sesuai dari ArrayList utama
        // Loop mundur agar aman saat menghapus elemen di tengah iterasi
        for (int i = daftarPeminjaman.size() - 1; i >= 0; i--) {
            Peminjaman p = daftarPeminjaman.get(i); // ambil elemen ke-i
            // Cocokkan roomId, hari, dan jam sekaligus
            if (p.getRuangan().getRoomId().equals(roomId)
                    && p.getHari().equals(hari)
                    && p.getJam().equals(jam)) {
                daftarPeminjaman.remove(i); // hapus dari ArrayList
                break; // cukup hapus satu, langsung keluar loop
            }
        }

        System.out.println("[BOOKING] Peminjaman ruangan " + roomId
                + " pada " + waktuKey + " berhasil dibatalkan.");
        return true;
    }

    // ----------------------------------------------------------
    // METHOD: isRuanganTersedia
    // Mengecek apakah ruangan tersedia pada hari dan jam tertentu
    // Digunakan oleh SwapService sebelum memvalidasi request
    // Return:
    //   true  -> ruangan BEBAS (bisa digunakan)
    //   false -> ruangan SUDAH TERPAKAI
    // ----------------------------------------------------------
    public boolean isRuanganTersedia(String roomId, String hari, String jam) {

        // Buat key waktu yang ingin dicek
        String waktuKey = hari + "_" + jam;

        // Jika roomId belum ada di index -> ruangan belum pernah dipesan -> tersedia
        if (!bookingIndex.containsKey(roomId)) {
            return true;
        }

        // contains() true = sudah terpakai, balik dengan "!" agar true = tersedia
        return !bookingIndex.get(roomId).contains(waktuKey);
    }

    // ----------------------------------------------------------
    // METHOD: getDaftarPeminjaman
    // Getter untuk mengakses seluruh daftar peminjaman (ArrayList)
    // ----------------------------------------------------------
    public ArrayList<Peminjaman> getDaftarPeminjaman() {
        return daftarPeminjaman;
    }

    // ----------------------------------------------------------
    // METHOD: getBookingIndex
    // Getter untuk mengakses HashMap index booking
    // ----------------------------------------------------------
    public HashMap<String, ArrayList<String>> getBookingIndex() {
        return bookingIndex;
    }

    // ----------------------------------------------------------
    // METHOD: tampilkanSemuaPeminjaman
    // Mencetak semua data peminjaman yang aktif ke konsol
    // ----------------------------------------------------------
    public void tampilkanSemuaPeminjaman() {
        System.out.println("=== DAFTAR PEMINJAMAN RUANGAN ===");

        if (daftarPeminjaman.isEmpty()) {
            System.out.println("(Belum ada peminjaman)");
            return;
        }

        // Loop setiap elemen ArrayList dan cetak via getPeminjamanInfo()
        // getPeminjamanInfo() sudah tersedia di class Peminjaman
        for (Peminjaman p : daftarPeminjaman) {
            System.out.println(p.getPeminjamanInfo());
        }
    }
}