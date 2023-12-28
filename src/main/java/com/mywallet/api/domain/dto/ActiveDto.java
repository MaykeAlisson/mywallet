package com.mywallet.api.domain.dto;

import com.mywallet.api.domain.enums.ActiveCategory;
import com.mywallet.api.domain.enums.ActiveCurrency;
import com.mywallet.api.domain.enums.ActiveType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record ActiveDto(

        @NotBlank
        String ticket,

        @NotNull
        ActiveCategory category,

        @NotNull
        ActiveType type,

        @NotNull
        ActiveCurrency currency
) implements Serializable {
}
