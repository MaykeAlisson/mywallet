package com.mywallet.api.domain.model;

import lombok.Builder;

@Builder
public record AcessModel(Long id, String name, String token) {
}
