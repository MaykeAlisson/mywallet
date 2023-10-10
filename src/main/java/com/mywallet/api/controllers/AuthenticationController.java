package com.mywallet.api.controllers;


import com.mywallet.api.domain.dto.AuthenticationDto;
import com.mywallet.api.domain.model.AcessModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auths/login")
@Tag(name = "Authentication")
public interface AuthenticationController {

    @PostMapping
    @Operation(description = "```Generate credentians acess```")
    ResponseEntity<AcessModel> login(AuthenticationDto dto);
}
