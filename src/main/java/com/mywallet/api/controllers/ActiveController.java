package com.mywallet.api.controllers;

import com.mywallet.api.domain.dto.ActiveDto;
import com.mywallet.api.domain.dto.ActiveUpdateDto;
import com.mywallet.api.domain.entity.Active;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@RequestMapping("/actives")
@Tag(name = "Active")
public interface ActiveController {

    @PostMapping
    @Operation(description = "```Create new Active```")
    ResponseEntity<Active> create(@RequestBody @Valid ActiveDto dto);

    @PutMapping(value = "/{id}")
    @Operation(description = "```Update Active```")
    ResponseEntity<Active> update(@PathVariable Long id, @RequestBody ActiveUpdateDto dto);

    @GetMapping
    @Operation(description = "```Find All Active```")
    ResponseEntity<Set<Active>> findAll();

    @DeleteMapping(value = "/{id}")
    @Operation(description = "```Delete Active```")
    ResponseEntity<Void> delete(@PathVariable Long id);
}

