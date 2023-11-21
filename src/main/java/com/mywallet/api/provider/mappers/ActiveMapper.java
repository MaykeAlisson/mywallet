package com.mywallet.api.provider.mappers;

import com.mywallet.api.domain.dto.ActiveDto;
import com.mywallet.api.domain.entity.Active;
import com.mywallet.api.domain.entity.User;
import com.mywallet.api.domain.model.BrapiTicketModel;

public class ActiveMapper {

   public static Active fromToActive(final ActiveDto dto, final BrapiTicketModel.Ticket brapiTicket, final Integer userId){
       return Active.builder()
               .name(brapiTicket.longName())
               .ticket(dto.ticket())
               .category(dto.category())
               .type(dto.type())
               .currency(dto.currency())
               .quantity(dto.quantity())
               .user(User.builder().id(userId).build())
               .build();
   }
}
