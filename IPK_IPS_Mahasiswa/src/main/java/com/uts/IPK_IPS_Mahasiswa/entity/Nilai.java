package com.uts.IPK_IPS_Mahasiswa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @Column(nullable = true)
    private float Nilai_Angka;
    @Column(nullable = true)
    private String Nilai_Huruf;
    @Column(nullable = true)
    private float bobot;
    
    @ManyToOne
    @JoinColumn(name= "matkul_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MataKuliah mataKuliah;
    
    @ManyToOne
    @JoinColumn(name= "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "periode_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Periode periode;
}
