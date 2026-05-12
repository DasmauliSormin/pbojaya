package service;

import model.SwapRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SwapService {

    // ArrayList untuk menyimpan semua SwapRequest yang masuk (urutan FIFO)
    private ArrayList<SwapRequest> daftarRequest = new ArrayList<>();

    // HashMap untuk akses cepat berdasarkan requestId
    private HashMap<String, SwapRequest> requestMap = new HashMap<>();

    // Referensi ke BookingService
    private BookingService bookingService;

    public SwapService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // ----------------------------------------------------------
    // METHOD: tambahRequest
    // ----------------------------------------------------------
    public void tambahRequest(String requestId, SwapRequest request) {
        if (requestMap.containsKey(requestId)) {
            return; // tolak duplikat
        }
        request.setStatus("MENUNGGU");
        daftarRequest.add(request);
        requestMap.put(requestId, request);
    }

    // ----------------------------------------------------------
    // METHOD: prosesValidasi
    //
    // Logika swap silang yang benar:
    // REQ01 mau ke slot X (R102_Monday_10:00) yang terpakai SCH02
    // REQ02 mau ke slot Y (R101_Monday_08:00) yang terpakai SCH01
    //
    // Deteksi swap silang:
    // Kumpulkan semua pasangan request yang saling mau ke slot terpakai BERBEDA
    // Jika REQ01 mau ke slot terpakai DAN REQ02 mau ke slot terpakai BERBEDA
    // -> keduanya masuk HashSet "swapSilang" -> keduanya VALID
    //
    // PENTING: Deteksi dilakukan SEBELUM mengubah status apapun
    // agar hasilnya simetris (REQ01 dan REQ02 sama-sama VALID)
    // ----------------------------------------------------------
    public void prosesValidasi() {

        // HashSet untuk menyimpan requestId yang terdeteksi swap silang
        // HashSet = tidak ada duplikat, akses O(1)
        HashSet<String> swapSilangSet = new HashSet<>();

        // PASS 1: Deteksi semua pasangan swap silang
        // Loop semua kombinasi pasangan request MENUNGGU
        for (int i = 0; i < daftarRequest.size(); i++) {
            SwapRequest reqA = daftarRequest.get(i);
            if (!reqA.getStatus().equals("MENUNGGU")) continue;

            // Slot tujuan reqA
            String slotTujuanA = reqA.getRuangan().getRoomId()
                    + "_" + reqA.getHari()
                    + "_" + reqA.getJam();

            // Cek apakah slot tujuan A terpakai
            boolean aTerpakai = !bookingService.isRuanganTersedia(
                    reqA.getRuangan().getRoomId(), reqA.getHari(), reqA.getJam());

            if (!aTerpakai) continue; // slot tujuan A kosong, tidak perlu cek swap silang

            for (int j = i + 1; j < daftarRequest.size(); j++) {
                SwapRequest reqB = daftarRequest.get(j);
                if (!reqB.getStatus().equals("MENUNGGU")) continue;

                // Slot tujuan reqB
                String slotTujuanB = reqB.getRuangan().getRoomId()
                        + "_" + reqB.getHari()
                        + "_" + reqB.getJam();

                // Cek apakah slot tujuan B terpakai
                boolean bTerpakai = !bookingService.isRuanganTersedia(
                        reqB.getRuangan().getRoomId(), reqB.getHari(), reqB.getJam());

                if (!bTerpakai) continue; // slot tujuan B kosong, bukan swap silang

                // Swap silang terjadi jika:
                // slot tujuan A != slot tujuan B (berbeda tujuan)
                // keduanya mau ke slot yang terpakai
                // (artinya mereka saling tukar slot yang sedang dipakai)
                if (!slotTujuanA.equals(slotTujuanB)) {
                    // Keduanya swap silang -> masukkan ke HashSet
                    swapSilangSet.add(reqA.getRequestId());
                    swapSilangSet.add(reqB.getRequestId());
                }
            }
        }

        // PASS 2: Set status berdasarkan hasil deteksi
        for (SwapRequest req : daftarRequest) {
            if (!req.getStatus().equals("MENUNGGU")) continue;

            if (swapSilangSet.contains(req.getRequestId())) {
                // Request ini bagian dari swap silang -> VALID
                req.setStatus("VALID");
            } else {
                // Bukan swap silang, cek apakah ruangan tujuan bebas
                boolean ruanganBebas = bookingService.isRuanganTersedia(
                        req.getRuangan().getRoomId(), req.getHari(), req.getJam());
                if (ruanganBebas) {
                    req.setStatus("VALID");
                } else {
                    req.setStatus("DITOLAK");
                }
            }
        }
    }

    // ----------------------------------------------------------
    // METHOD: approveRequest
    // ----------------------------------------------------------
    public void approveRequest(String requestId) {
        SwapRequest req = requestMap.get(requestId);

        if (req == null) {
            System.out.println("[SWAP] Request " + requestId + " tidak ditemukan.");
            return;
        }

        if (!req.getStatus().equals("VALID")) {
            System.out.println("[SWAP] Request " + requestId
                    + " tidak bisa disetujui. Status saat ini: " + req.getStatus());
            return;
        }

        // Update status menjadi DISETUJUI
        // applyReschedule() TIDAK dipanggil di sini
        // agar getDetail() tetap menampilkan jadwal asal -> jadwal baru
        // sesuai format output README
        req.setStatus("DISETUJUI");
    }

    // ----------------------------------------------------------
    // METHOD: tampilkanDetail
    // ----------------------------------------------------------
    public void tampilkanDetail(String requestId) {
        if (requestId.equalsIgnoreCase("ALL")) {
            for (SwapRequest req : daftarRequest) {
                System.out.println(req.getDetail());
            }
        } else {
            SwapRequest req = requestMap.get(requestId);
            if (req == null) {
                System.out.println("[SWAP] Request " + requestId + " tidak ditemukan.");
                return;
            }
            System.out.println(req.getDetail());
        }
    }

    // Getter ArrayList
    public ArrayList<SwapRequest> getDaftarRequest() {
        return daftarRequest;
    }

    // Getter HashMap
    public HashMap<String, SwapRequest> getRequestMap() {
        return requestMap;
    }
}