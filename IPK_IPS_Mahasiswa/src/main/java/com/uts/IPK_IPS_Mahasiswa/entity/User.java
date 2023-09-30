package com.uts.IPK_IPS_Mahasiswa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Role role;
    
    @ManyToMany(mappedBy = "user")
    private List<MataKuliah> mataKuliah;
    
    @OneToOne(mappedBy= "user")
    private IPS ips;
    
}
