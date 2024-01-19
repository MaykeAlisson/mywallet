package com.mywallet.api.services.impl;

import com.mywallet.api.domain.dto.RuleDto;
import com.mywallet.api.domain.entity.Active;
import com.mywallet.api.domain.entity.Wallet;
import com.mywallet.api.domain.enums.WallateCategory;
import com.mywallet.api.domain.model.BrapiTicketModel;
import com.mywallet.api.domain.model.RulesConsolidated;
import com.mywallet.api.domain.model.WalletConsolidated;
import com.mywallet.api.provider.exception.ResourceNotFoundException;
import com.mywallet.api.provider.mappers.WalletMapper;
import com.mywallet.api.repositories.ActiveRepository;
import com.mywallet.api.repositories.WalletRepository;
import com.mywallet.api.services.BrapiService;
import com.mywallet.api.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mywallet.api.provider.authentication.User.getUserContext;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ActiveRepository activeRepository;
    private final BrapiService brapiService;

    @Override
    public WalletConsolidated findWallet() {
        return this.consolidated();
    }

    @Override
    public Wallet create(final RuleDto dto) {
        var idUserRequest = requireNonNull(getUserContext()).getUserId();
        var rule = this.findWalletByUserAndCategory(idUserRequest, dto.category());
        return rule.map(wallet -> this.walletRepository.save(WalletMapper.nvl(wallet, dto)))
                .orElseGet(() -> this.walletRepository.save(WalletMapper.fromToActive(dto, idUserRequest)));
    }

    @Override
    public Set<Wallet> findAll() {
        var idUserRequest = requireNonNull(getUserContext()).getUserId();
        return this.walletRepository.findByUserId(idUserRequest);
    }

    @Override
    public void delete(final Long id) {
        var idUserRequest = requireNonNull(getUserContext()).getUserId();
        var rule = this.findWalletByUserAndId(idUserRequest, id);
        this.walletRepository.delete(rule);
    }

    @Override
    public Set<RulesConsolidated> findRulesConsolidated() {
        var idUserRequest = requireNonNull(getUserContext()).getUserId();
        return null;
    }

    private Optional<Wallet> findWalletByUserAndCategory(final Long userId, final WallateCategory category) {
        return this.walletRepository.findByUserIdAndCategory(userId, category);
    }

    private Wallet findWalletByUserAndId(final Long userId, final Long id) {
        return this.walletRepository.findByUserIdAndId(userId, id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Não foi encontrado regra com o id %s para seu usuário", id)));
    }

    private WalletConsolidated consolidated() {
        var idUserRequest = requireNonNull(getUserContext()).getUserId();
        var actives = this.activeRepository.findByUserId(idUserRequest);
        var tickets = this.findInfoActive(actives);
        var amountWallet = actives.stream().map(
                active -> {
                    var price = BigDecimal.valueOf(tickets.stream().filter(value -> value.symbol().equals(active.getTicket())).findFirst().get().regularMarketPrice());
                    return price.multiply(BigDecimal.valueOf(active.getQuantity()));
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
        var activesConsolidated = this.consolidaActive(actives, tickets, amountWallet);
        return WalletConsolidated
                .builder()
                .actives(activesConsolidated)
                .amount(amountWallet.setScale(2, RoundingMode.HALF_UP))
                .build();

    }

    private Set<BrapiTicketModel.Ticket> findInfoActive(final Set<Active> actives) {
        return actives.stream().map(Active::getTicket).collect(Collectors.toSet()).stream().map(
                active -> this.brapiService.findTicket(active).get().getResults().get(0)
        ).collect(Collectors.toSet());

    }

    private Set<WalletConsolidated.ActiveConsolidated> consolidaActive(final Set<Active> actives, final Set<BrapiTicketModel.Ticket> tickets, final BigDecimal walletAmount) {
        return actives.stream().map(
                active -> {
                    var price = BigDecimal.valueOf(tickets.stream().filter(value -> value.symbol().equals(active.getTicket())).findFirst().get().regularMarketPrice());
                    return WalletConsolidated.toActiveFromConsolidated(active, price, walletAmount);
                }
        ).collect(Collectors.toSet());

    }
}
