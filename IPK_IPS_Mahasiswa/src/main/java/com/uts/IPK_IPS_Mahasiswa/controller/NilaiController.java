package com.uts.IPK_IPS_Mahasiswa.controller;

import com.uts.IPK_IPS_Mahasiswa.entity.MataKuliah;
import com.uts.IPK_IPS_Mahasiswa.entity.Nilai;
import com.uts.IPK_IPS_Mahasiswa.entity.User;
import com.uts.IPK_IPS_Mahasiswa.enumeration.Erole;
import com.uts.IPK_IPS_Mahasiswa.payload.request.NilaiRequest;
import com.uts.IPK_IPS_Mahasiswa.payload.response.MessageResponse;
import com.uts.IPK_IPS_Mahasiswa.repository.MataKuliahRepository;
import com.uts.IPK_IPS_Mahasiswa.repository.NilaiRepository;
import com.uts.IPK_IPS_Mahasiswa.repository.UserRepository;
import com.uts.IPK_IPS_Mahasiswa.service.UserActiveService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class NilaiController {
    
    @Autowired
    MataKuliahRepository mataKuliahRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    NilaiRepository nilaiRepository;
    
    @Autowired
    UserActiveService userActiveService;
    
    @PostMapping("/nilai/create")
    public ResponseEntity<?> createNilai(@RequestBody NilaiRequest request){
        User userActiv = userActiveService.getUserActive();
        
        System.out.println("matkul dosen :" +userActiv.getListAllMatkul());
        if (!userActiv.getListAllMatkul().contains(request.getMataKuliah())) {
            return ResponseEntity.ok(new MessageResponse("Dosen tidak mengampu matkul ini"));
        }
        
        System.out.println("id : "+ request.getIdMahasiswa());
        System.out.println("Tugas : "+ request.getNilaiTugas());
        System.out.println("matkul :"+ request.getMataKuliah());
        Optional<User> user = userRepository.findById(request.getIdMahasiswa());
        User u = user.get();
        
        System.out.println(u.getRoles().toString().equals("["+Erole.Mahasiswa.toString()+"]"));
        if(!u.getRoles().toString().equals("["+Erole.Mahasiswa.toString()+"]") ) {
            return ResponseEntity.ok(new MessageResponse("Role bukan mahasiswa"));
        }
        
        Optional<MataKuliah> matkul = mataKuliahRepository.findByName(request.getMataKuliah());
        MataKuliah mk = matkul.get();
        
        Nilai nilai = new Nilai();
        
        nilai.setNilai_Praktikum(request.getNilaiPraktikum());
        nilai.setNilai_Tugas(request.getNilaiTugas());
        nilai.setNilai_UAS(request.getNilaiUAS());
        nilai.setNilai_UTS(request.getNilaiUTS());
        nilai.setUser(u);
        nilai.setMataKuliah(mk);
        
        nilaiRepository.save(nilai);
        
      return ResponseEntity.ok(new MessageResponse("Berhasil membuat nilai"));
    }
}
