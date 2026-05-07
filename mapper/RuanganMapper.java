

import model.Ruangan;

public class RuanganMapper {

    // Format input: ["R101", "GedungA", "40"]
    public static Ruangan fromArray(String[] data) {
        String roomId   = data[0].trim();
        String gedung   = data[1].trim();
        int kapasitas   = Integer.parseInt(data[2].trim());
        return new Ruangan(roomId, gedung, kapasitas);
    }

    public static String[] toArray(Ruangan ruangan) {
        return new String[]{
            ruangan.getRoomId(),
            ruangan.getGedung(),
            String.valueOf(ruangan.getKapasitas())
        };
    }
}