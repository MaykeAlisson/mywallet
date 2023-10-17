package com.mywallet.api.services;

import com.mywallet.api.ApiApplicationTests;
import com.mywallet.api.domain.dto.UpdateUserDto;
import com.mywallet.api.domain.dto.UserDto;
import com.mywallet.api.domain.entity.User;
import com.mywallet.api.provider.exception.BusinessException;
import com.mywallet.api.provider.exception.ResourceNotFoundException;
import com.mywallet.api.repositories.UserRepository;
import com.mywallet.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest extends ApiApplicationTests {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void createWhitSuccess(){
        final var userDto = new UserDto("any_name", "any@gmail.com", "123456");
        final var user = new User(1, "any_name", "", "", null,null);
        when(this.userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(this.userRepository.save(any())).thenReturn(user);

        final var result = this.userService.create(userDto);

        verify(this.userRepository, times(1)).findByEmail("any@gmail.com");
        Assertions.assertEquals(result.name(), userDto.name());
        Assertions.assertFalse(result.token().isEmpty());
    }

    @Test
    void createWhitEmailRegistered(){
        final var userDto = new UserDto("any_name", "any@gmail.com", "123456");
        final var user = new User(1, "any_name", "any@gmail.com", "", null,null);
        when(this.userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        Assertions.assertThrows(BusinessException.class, () -> this.userService.create(userDto)) ;
        verify(this.userRepository, times(0)).save(any());
    }

    @Test
    void updateWhitSuccess(){
        final var userUpdate = new UpdateUserDto("New Name");
        final var user = new User(1, "any_name", "any@gmail.com", "", null,null);
        final var userUpdeted = new User(1, "New Name", "any@gmail.com", "", null,null);
        when(this.userRepository.findById(any())).thenReturn(Optional.of(user));

        this.userService.update(1,  userUpdate);

        verify(this.userRepository, times(1)).save(userUpdeted);
    }

    @Test
    void updateNotFoundUser(){
        final var userUpdate = new UpdateUserDto("New Name");
        when(this.userRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> this.userService.update(1,  userUpdate));

        verify(this.userRepository, times(0)).save(any());
    }
}
