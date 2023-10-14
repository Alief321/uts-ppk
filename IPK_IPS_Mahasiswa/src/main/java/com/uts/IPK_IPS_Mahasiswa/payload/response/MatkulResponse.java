
package com.uts.IPK_IPS_Mahasiswa.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatkulResponse {
    private Long id;
    private String nama;
    private String kategori;
    private String periode;
}
