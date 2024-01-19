package com.mywallet.api.domain.dto;

import com.mywallet.api.domain.enums.WallateCategory;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record RuleDto(
        @NotNull
        WallateCategory category,
        Long quantity,
        @NotNull
        Integer percent
) implements Serializable {
}
