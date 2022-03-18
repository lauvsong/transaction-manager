package com.lauvsong.carrot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "transactions")
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    private Long id;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bank_code")
    private Bank bank;

    @NotNull
    private Long transactionAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private TransactionType transactionType;
}
