package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Peminjaman;
import model.Ruangan;

public class PeminjamanDao {

    // Mengambil seluruh data peminjaman dari database
    public List<Peminjaman> getAllPeminjaman() {

        List<Peminjaman> list = new ArrayList<>();

        try {

            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM peminjaman";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                // Membuat object ruangan
                Ruangan r = new Ruangan(
                        rs.getString("ruangan_id"),
                        "Gedung",
                        0
                );

                // Membuat object peminjaman
                Peminjaman p = new Peminjaman(
                        rs.getString("peminjaman_id"),
                        r,
                        rs.getString("hari"),
                        rs.getString("jam")
                );

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
//mutiara