package com.uts.IPK_IPS_Mahasiswa.service;



import com.uts.IPK_IPS_Mahasiswa.entity.User;
import com.uts.IPK_IPS_Mahasiswa.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    User user = userRepository.findByName(name)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + name));

    return UserDetailsImpl.build(user);
  }
    
}