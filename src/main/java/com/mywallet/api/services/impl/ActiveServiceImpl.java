package com.mywallet.api.services.impl;

import com.mywallet.api.domain.dto.ActiveDto;
import com.mywallet.api.domain.dto.ActiveUpdateDto;
import com.mywallet.api.domain.entity.Active;
import com.mywallet.api.domain.model.BrapiTicketModel;
import com.mywallet.api.provider.exception.BusinessException;
import com.mywallet.api.provider.exception.ResourceNotFoundException;
import com.mywallet.api.repositories.ActiveRepository;
import com.mywallet.api.services.ActiveService;
import com.mywallet.api.services.BrapiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.mywallet.api.provider.authentication.User.getUserContext;
import static com.mywallet.api.provider.mappers.ActiveMapper.fromToActive;
import static com.mywallet.api.provider.mappers.ActiveMapper.nvl;
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

    @Override
    public Active update(final Long id, final ActiveUpdateDto dto) {
        var idUserRequest = requireNonNull(getUserContext()).getUserId();
        var active = this.findById(idUserRequest, id);
        final var activeNvl = nvl(active, dto);

        return this.activeRepository.save(activeNvl);
    }

    @Override
    public Set<Active> findAll() {
        var idUserRequest = requireNonNull(getUserContext()).getUserId();
        return this.activeRepository.findByUserId(idUserRequest);
    }

    @Override
    public void delete(final Long id) {
        var idUserRequest = requireNonNull(getUserContext()).getUserId();
        var active = this.findById(idUserRequest, id);
        this.activeRepository.delete(active);
    }

    private Boolean existActive(final String ticket, final Long userId){
        return this.activeRepository.findByTicketAndUserId(ticket, userId).isPresent();
    }

    private BrapiTicketModel checkActive(String ticket) {
        return this.brapiService.findTicket(ticket).orElseThrow(() -> new BusinessException(format("Não foi encontrado ativo com o ticket %s", ticket)));
    }

    private Active findById(final Long userId, final Long id){
        return this.activeRepository.findByUserIdAndId(userId, id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Não foi encontrado ativo com o id %s para o usuario %s", id, userId)));
    }

}
