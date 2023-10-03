package com.mywallet.api.services;

import com.mywallet.api.domain.dto.UserDTO;
import com.mywallet.api.domain.model.AcessModel;

public interface UserService {

    AcessModel create(UserDTO dto);
}
