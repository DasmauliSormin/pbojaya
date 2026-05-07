package mapper;
// mapper = package khusus untuk mengubah data mentah menjadi object model

import model.Ruangan;
// import class Ruangan dari package model

public class RuanganMapper {
    // RuanganMapper = class yang bertugas mengubah data String[] menjadi object Ruangan
    // dan sebaliknya

    /**
     * METHOD: fromArray
     * Tujuan : Mengubah array String menjadi object Ruangan
     * Dipakai: Saat membaca input CLI seperti "room-add#R101#GedungA#40"
     *
     * Contoh input array:
     * data[0] = "R101"
     * data[1] = "GedungA"
     * data[2] = "40"
     */
    public static Ruangan fromArray(String[] data) {
        String roomId   = data[0].trim();
        // data[0] = ID ruangan (contoh: R101)

        String gedung   = data[1].trim();
        // data[1] = nama gedung (contoh: GedungA)

        int kapasitas   = Integer.parseInt(data[2].trim());
        // Integer.parseInt() = mengubah String "40" menjadi angka 40
        // Perlu dikonversi karena kapasitas di class Ruangan bertipe int, bukan String

        return new Ruangan(roomId, gedung, kapasitas);
        // Membuat dan mengembalikan object Ruangan baru dengan data yang sudah dikonversi
    }

    /**
     * METHOD: toArray
     * Tujuan : Mengubah object Ruangan kembali menjadi array String
     * Dipakai: Saat ingin menyimpan data Ruangan ke file atau database
     */
    public static String[] toArray(Ruangan ruangan) {
        return new String[]{
            ruangan.getRoomId(),                      // mengambil ID ruangan
            ruangan.getGedung(),                      // mengambil nama gedung
            String.valueOf(ruangan.getKapasitas())    // mengubah int kapasitas → String
            // String.valueOf() = kebalikan dari Integer.parseInt()
            // mengubah angka menjadi teks agar bisa disimpan dalam array String
        };
    }
}