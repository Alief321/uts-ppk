
package com.uts.IPK_IPS_Mahasiswa.service;

import com.uts.IPK_IPS_Mahasiswa.auth.JwtUtils;
import com.uts.IPK_IPS_Mahasiswa.entity.User;
import com.uts.IPK_IPS_Mahasiswa.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UserActiveService {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserRepository userRepository;
    
    public User getUserActive(){
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization").replace("Bearer", "");
        String email = jwtUtils.getUserNameFromJwtToken(token);
        Optional<User> user = userRepository.findByEmail(email);
        User userReal = user.get();
        
        return userReal;
    }
}
