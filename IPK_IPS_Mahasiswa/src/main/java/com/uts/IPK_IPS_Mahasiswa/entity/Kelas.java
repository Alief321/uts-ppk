package com.uts.IPK_IPS_Mahasiswa.entity;

import com.uts.IPK_IPS_Mahasiswa.enumeration.JurusanEnum;
import com.uts.IPK_IPS_Mahasiswa.enumeration.PeminatanEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Kelas")
public class Kelas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int tingkat;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JurusanEnum jurusan;
    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private PeminatanEnum peminatan;
    @Column(nullable = false, unique = true)
    private String namaKelas;
    
    @OneToMany(mappedBy = "kelasSekarang")
    private List<User> user;
    
}
