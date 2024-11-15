package com.uts.IPK_IPS_Mahasiswa.payload.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {
    private Long id;
    private String name;
    private String email;
    private String nim;
    private String nip;
    private String kelas;
    private List<String> matkulAmpu;
}
