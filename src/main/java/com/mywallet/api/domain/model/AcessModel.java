package com.mywallet.api.domain.model;

import lombok.Builder;

@Builder
public record AcessModel(Integer id, String name, String token) {
}
