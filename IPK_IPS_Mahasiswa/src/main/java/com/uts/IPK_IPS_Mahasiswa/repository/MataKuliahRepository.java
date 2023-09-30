
package com.uts.IPK_IPS_Mahasiswa.repository;

import com.uts.IPK_IPS_Mahasiswa.entity.MataKuliah;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "matkul", path = "matkul")
public interface MataKuliahRepository extends PagingAndSortingRepository<MataKuliah, Long>, CrudRepository<MataKuliah, Long>{
    
}
