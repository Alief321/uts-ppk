
package com.uts.IPK_IPS_Mahasiswa.repository;

import com.uts.IPK_IPS_Mahasiswa.entity.Kelas;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "kelas", path = "kelas")
public interface KelasRepository extends PagingAndSortingRepository<Kelas, Long>, CrudRepository<Kelas, Long>{
    Optional<Kelas> findByNamaKelas(String nama_kelas);
}
