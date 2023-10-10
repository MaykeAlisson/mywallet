package com.mywallet.api.controllers.impl;

import com.mywallet.api.controllers.UserController;
import com.mywallet.api.domain.dto.UpdateUserDto;
import com.mywallet.api.domain.dto.UserDto;
import com.mywallet.api.domain.model.AcessModel;
import com.mywallet.api.provider.authentication.PreAuthorizeProvider;
import com.mywallet.api.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@AllArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserServiceImpl userService;

    @Override
    public ResponseEntity<AcessModel> create(final UserDto dto){
        final var access = this.userService.create(dto);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(access.id()).toUri();
        return ResponseEntity.created(uri).body(access);
    }


    @Override
    @PreAuthorize(PreAuthorizeProvider.IS_AUTHENTICATED)
    public ResponseEntity<Void> update(final Integer id, final UpdateUserDto dto){
        this.userService.update(id, dto);
        return ResponseEntity.ok().build();
    }
}
