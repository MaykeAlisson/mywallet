package com.mywallet.api.services.impl;

import com.mywallet.api.domain.dto.AuthenticationDto;
import com.mywallet.api.domain.entity.User;
import com.mywallet.api.domain.model.AcessModel;
import com.mywallet.api.provider.exception.BusinessException;
import com.mywallet.api.provider.exception.ResourceNotFoundException;
import com.mywallet.api.provider.jwt.JwtProvider;
import com.mywallet.api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mywallet.api.provider.Encryption.check;
import static java.lang.String.format;

@Service
@AllArgsConstructor
public class AuthenticationService  {

    private final UserRepository userRepository;


    public AcessModel login(final AuthenticationDto dto){

       final var user =  this.userRepository.findByEmail(dto.email()).orElseThrow(() -> new ResourceNotFoundException(format("Not found user whit email %s", dto.email())));

        if(!check(dto.password(), user.getPassword()))
            throw  new BusinessException("Invalid password");

        final String token = JwtProvider.gerar(user.getId(), 1L)
                .orElseThrow(() -> new RuntimeException("Nao foi possivel gerar token"));
        return AcessModel.builder()
                .id(user.getId())
                .name(user.getName())
                .token(token)
                .build();

    }
}
