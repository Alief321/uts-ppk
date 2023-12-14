package com.uts.IPK_IPS_Mahasiswa.entity;

import com.uts.IPK_IPS_Mahasiswa.enumeration.KategoriMatkul;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
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
@Table(name = "MataKuliah")
public class MataKuliah {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = true)
    private String deskripsi;
    @Column(nullable = false)
    private int jumlahSKS;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KategoriMatkul kategori;

    @ManyToMany(mappedBy = "mataKuliah")
    private List<User> user;

    @OneToMany(mappedBy = "mataKuliah")
    private List<Nilai> nilai;

    @ManyToOne
    @JoinColumn(name = "periode_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Periode periode;

    public String toString() {
        return name.toString();
    }
}
