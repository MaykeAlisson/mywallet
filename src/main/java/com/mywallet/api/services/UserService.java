package com.mywallet.api.services;

import com.mywallet.api.domain.dto.UpdateUserDto;
import com.mywallet.api.domain.dto.UserDto;
import com.mywallet.api.domain.model.AcessModel;

public interface UserService {

    AcessModel create(UserDto dto);

    void update(Integer id, UpdateUserDto dto);
}
