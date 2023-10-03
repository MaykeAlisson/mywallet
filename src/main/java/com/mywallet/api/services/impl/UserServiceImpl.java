package com.mywallet.api.services.impl;

import com.mywallet.api.domain.dto.UserDTO;
import com.mywallet.api.domain.entity.User;
import com.mywallet.api.domain.model.AcessModel;
import com.mywallet.api.provider.exception.BusinessException;
import com.mywallet.api.repositories.UserRepository;
import com.mywallet.api.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static java.lang.String.format;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public AcessModel create(final UserDTO dto) {
         this.userRepository.findByEmail(dto.email())
                 .ifPresent(user -> {
                     throw new BusinessException(format("User with email %s already registered", user.getEmail()));
                 });
        final User created = this.userRepository.save(new User(dto));
        return new AcessModel(created.getId(), created.getName(), "token");
    }
}
