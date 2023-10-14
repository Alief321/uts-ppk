package com.uts.IPK_IPS_Mahasiswa.controller;

import com.uts.IPK_IPS_Mahasiswa.entity.MataKuliah;
import com.uts.IPK_IPS_Mahasiswa.entity.Periode;
import com.uts.IPK_IPS_Mahasiswa.payload.request.MatkulRequest;
import com.uts.IPK_IPS_Mahasiswa.payload.response.MatkulResponse;
import com.uts.IPK_IPS_Mahasiswa.repository.MataKuliahRepository;
import com.uts.IPK_IPS_Mahasiswa.repository.PeriodeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/matkul")
public class MatkulController {

    @Autowired
    PeriodeRepository periodeRepository;
    @Autowired
    MataKuliahRepository mataKuliahRepository;

    @PostMapping()
    public ResponseEntity<?> createMatkul(@RequestBody MatkulRequest request) {
        MataKuliah matkul = new MataKuliah();
        matkul.setName(request.getName());
        matkul.setJumlahSKS(request.getJumlahSks());
        matkul.setKategori(request.getKategori());
        Optional<Periode> periode = periodeRepository.findById(request.getPeriodeID());

        matkul.setPeriode(periode.get());

        mataKuliahRepository.save(matkul);

        MatkulResponse mkres = new MatkulResponse();
        mkres.setId(matkul.getId());
        mkres.setNama(matkul.getName());
        mkres.setKategori(matkul.getKategori().name());
        mkres.setPeriode(matkul.getPeriode().getSemester().toString());

        return ResponseEntity.ok(mkres);
    }

    @GetMapping()
    public ResponseEntity<?> getAllMatkul() {
        List<MataKuliah> list = (List<MataKuliah>) mataKuliahRepository.findAll();

        List<MatkulResponse> listRes = new ArrayList<>();
        for (MataKuliah mk : list) {
            MatkulResponse mkres = new MatkulResponse();
            mkres.setId(mk.getId());
            mkres.setNama(mk.getName());
            mkres.setKategori(mk.getKategori().name());
            mkres.setPeriode(mk.getPeriode().getSemester().toString());
            listRes.add(mkres);
        }
        return ResponseEntity.ok(listRes);
    }
}
