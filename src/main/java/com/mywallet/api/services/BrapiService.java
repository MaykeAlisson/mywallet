package com.mywallet.api.services;

import com.mywallet.api.domain.model.BrapiTicketModel;

import java.util.Optional;

public interface BrapiService {

    Optional<BrapiTicketModel> findTicket(String ticket);
}
