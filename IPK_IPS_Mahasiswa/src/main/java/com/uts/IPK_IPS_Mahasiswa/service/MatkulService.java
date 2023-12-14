package com.uts.IPK_IPS_Mahasiswa.service;

import com.uts.IPK_IPS_Mahasiswa.entity.MataKuliah;
import com.uts.IPK_IPS_Mahasiswa.repository.MataKuliahRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatkulService {
    @Autowired
    private MataKuliahRepository mataKuliahRepository;

    public Optional<MataKuliah> findMataKuliahByNameOptional(String name) {
        return mataKuliahRepository.findByName(name);
    }
    
    public List<MataKuliah> findMataKuliahByNameList(String name){
        return mataKuliahRepository.findByNameContains(name);
    }
}
