package com.lauvsong.carrot.domain;

import lombok.Data;

@Data
public class CsvRow {

    private Long id;

    private int year;
    private int month;
    private int day;

    private Long userId;

    private String bankCode;

    private Long amount;
    private TransactionType type;

    public CsvRow(String[] row) {
        id = Long.parseLong(row[0]);

        year = Integer.parseInt(row[1]);
        month = Integer.parseInt(row[2]);
        day = Integer.parseInt(row[3]);

        userId = Long.parseLong(row[4]);

        bankCode = row[5];

        amount = Long.parseLong(row[6]);
        type = TransactionType.valueOf(row[7]);
    }
}
