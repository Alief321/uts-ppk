
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
public class MatkulRequest {
    @NotBlank
    private String name;
    @NotBlank
    private int jumlahSks;
    @NotBlank
    private Long periodeID;
}