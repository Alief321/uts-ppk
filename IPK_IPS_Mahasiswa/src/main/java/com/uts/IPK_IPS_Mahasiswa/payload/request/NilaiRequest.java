package com.uts.IPK_IPS_Mahasiswa.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NilaiRequest {

    @NotBlank
    private float nilaiUTS;
    @NotBlank 
    private float nilaiUAS;
    @NotBlank
    private float nilaiTugas;

    private float nilaiPraktikum;
    
    @NotBlank
    private String mataKuliah;
    @NotBlank
    private Long idMahasiswa;
}