package com.mywallet.api.controllers.impl;

import com.mywallet.api.controllers.WalletController;
import com.mywallet.api.domain.dto.RuleDto;
import com.mywallet.api.domain.entity.Wallet;
import com.mywallet.api.domain.model.RulesConsolidated;
import com.mywallet.api.domain.model.WalletConsolidated;
import com.mywallet.api.provider.authentication.PreAuthorizeProvider;
import com.mywallet.api.services.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@AllArgsConstructor
public class WalletControllerImpl implements WalletController {

    private final WalletService walletService;
    @Override
    @PreAuthorize(PreAuthorizeProvider.IS_AUTHENTICATED)
    public ResponseEntity<WalletConsolidated> findWallet() {
        var wallet = this.walletService.findWallet();
        return ResponseEntity.ok().body(wallet);
    }

    @Override
    @PreAuthorize(PreAuthorizeProvider.IS_AUTHENTICATED)
    public ResponseEntity<Wallet> create(final RuleDto dto) {
        var rule = this.walletService.create(dto);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(rule.getId()).toUri();
        return ResponseEntity.created(uri).body(rule);
    }

    @Override
    @PreAuthorize(PreAuthorizeProvider.IS_AUTHENTICATED)
    public ResponseEntity<Set<Wallet>> findAll() {
        var rules = this.walletService.findAll();
        return ResponseEntity.ok().body(rules);
    }

    @Override
    @PreAuthorize(PreAuthorizeProvider.IS_AUTHENTICATED)
    public ResponseEntity<Void> delete(final Long id) {
        this.walletService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize(PreAuthorizeProvider.IS_AUTHENTICATED)
    public ResponseEntity<Set<RulesConsolidated>> findRulesConsolidated() {
        var rules = this.walletService.findRulesConsolidated();
        return ResponseEntity.ok().body(rules);
    }
}
