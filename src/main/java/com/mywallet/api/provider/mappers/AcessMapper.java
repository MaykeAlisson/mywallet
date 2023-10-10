package com.mywallet.api.provider.mappers;


import com.mywallet.api.domain.entity.User;
import com.mywallet.api.domain.model.AcessModel;
import com.mywallet.api.provider.jwt.JwtProvider;

import java.io.Serializable;

public class AcessMapper implements Serializable {

    public static AcessMapper from(){
        return new AcessMapper();
    }

    public AcessModel fromAcess(User source){
        final String token = JwtProvider.gerar(source.getId(), 1L)
                .orElseThrow(() -> new RuntimeException("Nao foi possivel gerar token"));
        return AcessModel.builder()
                .id(source.getId())
                .name(source.getName())
                .token(token)
                .build();
    }
}
