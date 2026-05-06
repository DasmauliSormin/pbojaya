package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Ruangan;

public class RuanganDao {

    /*
     * Method untuk mengambil semua data ruangan dari database
     */
    public List<Ruangan> getAllRuangan() {

        // List untuk menyimpan data ruangan
        List<Ruangan> list = new ArrayList<>();

        try {

            // mengambil koneksi database
            Connection conn = DBConnection.getConnection();

            // query untuk mengambil semua data ruangan
            String sql = "SELECT * FROM ruangan";

            // menyiapkan query
            PreparedStatement ps = conn.prepareStatement(sql);

            // menjalankan query dan menyimpan hasilnya
            ResultSet rs = ps.executeQuery();

            // membaca data hasil query satu per satu
            while (rs.next()) {

                // membuat object Ruangan dari data database
                Ruangan r = new Ruangan(
                        rs.getString("room_id"),
                        rs.getString("gedung"),
                        rs.getInt("kapasitas")
                );

                // menambahkan object ke dalam list
                list.add(r);
            }

        } catch (Exception e) {
            // menampilkan error jika terjadi kesalahan
            e.printStackTrace();
        }

        // mengembalikan semua data ruangan
        return list;
    }
}