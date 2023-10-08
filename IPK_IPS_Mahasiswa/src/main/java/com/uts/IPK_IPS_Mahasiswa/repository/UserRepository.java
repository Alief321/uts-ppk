
package com.uts.IPK_IPS_Mahasiswa.repository;

import com.uts.IPK_IPS_Mahasiswa.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long>, CrudRepository<User, Long>{
    Optional<User> findByName(String name);
    
    User findByEmail(String email);

    Boolean existsByName(String name);

    Boolean existsByEmail(String email);
}
