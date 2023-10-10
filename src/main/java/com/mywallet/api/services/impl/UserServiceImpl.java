package com.mywallet.api.services.impl;

import com.mywallet.api.domain.dto.UpdateUserDto;
import com.mywallet.api.domain.dto.UserDto;
import com.mywallet.api.domain.entity.User;
import com.mywallet.api.domain.model.AcessModel;
import com.mywallet.api.provider.exception.BusinessException;
import com.mywallet.api.provider.exception.ResourceNotFoundException;
import com.mywallet.api.provider.mappers.AcessMapper;
import com.mywallet.api.provider.mappers.UserMapper;
import com.mywallet.api.repositories.UserRepository;
import com.mywallet.api.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Log4j2
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper = UserMapper.from();
    private final AcessMapper acessMapper = AcessMapper.from();

    @Override
    public AcessModel create(final UserDto dto) {
        log.info("UserServiceImpl.create: " + dto);
         this.userRepository.findByEmail(dto.email())
                 .ifPresent(user -> {
                     throw new BusinessException(format("User with email %s already registered", user.getEmail()));
                 });
        final User created = this.userRepository.save(userMapper.toEntity(dto));
        return acessMapper.fromAcess(created);
    }

    @Override
    public void update(final Integer id, final UpdateUserDto dto) {
        log.info("UserServiceImpl.update: ");
        var user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Not found user whit id %s", id)));

        this.userRepository.save(userMapper.toUpdated(user, dto));
    }
}
