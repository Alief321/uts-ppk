
package com.uts.IPK_IPS_Mahasiswa.controller;

import com.uts.IPK_IPS_Mahasiswa.entity.MataKuliah;
import com.uts.IPK_IPS_Mahasiswa.entity.Periode;
import com.uts.IPK_IPS_Mahasiswa.payload.request.MatkulRequest;
import com.uts.IPK_IPS_Mahasiswa.payload.response.MessageResponse;
import com.uts.IPK_IPS_Mahasiswa.repository.MataKuliahRepository;
import com.uts.IPK_IPS_Mahasiswa.repository.PeriodeRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.hateoas.HateoasProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    public ResponseEntity<?> createMatkul(@RequestBody MatkulRequest request){
        MataKuliah matkul = new MataKuliah();
        matkul.setName(request.getName());
        matkul.setJumlahSKS(request.getJumlahSks());
        Optional<Periode> periode = periodeRepository.findById(request.getPeriodeID());
        
        matkul.setPeriode(periode.get());
        
        mataKuliahRepository.save(matkul);
        return ResponseEntity.ok(new MessageResponse("Berhasil membuat mata kuliah"));
    }
}
