package com.uts.IPK_IPS_Mahasiswa.repository;

import com.uts.IPK_IPS_Mahasiswa.entity.Role;
import com.uts.IPK_IPS_Mahasiswa.enumeration.Erole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(Erole name);
}
