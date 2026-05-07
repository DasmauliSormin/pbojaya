

import model.Jadwal;
import model.Ruangan;

public class JadwalMapper {

    public static Jadwal fromArray(String[] data, Ruangan ruangan) {
        String scheduleId = data[0].trim();
        String courseId   = data[1].trim();
        String kelas      = data[2].trim();
        String lecturerId = data[3].trim();
        String hari       = data[4].trim();
        String jam        = data[5].trim();

        return new Jadwal(scheduleId, courseId, kelas, lecturerId, hari, jam, ruangan);
    }

    public static String[] toArray(Jadwal jadwal) {
        return new String[]{
            jadwal.getScheduleId(),
            jadwal.getCourseId(),
            jadwal.getKelas(),
            jadwal.getLecturerId(),
            jadwal.getHari(),
            jadwal.getJam(),
            jadwal.getRuangan().getRoomId()
        };
    }

    public static String toDisplay(Jadwal jadwal) {
        return jadwal.getFullInfo();
    }
}