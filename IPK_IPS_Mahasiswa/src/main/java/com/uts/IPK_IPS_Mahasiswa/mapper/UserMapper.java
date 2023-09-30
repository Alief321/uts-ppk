package com.uts.IPK_IPS_Mahasiswa.mapper;

import com.uts.IPK_IPS_Mahasiswa.dto.UserDto;
import com.uts.IPK_IPS_Mahasiswa.entity.User;


public class UserMapper {

    public static User mapToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .NIM(userDto.getNIM())
                .NIP(userDto.getNIP())
                .build();
    }

    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .email(user.getEmail())
                .NIM(user.getNIM())
                .NIP(user.getNIP())
                .build();
    }
}
