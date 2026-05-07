package mapper;
// mapper = package khusus untuk class-class yang bertugas mengubah data mentah menjadi object

import model.User;
// import = mengambil class User dari package model agar bisa dipakai di sini

public class UserMapper {
    // UserMapper = class yang bertugas mengubah data String[] menjadi object User
    // dan sebaliknya (object User → String[])

    /**
     * METHOD: fromArray
     * Tujuan : Mengubah array String menjadi object User
     * Dipakai: Saat membaca input CLI seperti "lecturer-add#L001#Dr. Andi#andi@kampus.ac.id"
     *
     * Contoh input array:
     * data[0] = "L001"
     * data[1] = "Dr. Andi"
     * data[2] = "andi@kampus.ac.id"
     */
    public static User fromArray(String[] data) {
        // static = bisa dipanggil tanpa membuat object UserMapper terlebih dahulu
        // Contoh pemakaian: UserMapper.fromArray(data)

        String id    = data[0].trim();
        // trim() = menghapus spasi yang tidak sengaja ada di depan/belakang teks
        // data[0] = elemen pertama array = ID user (contoh: L001)

        String name  = data[1].trim();
        // data[1] = elemen kedua array = nama user (contoh: Dr. Andi)

        String email = data[2].trim();
        // data[2] = elemen ketiga array = email user (contoh: andi@kampus.ac.id)

        return new User(id, name, email);
        // return = mengembalikan object User yang sudah terisi datanya
        // new User(...) = membuat object User baru menggunakan constructor dari class User
    }

    /**
     * METHOD: toArray
     * Tujuan : Mengubah object User kembali menjadi array String
     * Dipakai: Saat ingin menyimpan data User ke file atau database
     */
    public static String[] toArray(User user) {
        return new String[]{
            user.getId(),    // mengambil ID user via getter
            user.getName(),  // mengambil nama user via getter
            user.getEmail()  // mengambil email user via getter
        };
        // new String[]{...} = membuat array String baru langsung dengan isinya
    }
}