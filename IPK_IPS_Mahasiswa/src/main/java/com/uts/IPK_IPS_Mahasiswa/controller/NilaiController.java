package com.uts.IPK_IPS_Mahasiswa.controller;

import com.uts.IPK_IPS_Mahasiswa.entity.MataKuliah;
import com.uts.IPK_IPS_Mahasiswa.entity.Nilai;
import com.uts.IPK_IPS_Mahasiswa.entity.Periode;
import com.uts.IPK_IPS_Mahasiswa.entity.User;
import com.uts.IPK_IPS_Mahasiswa.enumeration.Erole;
import com.uts.IPK_IPS_Mahasiswa.payload.request.NilaiRequest;
import com.uts.IPK_IPS_Mahasiswa.payload.response.MessageResponse;
import com.uts.IPK_IPS_Mahasiswa.payload.response.NilaiResponse;
import com.uts.IPK_IPS_Mahasiswa.repository.MataKuliahRepository;
import com.uts.IPK_IPS_Mahasiswa.repository.NilaiRepository;
import com.uts.IPK_IPS_Mahasiswa.repository.PeriodeRepository;
import com.uts.IPK_IPS_Mahasiswa.repository.UserRepository;
import com.uts.IPK_IPS_Mahasiswa.service.NilaiService;
import com.uts.IPK_IPS_Mahasiswa.service.UserActiveService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/nilai")
public class NilaiController {

    @Autowired
    MataKuliahRepository mataKuliahRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NilaiRepository nilaiRepository;

    @Autowired
    UserActiveService userActiveService;

    @Autowired
    PeriodeRepository periodeRepository;

    @Autowired
    NilaiService nilaiService;

    @PostMapping()
    public ResponseEntity<?> createNilai(@RequestBody NilaiRequest request) {
        User userActiv = userActiveService.getUserActive();

        System.out.println("matkul dosen :" + userActiv.getListAllMatkul());
        if (!userActiv.getListAllMatkul().contains(request.getMataKuliah())) {
            return ResponseEntity.ok(new MessageResponse("Dosen tidak mengampu matkul ini"));
        }

        System.out.println("id : " + request.getIdMahasiswa());
        System.out.println("Tugas : " + request.getNilaiTugas());
        System.out.println("matkul :" + request.getMataKuliah());
        Optional<User> user = userRepository.findById(request.getIdMahasiswa());
        User u = user.get();

        System.out.println(u.getRoles().toString().equals("[" + Erole.Mahasiswa.toString() + "]"));
        if (!u.getRoles().toString().equals("[" + Erole.Mahasiswa.toString() + "]")) {
            return ResponseEntity.ok(new MessageResponse("Role bukan mahasiswa"));
        }

        Optional<MataKuliah> matkul = mataKuliahRepository.findByName(request.getMataKuliah());
        MataKuliah mk = matkul.get();

        System.out.println("matkul " + mk.toString());
        Optional<Periode> periode = periodeRepository.findById(mk.getPeriode().getId());
        System.out.println("matkul " + periode.get().toString());

        List<Nilai> DaftarNilai = nilaiRepository.findByUser_Id(u.getId());
        if (DaftarNilai != null) {
            for (Nilai nilai : DaftarNilai) {
                if (Objects.equals(nilai.getPeriode().getId(), periode.get().getId()) && Objects.equals(nilai.getMataKuliah().getId(), mk.getId())) {
                    return ResponseEntity.ok(new MessageResponse("Gagal menginput nilai dikarenakan nilai sudah dibuat untuk user dengan matkul dan periode tersebut"));
                }
            }
        }
        System.out.println("lolos pengecekan");

        Nilai nilai = new Nilai();

        nilai.setNilai_Praktikum(request.getNilaiPraktikum());
        nilai.setNilai_Tugas(request.getNilaiTugas());
        nilai.setNilai_UAS(request.getNilaiUAS());
        nilai.setNilai_UTS(request.getNilaiUTS());
        nilai.setUser(u);
        nilai.setMataKuliah(mk);
        nilai.setPeriode(periode.get());

        float nilaiAngka = nilaiService.getNilaiangka(nilai);
        String nilaiHuruf = nilaiService.getNilaiHuruf(nilaiAngka);
        float bobot = nilaiService.getBobot(nilaiHuruf);

        System.out.println("nilai angka = " + nilaiAngka);
        nilai.setNilai_Angka(nilaiAngka);
        nilai.setNilai_Huruf(nilaiHuruf);
        nilai.setBobot(bobot);

        nilaiRepository.save(nilai);

        NilaiResponse nilaires = new NilaiResponse();
        nilaires.setMataKuliah(nilai.getMataKuliah().toString());
        nilaires.setNilaiPraktikum(nilai.getNilai_Praktikum());
        nilaires.setNilaiTugas(nilai.getNilai_Tugas());
        nilaires.setNilaiUTS(nilai.getNilai_UTS());
        nilaires.setNilaiUAS(nilai.getNilai_UAS());
        nilaires.setNilaiHuruf(nilai.getNilai_Huruf());
        nilaires.setNilaiAngka(nilai.getNilai_Angka());
        nilaires.setBobot(nilai.getBobot());
        nilaires.setMahasiswa(nilai.getUser().getName());
        
        return ResponseEntity.ok(nilaires);
    }

    @GetMapping("/mahasiswa/{id}")
    public ResponseEntity<?> read(@PathVariable("id") Long userID) {

        List<Nilai> nilai = nilaiRepository.findByUser_Id(userID);

        List<NilaiResponse> listres = new ArrayList<>();
        for (Nilai n : nilai) {
            NilaiResponse nilaires = new NilaiResponse();
            nilaires.setMataKuliah(n.getMataKuliah().toString());
            nilaires.setNilaiPraktikum(n.getNilai_Praktikum());
            nilaires.setNilaiTugas(n.getNilai_Tugas());
            nilaires.setNilaiUTS(n.getNilai_UTS());
            nilaires.setNilaiUAS(n.getNilai_UAS());
            nilaires.setNilaiHuruf(n.getNilai_Huruf());
            nilaires.setNilaiAngka(n.getNilai_Angka());
            nilaires.setBobot(n.getBobot());
            nilaires.setMahasiswa(n.getUser().getName());
            listres.add(nilaires);
        }

        return ResponseEntity.ok(listres);
    }

    @GetMapping()
    public ResponseEntity<?> getAllNilai() {

        List<Nilai> nilai = (List<Nilai>) nilaiRepository.findAll();

        List<NilaiResponse> listres = new ArrayList<>();
        for (Nilai n : nilai) {
            NilaiResponse nilaires = new NilaiResponse();
            nilaires.setMataKuliah(n.getMataKuliah().toString());
            nilaires.setNilaiPraktikum(n.getNilai_Praktikum());
            nilaires.setNilaiTugas(n.getNilai_Tugas());
            nilaires.setNilaiUTS(n.getNilai_UTS());
            nilaires.setNilaiUAS(n.getNilai_UAS());
            nilaires.setNilaiHuruf(n.getNilai_Huruf());
            nilaires.setNilaiAngka(n.getNilai_Angka());
            nilaires.setBobot(n.getBobot());
            nilaires.setMahasiswa(n.getUser().getName());
            listres.add(nilaires);
        }

        return ResponseEntity.ok(listres);
    }
}
