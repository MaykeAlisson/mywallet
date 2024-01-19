package com.mywallet.api.provider.mappers;

import com.mywallet.api.domain.dto.ActiveDto;
import com.mywallet.api.domain.dto.ActiveUpdateDto;
import com.mywallet.api.domain.entity.Active;
import com.mywallet.api.domain.entity.User;
import com.mywallet.api.domain.model.BrapiTicketModel;
import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;

public class ActiveMapper {

   public static Active fromToActive(final ActiveDto dto, final BrapiTicketModel.Ticket brapiTicket, final Long userId){
       return Active.builder()
               .name(brapiTicket.longName())
               .ticket(dto.ticket())
               .category(dto.category())
               .type(dto.type())
               .currency(dto.currency())
               .user(User.builder().id(userId).build())
               .build();
   }

   public static Active nvl(Active active, ActiveUpdateDto dto){
       if(!Objects.isNull(dto.category())) active.setCategory(dto.category());
       if(!Objects.isNull(dto.type())) active.setType(dto.type());
       if(!Objects.isNull(dto.currency())) active.setCurrency(dto.currency());
       if(!Objects.isNull(dto.quantity())) active.setQuantity(dto.quantity());
       if(!Objects.isNull(dto.pl())) active.setPl(dto.pl());
       if(!Objects.isNull(dto.pm())) active.setPm(dto.pm());
       if(!Objects.isNull(dto.pvp())) active.setPvp(dto.pvp());
       if(!Objects.isNull(dto.maximumPrice())) active.setMaximumPrice(dto.maximumPrice());
       return active;
   }
}
