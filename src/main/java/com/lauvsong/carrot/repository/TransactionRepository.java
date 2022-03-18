package com.lauvsong.carrot.repository;

import com.lauvsong.carrot.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
