package com.uts.IPK_IPS_Mahasiswa.controller;

import com.uts.IPK_IPS_Mahasiswa.payload.response.JwtResponse;
import com.uts.IPK_IPS_Mahasiswa.auth.JwtUtils;
import com.uts.IPK_IPS_Mahasiswa.dto.UserDto;
import com.uts.IPK_IPS_Mahasiswa.payload.response.MessageResponse;
import com.uts.IPK_IPS_Mahasiswa.entity.Role;
import com.uts.IPK_IPS_Mahasiswa.entity.User;
import com.uts.IPK_IPS_Mahasiswa.enumeration.Erole;
import com.uts.IPK_IPS_Mahasiswa.payload.request.LoginRequest;
import com.uts.IPK_IPS_Mahasiswa.payload.request.RequestChangePassword;
import com.uts.IPK_IPS_Mahasiswa.payload.request.SignupRequest;
import com.uts.IPK_IPS_Mahasiswa.repository.RoleRepository;
import com.uts.IPK_IPS_Mahasiswa.repository.UserRepository;
import com.uts.IPK_IPS_Mahasiswa.service.UserActiveService;
import com.uts.IPK_IPS_Mahasiswa.service.UserDetailsImpl;
import com.uts.IPK_IPS_Mahasiswa.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    UserActiveService userActiveService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByName(signUpRequest.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getName(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getNim(),
                signUpRequest.getNip());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(Erole.Mahasiswa)
                    .orElseThrow(() -> new RuntimeException("Error: Role Mahasiswa is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "Dosen":
                        Role adminRole = roleRepository.findByName(Erole.Dosen)
                                .orElseThrow(() -> new RuntimeException("Error: Role Dosen is not found."));
                        roles.add(adminRole);

                        break;
                    case "Admin":
                        Role modRole = roleRepository.findByName(Erole.Admin)
                                .orElseThrow(() -> new RuntimeException("Error: Role Admin is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(Erole.Mahasiswa)
                                .orElseThrow(() -> new RuntimeException("Error: Role mdsaid is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }    
   @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        try {
            // Perform logout operations here
            request.getSession().invalidate();
            return ResponseEntity.ok("Logged out successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed");
        }
    }
 
}
