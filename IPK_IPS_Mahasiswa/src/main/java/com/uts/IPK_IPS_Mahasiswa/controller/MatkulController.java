package com.uts.IPK_IPS_Mahasiswa.controller;

import com.uts.IPK_IPS_Mahasiswa.entity.MataKuliah;
import com.uts.IPK_IPS_Mahasiswa.entity.Periode;
import com.uts.IPK_IPS_Mahasiswa.payload.request.MatkulRequest;
import com.uts.IPK_IPS_Mahasiswa.payload.response.MatkulResponse;
import com.uts.IPK_IPS_Mahasiswa.repository.MataKuliahRepository;
import com.uts.IPK_IPS_Mahasiswa.repository.PeriodeRepository;
import com.uts.IPK_IPS_Mahasiswa.service.MatkulService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/matkul")
public class MatkulController {

    @Autowired
    PeriodeRepository periodeRepository;
    @Autowired
    MataKuliahRepository mataKuliahRepository;
    @Autowired
    MatkulService matakuliahservice;
   

    @PostMapping()
    public ResponseEntity<?> createMatkul(@RequestBody MatkulRequest request) {
        MataKuliah matkul = new MataKuliah();
        matkul.setName(request.getName());
        matkul.setJumlahSKS(request.getJumlahSks());
        matkul.setDeskripsi(request.getDeskripsi());
        matkul.setKategori(request.getKategori());
        Optional<Periode> periode = periodeRepository.findById(request.getPeriodeID());

        matkul.setPeriode(periode.get());

        mataKuliahRepository.save(matkul);

        MatkulResponse mkres = new MatkulResponse();
        mkres.setId(matkul.getId());
        mkres.setNama(matkul.getName());
        mkres.setJumlahSKS(matkul.getJumlahSKS());
        mkres.setKategori(matkul.getKategori().name());
        mkres.setDeskripsi(matkul.getDeskripsi());
        mkres.setPeriode(matkul.getPeriode().getSemester().toString());

        return ResponseEntity.ok(mkres);
    }

    @GetMapping()
    public ResponseEntity<?> getAllMatkul(@RequestParam(required = false) String name, Long periode) {

        List<MataKuliah> list = new ArrayList<>();
        if (periode != null) {
            Optional<Periode> p = periodeRepository.findById(periode);
            Periode periodeAsli = p.orElse(new Periode());
            list = mataKuliahRepository.findByPeriode(periodeAsli);
            if(name != null){
               list = mataKuliahRepository.findByNameContainingAndPeriode(name, periodeAsli);
            }
        } else {
            if (name != null) {
                list = matakuliahservice.findMataKuliahByNameList(name);
            } else {
                list = (List<MataKuliah>) mataKuliahRepository.findAll();
            }
        }
        
        List<MatkulResponse> listRes = new ArrayList<>();
        for (MataKuliah mk : list) {
            MatkulResponse mkres = new MatkulResponse();
            mkres.setId(mk.getId());
            mkres.setNama(mk.getName());
            mkres.setKategori(mk.getKategori().name());
            mkres.setJumlahSKS(mk.getJumlahSKS());
            mkres.setPeriode(mk.getPeriode().getSemester().toString());
            listRes.add(mkres);
        }

        Comparator<MatkulResponse> periodeComparator = (c1, c2) -> c1.getPeriode().compareTo(c2.getPeriode());
        listRes.sort(periodeComparator);
        return ResponseEntity.ok(listRes);
    }
}
