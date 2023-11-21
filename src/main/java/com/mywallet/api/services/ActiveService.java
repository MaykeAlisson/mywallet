package com.mywallet.api.services;

import com.mywallet.api.domain.dto.ActiveDto;
import com.mywallet.api.domain.entity.Active;

public interface ActiveService {

    Active create(ActiveDto dto);
}
