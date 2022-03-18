package com.lauvsong.carrot.repository;

import com.lauvsong.carrot.domain.Bank;
import com.lauvsong.carrot.domain.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, String> {

    @Query(
            "select distinct b from Bank b"
                    + " join fetch b.transactions t"
                    + " where (t.createdDate = :date or :date is null)"
                    + " and (t.transactionType = :transactionType or :transactionType is null)"
                    + " and (t.bank = :bank or :bank is null)"
    )
    List<Bank> findAllWithTransaction(@Param("date") Optional<LocalDate> date,
                             @Param("bank") Optional<Bank> bank,
                             @Param("transactionType") Optional<TransactionType> transactionType);
}
