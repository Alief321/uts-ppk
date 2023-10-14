package com.uts.IPK_IPS_Mahasiswa.controller;

import com.uts.IPK_IPS_Mahasiswa.dto.UserDto;
import com.uts.IPK_IPS_Mahasiswa.entity.Kelas;
import com.uts.IPK_IPS_Mahasiswa.entity.MataKuliah;
import com.uts.IPK_IPS_Mahasiswa.entity.User;
import com.uts.IPK_IPS_Mahasiswa.enumeration.Erole;
import com.uts.IPK_IPS_Mahasiswa.payload.request.RequestChangePassword;
import com.uts.IPK_IPS_Mahasiswa.payload.request.RequestEditProfil;
import com.uts.IPK_IPS_Mahasiswa.payload.request.SetKelasMahasiswa;
import com.uts.IPK_IPS_Mahasiswa.payload.request.SetMatkulRequest;
import com.uts.IPK_IPS_Mahasiswa.payload.response.MessageResponse;
import com.uts.IPK_IPS_Mahasiswa.payload.response.ProfileResponse;
import com.uts.IPK_IPS_Mahasiswa.repository.KelasRepository;
import com.uts.IPK_IPS_Mahasiswa.repository.MataKuliahRepository;
import com.uts.IPK_IPS_Mahasiswa.repository.UserRepository;
import com.uts.IPK_IPS_Mahasiswa.service.UserActiveService;
import com.uts.IPK_IPS_Mahasiswa.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    KelasRepository kelasRepository;
    
    @Autowired
    MataKuliahRepository mataKuliahRepository;

    @PatchMapping("/user/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody RequestChangePassword request) {
        String userActiveEmail = userActiveService.getUserActive().getEmail();
        System.out.println("email "+ request.getEmail() + " activ "+ userActiveEmail);
        if (!request.getEmail().equals(userActiveEmail)) {
            return ResponseEntity.ok(new MessageResponse("email dan user yang sedang login tidak cocok"));
        }
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
        String namaKelas = "Tidak terdaftar dalam kelas"; 
        if(user.getKelasSekarang()!= null){
            namaKelas = user.getKelasSekarang().getNamaKelas();
        }
        return ResponseEntity.ok(new ProfileResponse(user.getId(), user.getName(), user.getEmail(), user.getNIM(), user.getNIP(),namaKelas, user.getListAllMatkul()));
    
    }
    
     
    @PatchMapping("/profile")
    public ResponseEntity<?> editProfile(@RequestBody RequestEditProfil request){
        User user = userActiveService.getUserActive();
        
        user.setName(request.getName());
        
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("ubah nama profil berhasil"));
    }
    
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteAcount(){
        User user = userActiveService.getUserActive();
        userRepository.delete(user);
        
        return ResponseEntity.ok(new MessageResponse("Akun berhasil dihapus"));
    }
    
    @PatchMapping("/user/setKelas")
    public ResponseEntity<?> setKelas(@RequestBody SetKelasMahasiswa request){
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        User userReal = user.get();
        
        System.out.println("role user = " + userReal.getRoles().toString()+ " " + "["+Erole.Mahasiswa.toString()+"]");
        
        if(!userReal.getRoles().toString().equals("["+Erole.Mahasiswa.toString()+"]") ) {
            return ResponseEntity.ok(new MessageResponse("Role bukan mahasiswa"));
        }
        String strKelas = request.getNamaKelas();
        Optional<Kelas> kelas = kelasRepository.findByNamaKelas(strKelas);
        
        userReal.setKelasSekarang(kelas.get());
        userRepository.save(userReal);
        return ResponseEntity.ok(new MessageResponse("set kelas berhasil"));
    }
    
    @PatchMapping("/user/setMatkul")
    public ResponseEntity<?> setKelas(@RequestBody SetMatkulRequest request){
        User user = userActiveService.getUserActive();
        
        if(!user.getRoles().toString().equals("["+Erole.Admin.toString()+"]") ) {
            return ResponseEntity.ok(new MessageResponse("Anda tidak berhak mengedit matkul dan dosen pengampu"));
        }
        
        Optional<User> d = userRepository.findById(request.getIdDosen());
        User dosen = d.get();
        if(!dosen.getRoles().toString().equals("["+Erole.Dosen.toString()+"]") ) {
            return ResponseEntity.ok(new MessageResponse("User bukan dosen"));
        }
        
        List<Long> idMatkul = request.getMataKuliah();
        List<MataKuliah> matkul = new ArrayList<>();
        
        if(idMatkul != null){
            idMatkul.forEach(mk -> {
                if(mataKuliahRepository.findById(mk) != null){
                    matkul.add(mataKuliahRepository.findById(mk).get());
                }
            }        
            );
        }
        
        dosen.setMataKuliah(matkul);
        userRepository.save(dosen);
        
         return ResponseEntity.ok(new MessageResponse("set matkul berhasil"));
    }
}
