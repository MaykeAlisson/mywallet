package com.mywallet.api.controllers.impl;

import com.mywallet.api.controllers.AuthenticationController;
import com.mywallet.api.domain.dto.AuthenticationDto;
import com.mywallet.api.domain.model.AcessModel;
import com.mywallet.api.services.impl.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {

    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<AcessModel> login(@RequestBody @Valid final AuthenticationDto dto){
        var acess = authenticationService.login(dto);
        return ResponseEntity.ok(acess);
    }
}
