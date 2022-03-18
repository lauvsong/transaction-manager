package com.lauvsong.carrot.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lauvsong.carrot.domain.Transaction;
import com.lauvsong.carrot.domain.TransactionType;
import lombok.Data;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;

@Data
@JsonNaming(SnakeCaseStrategy.class)
public class TransactionDto {
    private Long id;

    private int year;
    private int month;
    private int day;

    private Long userId;
    private String bankCode;

    private Long transactionAmount;
    private TransactionType transactionType;

    public TransactionDto(Transaction transaction) {
        id = transaction.getId();

        year = transaction.getCreatedDate().getYear();
        month = transaction.getCreatedDate().getMonthValue();
        day = transaction.getCreatedDate().getDayOfMonth();

        userId = transaction.getUser().getId();
        bankCode = transaction.getBank().getCode();

        transactionAmount = transaction.getTransactionAmount();
        transactionType = transaction.getTransactionType();
    }
}
