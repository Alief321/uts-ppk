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
public class SetKelasMahasiswa {
    @NotBlank
    private String email;
    
    @NotBlank
    private String namaKelas;
}
