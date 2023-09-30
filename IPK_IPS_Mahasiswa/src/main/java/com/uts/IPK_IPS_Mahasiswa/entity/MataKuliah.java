package com.uts.IPK_IPS_Mahasiswa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "MataKuliah")
public class MataKuliah {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int jumlahSKS;
    
    @ManyToMany(fetch = FetchType.EAGER)     
    @JoinTable(
        name = "dosen_matkul",
        joinColumns = @JoinColumn(name = "dosen_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "matkul_id", 
        referencedColumnName = "id"))
    private List<User> user;
    
    @OneToMany(mappedBy= "mataKuliah")
    private List<Nilai> nilai;
    
}
