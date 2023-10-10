package com.mywallet.api.domain.dto;

import java.io.Serializable;

public record UpdateUserDto(
        String name
) implements Serializable {
}
