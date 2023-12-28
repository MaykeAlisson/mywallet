package com.mywallet.api.services.impl;

import com.mywallet.api.domain.dto.ActiveDto;
import com.mywallet.api.domain.entity.Active;
import com.mywallet.api.domain.model.BrapiTicketModel;
import com.mywallet.api.provider.exception.BusinessException;
import com.mywallet.api.repositories.ActiveRepository;
import com.mywallet.api.services.ActiveService;
import com.mywallet.api.services.BrapiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mywallet.api.provider.authentication.User.getUserContext;
import static com.mywallet.api.provider.mappers.ActiveMapper.fromToActive;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class ActiveServiceImpl implements ActiveService {

    private final BrapiService brapiService;
    private final ActiveRepository activeRepository;

    @Override
    public Active create(ActiveDto dto) {

        var idUserRequest = requireNonNull(getUserContext()).getUserId();
        if(this.existActive(dto.ticket(), idUserRequest))
            throw new BusinessException("Ativo já cadastrado!");

        final var ticket = this.checkActive(dto.ticket()).getResults().stream().findFirst().orElseThrow();
        final var active = fromToActive(dto, ticket, idUserRequest);

        return this.activeRepository.save(active);
    }

    private Boolean existActive(final String ticket, final Long userId){
        return this.activeRepository.findByTicketAndUserId(ticket, userId).isPresent();
    }

    private BrapiTicketModel checkActive(String ticket) {
        return this.brapiService.findTicket(ticket).orElseThrow(() -> new BusinessException(format("Não foi encontrado ativo com o ticket %s", ticket)));
    }

}
