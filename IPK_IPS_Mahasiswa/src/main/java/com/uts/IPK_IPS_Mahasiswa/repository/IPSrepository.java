package com.uts.IPK_IPS_Mahasiswa.repository;

import com.uts.IPK_IPS_Mahasiswa.entity.IPS;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "ips", path = "ips")
public interface IPSrepository extends PagingAndSortingRepository<IPS, Long>, CrudRepository<IPS, Long>{
    List<IPS> findByUserId(Long userId);
}
