package com.mywallet.api.controllers.impl;

import com.mywallet.api.domain.dto.UserDTO;
import com.mywallet.api.domain.model.AcessModel;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/users")
//@Tag(name = "Authentication")
public interface UserController {
    @PostMapping
//    @Operation(description = "```Autenticação de usuário na API SSO ou Nav```")
    ResponseEntity<AcessModel> create(@RequestBody  @Valid  UserDTO dto);

}
