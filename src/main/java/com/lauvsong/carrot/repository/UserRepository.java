package com.lauvsong.carrot.repository;

import com.lauvsong.carrot.domain.TransactionType;
import com.lauvsong.carrot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(
            "select distinct u from User u"
                    + " join fetch u.transactions t"
                    + " where (t.createdDate = :date or :date is null)"
                    + " and (t.transactionType = :transactionType or :transactionType is null)"
    )
    List<User> findAllWithTransaction(@Param("date") Optional<LocalDate> date,
                             @Param("transactionType") Optional<TransactionType> transactionType);
}
