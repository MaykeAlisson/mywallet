package com.mywallet.api.repositories;

import com.mywallet.api.domain.entity.Active;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActiveRepository extends JpaRepository<Active, Integer> {

    Optional<Active> findByTicketAndUserId(String ticket, Integer userId);
}
