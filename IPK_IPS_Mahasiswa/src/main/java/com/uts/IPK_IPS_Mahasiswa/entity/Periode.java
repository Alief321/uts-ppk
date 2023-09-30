package com.uts.IPK_IPS_Mahasiswa.entity;

import com.uts.IPK_IPS_Mahasiswa.enumeration.SemesterEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "periode")
public class Periode{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private SemesterEnum semester;
    @Column(nullable = false)
    private String tahunPelajaran;
    
    @OneToMany(mappedBy= "periode")
    private List<Kelas> Kelas;
    
    @OneToOne(mappedBy= "periode")
    private IPS ips;
}
