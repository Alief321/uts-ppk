
package com.uts.IPK_IPS_Mahasiswa.payload.request;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SetMatkulRequest {
    
    @NotBlank
    private Long idDosen;
    
    @NotBlank
    private List<String> mataKuliah;
}
