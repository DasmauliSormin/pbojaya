package dao;

// import library SQL untuk koneksi database
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// import struktur data List untuk menampung banyak object
import java.util.ArrayList;
import java.util.List;

// import class User dari package model
import model.User;

public class UserDao {

    /*
     * Method ini digunakan untuk mengambil seluruh data user
     * dari database MySQL kemudian mengubahnya menjadi object User
     * lalu disimpan dalam sebuah List.
     */
    public List<User> getAllUsers() {

        // List untuk menyimpan semua object User hasil dari database
        List<User> list = new ArrayList<>();

        try {

            /*
             * Mengambil koneksi database dari class DBConnection
             * DBConnection berfungsi sebagai penghubung antara Java dan MySQL
             */
            Connection conn = DBConnection.getConnection();

            /*
             * Query SQL untuk mengambil semua data dari tabel users
             */
            String sql = "SELECT * FROM users";

            /*
             * PreparedStatement digunakan untuk menjalankan query SQL
             * dengan cara yang lebih aman dibanding Statement biasa
             */
            PreparedStatement ps = conn.prepareStatement(sql);

            /*
             * executeQuery() digunakan untuk menjalankan query SELECT
             * hasilnya akan disimpan dalam object ResultSet
             */
            ResultSet rs = ps.executeQuery();

            /*
             * while digunakan untuk membaca data dari ResultSet
             * selama masih ada baris data yang tersedia
             */
            while (rs.next()) {

                /*
                 * Membuat object User dari data yang diambil dari database
                 * rs.getString("nama_kolom") digunakan untuk mengambil nilai
                 * dari kolom tertentu pada tabel database
                 */
                User u = new User(
                        rs.getString("user_id"), // mengambil kolom user_id
                        rs.getString("nama"),    // mengambil kolom nama
                        rs.getString("email")    // mengambil kolom email
                );

                /*
                 * Object User yang sudah dibuat dimasukkan
                 * ke dalam List agar bisa digunakan nanti
                 */
                list.add(u);
            }

        } catch (Exception e) {

            /*
             * Jika terjadi error saat koneksi atau query
             * maka error akan ditampilkan di console
             */
            e.printStackTrace();
        }

        /*
         * Mengembalikan seluruh data user dalam bentuk List
         */
        return list;
    }
}
//mutiara