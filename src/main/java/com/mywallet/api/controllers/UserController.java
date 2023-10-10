package com.mywallet.api.controllers;

import com.mywallet.api.domain.dto.UpdateUserDto;
import com.mywallet.api.domain.dto.UserDto;
import com.mywallet.api.domain.model.AcessModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
@Tag(name = "User")
public interface UserController {
    @PostMapping
    @Operation(description = "```Create new User```")
    ResponseEntity<AcessModel> create(@RequestBody  @Valid UserDto dto);

    @PutMapping(value = "/{id}")
    @Operation(description = "```Update User```")
    ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody UpdateUserDto dto);

}
