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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_kelas",
            joinColumns = @JoinColumn(name = "kelas_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "id"))
    private List<Kelas> kelas;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    private boolean enabled;

    @ManyToMany(mappedBy = "user")
    private List<MataKuliah> mataKuliah;

    @OneToOne(mappedBy = "user")
    private IPS ips;

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
