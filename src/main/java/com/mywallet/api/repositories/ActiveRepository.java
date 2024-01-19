package com.mywallet.api.repositories;

import com.mywallet.api.domain.entity.Active;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ActiveRepository extends JpaRepository<Active, Integer> {

    Optional<Active> findByTicketAndUserId(String ticket, Long userId);

    Optional<Active> findByUserIdAndId(Long userId, Long Id);

    Set<Active> findByUserId(Long userId);
}
