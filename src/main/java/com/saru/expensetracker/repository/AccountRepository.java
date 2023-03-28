package com.saru.expensetracker.repository;

import com.saru.expensetracker.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<List<Account>> findByUserId(Long userId);
    Optional<Account> findByUserIdAndId(Long userId, Long accountId);
}
