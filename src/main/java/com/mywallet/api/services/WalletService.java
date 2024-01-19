package com.mywallet.api.services;

import com.mywallet.api.domain.dto.RuleDto;
import com.mywallet.api.domain.entity.Wallet;
import com.mywallet.api.domain.model.RulesConsolidated;
import com.mywallet.api.domain.model.WalletConsolidated;

import java.util.Set;

public interface WalletService {

    WalletConsolidated findWallet();

    Wallet create(RuleDto dto);

    Set<Wallet> findAll();

    void delete(Long id);

    Set<RulesConsolidated> findRulesConsolidated();
}
