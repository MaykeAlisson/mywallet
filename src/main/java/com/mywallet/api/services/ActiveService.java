package com.mywallet.api.services;

import com.mywallet.api.domain.dto.ActiveDto;
import com.mywallet.api.domain.dto.ActiveUpdateDto;
import com.mywallet.api.domain.entity.Active;

import java.util.Set;

public interface ActiveService {

    Active create(ActiveDto dto);

    Active update(Long id, ActiveUpdateDto dto);

    Set<Active> findAll();

    void delete(Long id);
}
