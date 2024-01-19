package com.mywallet.api.repositories;

import com.mywallet.api.domain.entity.Wallet;
import com.mywallet.api.domain.enums.WallateCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUserIdAndCategory(Long userId, WallateCategory category);

    Set<Wallet> findByUserId(Long userId);

    Optional<Wallet> findByUserIdAndId(Long userId, Long id);
}
