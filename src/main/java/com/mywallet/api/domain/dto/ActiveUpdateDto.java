package com.mywallet.api.domain.dto;

import com.mywallet.api.domain.enums.ActiveCategory;
import com.mywallet.api.domain.enums.ActiveCurrency;
import com.mywallet.api.domain.enums.ActiveType;

import java.math.BigDecimal;

public record ActiveUpdateDto(
        ActiveCategory category,
        ActiveType type,
        ActiveCurrency currency,
        Long quantity,
        BigDecimal pvp,
        BigDecimal pl,
        BigDecimal pm,
        BigDecimal maximumPrice
) {
}
