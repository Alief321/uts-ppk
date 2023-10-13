/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uts.IPK_IPS_Mahasiswa.service;

import com.uts.IPK_IPS_Mahasiswa.entity.User;
import com.uts.IPK_IPS_Mahasiswa.repository.UserRepository;
import java.util.Optional;
import static org.hibernate.query.sqm.tree.SqmNode.log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserCheckService {
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    public boolean isTruePassword(String email, String oldPassword){
        Optional<User> user = userRepository.findByEmail(email);
        
        if(user!= null){
            log.info("find user");
            boolean check = passwordEncoder.matches(oldPassword, user.get().getPassword());
            return check;
        }
      return false;
    }
}
