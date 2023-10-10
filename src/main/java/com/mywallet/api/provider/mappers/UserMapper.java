package com.mywallet.api.provider.mappers;

import com.mywallet.api.domain.dto.UpdateUserDto;
import com.mywallet.api.domain.dto.UserDto;
import com.mywallet.api.domain.entity.User;

import static com.mywallet.api.provider.Encryption.encriptar;

public class UserMapper {

    public static UserMapper from(){
        return new UserMapper();
    }

    public User toEntity(UserDto source){
        return User.builder()
                .name(source.name())
                .email(source.email())
                .password(encriptar(source.password()))
                .build();
    }

    public User toUpdated(User user, UpdateUserDto source){
        if(!source.name().isEmpty()) user.setName(source.name());
        return user;
    }

}
