package com.mywallet.api.controllers;

import com.mywallet.api.domain.dto.ActiveDto;
import com.mywallet.api.domain.entity.Active;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/actives")
@Tag(name = "Active")
public interface ActiveController {

    @PostMapping
    @Operation(description = "```Create new Active```")
    ResponseEntity<Active> create(@RequestBody @Valid ActiveDto dto);
}

