package com.uts.IPK_IPS_Mahasiswa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "Nilai")
public class Nilai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private float Nilai_UTS;
    @Column(nullable = false)
    private float Nilai_UAS;
    @Column(nullable = false)
    private float Nilai_Tugas;
    @Column(nullable = true)
    private float Nilai_Praktikum;
    
    @OneToOne
    @JoinColumn(name= "matkul_id")
    private MataKuliah mataKuliah;
}
