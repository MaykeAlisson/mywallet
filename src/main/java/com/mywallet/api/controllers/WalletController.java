package com.mywallet.api.controllers;

import com.mywallet.api.domain.dto.RuleDto;
import com.mywallet.api.domain.entity.Wallet;
import com.mywallet.api.domain.model.RulesConsolidated;
import com.mywallet.api.domain.model.WalletConsolidated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@RequestMapping("/wallets")
@Tag(name = "Wallet")
public interface WalletController {

    @GetMapping
    @Operation(description = "```Find wallet consolidated```")
    ResponseEntity<WalletConsolidated> findWallet();

    @PostMapping("/rules")
    @Operation(description = "```Create or Update rule```")
    ResponseEntity<Wallet> create(@RequestBody @Valid RuleDto dto);

    @GetMapping("/rules")
    @Operation(description = "```Find All rule```")
    ResponseEntity<Set<Wallet>> findAll();

    @DeleteMapping("/rules/{id}")
    @Operation(description = "```Delete rule```")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @GetMapping("/rules/consolidated")
    @Operation(description = "```Find All rule consolidated```")
    ResponseEntity<Set<RulesConsolidated>> findRulesConsolidated();
}
