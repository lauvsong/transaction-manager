package com.lauvsong.carrot.service;

import com.lauvsong.carrot.domain.Bank;
import com.lauvsong.carrot.domain.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BankServiceTest {

    @Autowired private BankService bankService;
    @Autowired private TransactionService transactionService;

    @BeforeEach
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    @BeforeEach
    public void CSV_저장() throws Exception {
        final String FILE_PATH = getClass().getClassLoader().getResource("transaction.csv").getFile();
        final String FILE_NAME = "transaction.csv";
        FileInputStream fileInputStream = new FileInputStream(new File(FILE_PATH));

        MockMultipartFile file = new MockMultipartFile("files", FILE_NAME, "text/csv", fileInputStream);

        transactionService.save(file);
    }

    @Test
    public void 은행별_조회() throws Exception {
        //given
        String stringDate = "2021-01-01";
        LocalDate date = LocalDate.parse(stringDate, DateTimeFormatter.ISO_DATE);

        String bankCode = "090";

        TransactionType type = TransactionType.WITHDRAW;

        //when
        List<Bank> banks = bankService.findTransactions(of(date), of(bankCode), of(type));

        //then
        assertThat(banks.size()).isEqualTo(1);
        assertThat(banks.get(0).getTransactions().size()).isEqualTo(4);
    }

    @Test
    public void 은행별_조회_NULL파라미터() throws Exception {
        //given
        Optional<LocalDate> date = empty();
        Optional<String> bankCode = empty();
        Optional<TransactionType> type = empty();

        //when
        List<Bank> banks = bankService.findTransactions(date, bankCode, type);

        //then
        assertThat(banks.size()).isEqualTo(5);

        Map<String, Integer> bankMap = new HashMap<>() {{
            put("004", 1473);
            put("011", 1452);
            put("020", 1471);
            put("088", 1491);
            put("090", 1442);
        }};

        for (Bank bank : banks) {
            String code = bank.getCode();
            Integer size = bankMap.get(code);

            assertThat(size).isEqualTo(bank.getTransactions().size());
        }
    }
}
