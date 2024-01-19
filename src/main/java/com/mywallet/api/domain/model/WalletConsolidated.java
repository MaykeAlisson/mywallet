package com.mywallet.api.domain.model;

import com.mywallet.api.domain.entity.Active;
import com.mywallet.api.domain.enums.ActiveCurrency;
import com.mywallet.api.domain.enums.ActiveCategory;
import com.mywallet.api.domain.enums.ActiveType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNullElse;

@Builder
@Getter
public class WalletConsolidated {

    public Set<ActiveConsolidated> actives;
    public BigDecimal amount;

    public static ActiveConsolidated toActiveFromConsolidated(Active active, BigDecimal price, BigDecimal walletAmount) {
        var amount = price.multiply(BigDecimal.valueOf(active.getQuantity()));
        var percent = amount.multiply(new BigDecimal(100)).divide(walletAmount, RoundingMode.HALF_UP);

        return new WalletConsolidated
                .ActiveConsolidated(
                active.getTicket(),
                active.getCategory(),
                active.getType(),
                active.getCurrency(),
                active.getQuantity(),
                price.setScale(2, RoundingMode.HALF_UP),
                isNull(active.getPvp()) ? BigDecimal.ZERO : active.getPvp().setScale(2, RoundingMode.HALF_UP),
                isNull(active.getPl()) ? BigDecimal.ZERO : active.getPl().setScale(2, RoundingMode.HALF_UP),
                isNull(active.getPm()) ? BigDecimal.ZERO : active.getPm().setScale(2, RoundingMode.HALF_UP),
                isNull(active.getMaximumPrice()) ? BigDecimal.ZERO : active.getMaximumPrice().setScale(2, RoundingMode.HALF_UP),
                amount.setScale(2, RoundingMode.HALF_UP),
                percent.setScale(2, RoundingMode.HALF_UP)
        );

    }

    @Builder
    public record ActiveConsolidated(
            String ticket,
            ActiveCategory category,
            ActiveType type,
            ActiveCurrency currency,
            Long quantity,
            BigDecimal price,
            BigDecimal pvp,
            BigDecimal pl,
            BigDecimal pm,
            BigDecimal maximumPrice,
            BigDecimal amount,
            BigDecimal percentWallet
    ) {
    }

}

