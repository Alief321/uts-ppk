package com.uts.IPK_IPS_Mahasiswa.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IPSResponse {
    private Long id;
    private float ips;
    private String namaMahasiswa;
    private String periode;
}
