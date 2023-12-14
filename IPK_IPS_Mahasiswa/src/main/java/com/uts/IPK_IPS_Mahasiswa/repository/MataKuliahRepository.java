package com.uts.IPK_IPS_Mahasiswa.repository;

import com.uts.IPK_IPS_Mahasiswa.entity.MataKuliah;
import com.uts.IPK_IPS_Mahasiswa.entity.Periode;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "matkul", path = "matkul")
public interface MataKuliahRepository extends PagingAndSortingRepository<MataKuliah, Long>, CrudRepository<MataKuliah, Long> {

    Optional<MataKuliah> findByName(String name);

    List<MataKuliah> findByPeriode(Periode periode);

    List<MataKuliah> findByNameContains(String name);

    List<MataKuliah> findByNameContainingAndPeriode(String name, Periode periode);
}
