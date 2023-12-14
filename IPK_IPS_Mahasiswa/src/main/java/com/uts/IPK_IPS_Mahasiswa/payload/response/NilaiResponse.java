
package com.uts.IPK_IPS_Mahasiswa.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.uts.IPK_IPS_Mahasiswa.entity.Periode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NilaiResponse {
    private String mataKuliah;
    private float nilaiTugas;
    private float nilaiPraktikum;
    private float nilaiUTS;
    private float nilaiUAS;
    private String nilaiHuruf;
    private float nilaiAngka;
    private float bobot;
    private String mahasiswa;
    private String periode;
}
