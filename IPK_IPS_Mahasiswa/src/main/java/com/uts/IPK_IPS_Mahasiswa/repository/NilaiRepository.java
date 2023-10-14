
package com.uts.IPK_IPS_Mahasiswa.repository;

import com.uts.IPK_IPS_Mahasiswa.entity.Nilai;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "nilai", path = "nilai")
public interface NilaiRepository extends PagingAndSortingRepository<Nilai, Long>, CrudRepository<Nilai, Long>{
    List<Nilai> findByUser_Id(Long userId);
}
