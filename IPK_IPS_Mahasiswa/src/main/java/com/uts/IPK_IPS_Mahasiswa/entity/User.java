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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true, unique = true)
    private String NIM;
    @Column(nullable = true, unique = true)
    private String NIP;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "kelas_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Kelas kelasSekarang;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_matkul",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "matkul_id",
                    referencedColumnName = "id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<MataKuliah> mataKuliah;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_periode",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "periode_id",
                    referencedColumnName = "id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Periode> periode;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private List<IPS> ips;
    
    @OneToMany(mappedBy = "user")
    private List<Nilai> nilai;
    
    public String getListAllMatkul() {
        String kata="";
        for (MataKuliah matkul : this.mataKuliah) {
            if (matkul.toString().isEmpty()) {
                matkul = null;
                kata = null;
            }
            else{
                kata += matkul.toString() + " ";
            }
        }
        return kata;
    }
    
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    public User(String name, String email, String password, String NIM) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.NIM = NIM;
    }
    
    public User(String name, String email, String password, String NIM, String NIP) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.NIM = NIM;
        this.NIP = NIP;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
