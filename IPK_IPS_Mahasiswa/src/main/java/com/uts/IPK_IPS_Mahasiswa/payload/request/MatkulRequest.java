
package com.uts.IPK_IPS_Mahasiswa.payload.request;

import com.uts.IPK_IPS_Mahasiswa.enumeration.KategoriMatkul;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatkulRequest {
    @NotBlank
    private String name;
    @NotBlank
    private int jumlahSks;
    @NotBlank
    private Long periodeID;
    @NotBlank
    private KategoriMatkul kategori;
    
    private String deskripsi;
}
