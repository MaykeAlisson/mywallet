package com.mywallet.api.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record UserDTO(
    @NotBlank
    String name,
    @NotBlank
    @Email
    String email,
    @NotBlank
    @Size(min = 6, message = "{validation.name.size.too_short}")
    String password
) implements Serializable {

}
