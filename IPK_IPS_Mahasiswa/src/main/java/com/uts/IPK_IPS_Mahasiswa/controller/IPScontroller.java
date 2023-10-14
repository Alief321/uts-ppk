package com.uts.IPK_IPS_Mahasiswa.controller;

import com.uts.IPK_IPS_Mahasiswa.entity.IPS;
import com.uts.IPK_IPS_Mahasiswa.entity.Nilai;
import com.uts.IPK_IPS_Mahasiswa.entity.User;
import com.uts.IPK_IPS_Mahasiswa.enumeration.Erole;
import com.uts.IPK_IPS_Mahasiswa.payload.response.IPSResponse;
import com.uts.IPK_IPS_Mahasiswa.payload.response.MessageResponse;
import com.uts.IPK_IPS_Mahasiswa.repository.IPSrepository;
import com.uts.IPK_IPS_Mahasiswa.repository.NilaiRepository;
import com.uts.IPK_IPS_Mahasiswa.repository.PeriodeRepository;
import com.uts.IPK_IPS_Mahasiswa.repository.UserRepository;
import com.uts.IPK_IPS_Mahasiswa.service.NilaiService;
import com.uts.IPK_IPS_Mahasiswa.service.UserActiveService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/ips")
public class IPScontroller {

    @Autowired
    NilaiRepository nilaiRepository;
    @Autowired
    IPSrepository ipsRepository;
    @Autowired
    NilaiService nilaiService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PeriodeRepository periodeRepository;
    @Autowired
    UserActiveService userActiveService;

    @PostMapping()
    public ResponseEntity<?> generateIPS() {
        Iterable<Nilai> daftarNilai = nilaiRepository.findAll();
        Iterable<IPS> daftarIPS = ipsRepository.findAll();

        int j = 0;
        for (IPS ips : daftarIPS) {
            j += 1;
        }

        System.out.println("j = " + j);
        List<Nilai> NilaiBaru = new ArrayList<>();

        Map<Long, Map<Long, List<Nilai>>> grouppedNilai = new HashMap<>();

        if (j != 0) {
            daftarNilai.forEach(n -> {
                boolean isDuplicate = false;

                for (IPS i : daftarIPS) {
                    if (n.getUser().equals(i.getUser()) && n.getPeriode().equals(i.getPeriode())) {
                        isDuplicate = true;
                        break;
                    }

                }
                if (!isDuplicate) {
                    System.out.println("Nilai belum terdaftar menambahkan nilai");
                    System.out.println("nilai: " + n.getId());
                    NilaiBaru.add(n);
                }
            });
        } else {
            daftarNilai.forEach(n -> {
                System.out.println("Nilai belum terdaftar menambahkan nilai");
                System.out.println("nilai: " + n.getId());
                NilaiBaru.add(n);
            });
        }
        for (Nilai nilai : NilaiBaru) {
            Long idPeriode = nilai.getPeriode().getId();
            Long idUser = nilai.getUser().getId();

            Map<Long, List<Nilai>> innerMap = grouppedNilai.computeIfAbsent(idPeriode, p -> new HashMap<>());
            innerMap.computeIfAbsent(idUser, u -> new ArrayList<>()).add(nilai);
        }

        for (Long idPeriode
                : grouppedNilai.keySet()) {
            Map<Long, List<Nilai>> innerMap = grouppedNilai.get(idPeriode);

            for (Long idUser : innerMap.keySet()) {
                List<Nilai> nilaiList = innerMap.get(idUser);
                IPS ips = new IPS();
                System.out.println("data dengan idperiode = " + idPeriode + " idUSER = " + idUser);

                ips.setIps(nilaiService.getIPS(nilaiList));
                ips.setUser(userRepository.findById(idUser).get());
                ips.setPeriode(periodeRepository.findById(idPeriode).get());

                ipsRepository.save(ips);

                for (Nilai nilai : nilaiList) {
                    System.out.println(nilai);
                }
            }
        }

        return ResponseEntity.ok(new MessageResponse("Berhasil generate IPS"));
    }

    @GetMapping("/mahasiswa")
    public ResponseEntity<?> getIPSself() {
        User u = userActiveService.getUserActive();
        if (!u.getRoles().toString().equals("[" + Erole.Mahasiswa.toString() + "]")) {
            return ResponseEntity.ok(new MessageResponse("User bukan mahasiswa"));
        }
        List<IPS> ips = ipsRepository.findByUserId(u.getId());

        List<IPSResponse> ipres = new ArrayList<>();

        for (IPS ip : ips) {
            IPSResponse ipMhs = new IPSResponse();
            ipMhs.setId(ip.getId());
            System.out.println("ips " + ip.getIps());
            ipMhs.setNamaMahasiswa(ip.getUser().getName());
            ipMhs.setIps(ip.getIps());
            ipMhs.setPeriode(ip.getPeriode().getSemester().toString());
            ipres.add(ipMhs);
        }

        return ResponseEntity.ok(ipres);
    }

    @GetMapping("/mahasiswa/{id}")
    public ResponseEntity<?> read(@PathVariable("id") Long userID) {
        Optional<User> user = userRepository.findById(userID);
        User u = user.get();
        if (!u.getRoles().toString().equals("[" + Erole.Mahasiswa.toString() + "]")) {
            return ResponseEntity.ok(new MessageResponse("User bukan mahasiswa"));
        }
        List<IPS> ips = ipsRepository.findByUserId(u.getId());

        List<IPSResponse> ipres = new ArrayList<>();

        for (IPS ip : ips) {
            IPSResponse ipMhs = new IPSResponse();
            ipMhs.setId(ip.getId());
            ipMhs.setNamaMahasiswa(ip.getUser().getName());
            ipMhs.setIps(ip.getIps());
            ipMhs.setPeriode(ip.getPeriode().getSemester().toString());
            ipres.add(ipMhs);
        }

        return ResponseEntity.ok(ipres);
    }
}
