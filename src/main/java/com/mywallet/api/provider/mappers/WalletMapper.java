package com.mywallet.api.provider.mappers;

import com.mywallet.api.domain.dto.ActiveUpdateDto;
import com.mywallet.api.domain.dto.RuleDto;
import com.mywallet.api.domain.entity.Active;
import com.mywallet.api.domain.entity.User;
import com.mywallet.api.domain.entity.Wallet;

import java.util.Objects;

public class WalletMapper {

    public static Wallet fromToActive(final RuleDto dto, final Long userId){
        return Wallet.builder()
                .category(dto.category())
                .percent(dto.percent())
                .quantity(dto.quantity())
                .user(User.builder().id(userId).build())
                .build();
    }

    public static Wallet nvl(Wallet wallet, RuleDto dto){
        if(!Objects.isNull(dto.category())) wallet.setCategory(dto.category());
        if(!Objects.isNull(dto.percent())) wallet.setPercent(dto.percent());
        if(!Objects.isNull(dto.quantity())) wallet.setQuantity(dto.quantity());
        return wallet;
    }
}
