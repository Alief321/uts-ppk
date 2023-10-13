package com.uts.IPK_IPS_Mahasiswa.service;

import com.uts.IPK_IPS_Mahasiswa.dto.UserDto;


public interface UserService {

    public UserDto createUser(UserDto user);

    public int check(UserDto user, String newPassword);
//    public UserDto getUserByEmail(String email);
}
