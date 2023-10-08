/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uts.IPK_IPS_Mahasiswa.repository;

import com.uts.IPK_IPS_Mahasiswa.entity.IPS;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "ips", path = "ips")
public interface IPSrepository extends PagingAndSortingRepository<IPS, Long>, CrudRepository<IPS, Long>{
    
}
