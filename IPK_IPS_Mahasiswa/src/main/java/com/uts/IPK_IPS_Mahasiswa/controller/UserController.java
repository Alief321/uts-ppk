package com.uts.IPK_IPS_Mahasiswa.controller;

import com.uts.IPK_IPS_Mahasiswa.dto.UserDto;
import com.uts.IPK_IPS_Mahasiswa.entity.User;
import com.uts.IPK_IPS_Mahasiswa.payload.request.RequestChangePassword;
import com.uts.IPK_IPS_Mahasiswa.payload.response.MessageResponse;
import com.uts.IPK_IPS_Mahasiswa.payload.response.ProfileResponse;
import com.uts.IPK_IPS_Mahasiswa.service.UserActiveService;
import com.uts.IPK_IPS_Mahasiswa.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {
    @Autowired
    UserService userService;
    
    @Autowired
    UserActiveService userActiveService;

    @PatchMapping("/user/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody RequestChangePassword request) {
        UserDto udto = new UserDto();
        udto.setEmail(request.getEmail());
        udto.setPassword(request.getOldPassword());

        int check = userService.check(udto, request.getNewPassword());

        if (check == 1) {
            return ResponseEntity.ok(new MessageResponse("ubah password berhasil"));
        }
        return ResponseEntity.ok(new MessageResponse("ubah password gagal"));
    }
    
    @GetMapping("/profile")
    public ResponseEntity<?> profile (HttpServletRequest request){
        System.out.println("coba req: "+((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization"));
    
        User user = userActiveService.getUserActive();     
        return ResponseEntity.ok(new ProfileResponse(user.getId(), user.getEmail(), user.getName(), user.getNIM(), user.getNIP()));
    
    }
    
     
    @PatchMapping("/user/editProfile")
    public ResponseEntity<?> editProfile(@RequestBody RequestChangePassword request){
        User user = userActiveService.getUserActive();
        
        
        return null;
    }
}
