package com.mywallet.api.services;

import com.mywallet.api.ApiApplicationTests;
import com.mywallet.api.domain.dto.ActiveDto;
import com.mywallet.api.domain.entity.Active;
import com.mywallet.api.domain.enums.ActiveCategory;
import com.mywallet.api.domain.enums.ActiveCurrency;
import com.mywallet.api.domain.enums.ActiveType;
import com.mywallet.api.domain.model.BrapiTicketModel;
import com.mywallet.api.provider.authentication.User;
import com.mywallet.api.repositories.ActiveRepository;
import com.mywallet.api.services.impl.ActiveServiceImpl;
import com.mywallet.api.services.impl.BrapiServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ActiveServiceTest extends ApiApplicationTests {

    @InjectMocks
    private ActiveServiceImpl activeService;
    @Mock
    private ActiveRepository activeRepository;
    @Mock
    private BrapiServiceImpl brapiService;

    @Test
    void createWhitSuccess(){
        final var user = new User("", "", List.of(), 1L);
        final var auth =  new UsernamePasswordAuthenticationToken(user, null);
        final var securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        final var dto = new ActiveDto("any_ticket",ActiveCategory.FII, ActiveType.BUY_HOLD, ActiveCurrency.REAL);
        final var active = new Active(1L, "any_name", "any_ticket", ActiveCategory.FII, ActiveType.BUY_HOLD, ActiveCurrency.REAL, 3L, new BigDecimal("3"), new BigDecimal("2"), new BigDecimal("2"), List.of(), List.of(), null, null, null);
        final var brapiTicket = new BrapiTicketModel(List.of(new BrapiTicketModel.Ticket("", "", "", 10.5F)));

        when(securityContext.getAuthentication()).thenReturn(auth);
        when(this.brapiService.findTicket(anyString())).thenReturn(Optional.of(brapiTicket));
        when(this.activeRepository.findByTicketAndUserId(any(), any())).thenReturn(Optional.empty());
        when(this.activeRepository.save(any())).thenReturn(active);

        final var result = this.activeService.create(dto);

        verify(this.activeRepository, times(1)).findByTicketAndUserId("any_ticket", 1L);
        verify(this.brapiService, times(1)).findTicket("any_ticket");
        Assertions.assertNull(result.getUser());
        Assertions.assertNotNull(result.getName());
    }

//    @Test
//    void createErrorNotFoundUserId(){}
//
//    @Test
//    void createErrorNotFoundUserId(){}

}
