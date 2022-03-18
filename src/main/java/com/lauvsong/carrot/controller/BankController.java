package com.lauvsong.carrot.controller;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lauvsong.carrot.domain.Bank;
import com.lauvsong.carrot.domain.TransactionType;
import com.lauvsong.carrot.dto.TransactionDto;
import com.lauvsong.carrot.service.BankService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/banks")
public class BankController {

    private final BankService bankService;

    /**
     * 은행 별 거래내역 조회
     */
    @GetMapping()
    public List<BankDto> findByBank(@RequestParam(value = "date", required = false) String stringDate,
                                                          @RequestParam(value = "bank_code", required = false) String bankCode,
                                                          @RequestParam(value = "type", required = false) TransactionType transactionType) {
        LocalDate date = stringDate != null ? LocalDate.parse(stringDate, DateTimeFormatter.ISO_DATE) : null;

        List<Bank> banks = bankService.findTransactions(Optional.ofNullable(date),
                Optional.ofNullable(bankCode),
                Optional.ofNullable(transactionType));
        List<BankDto> result = banks.stream()
                .map(b -> new BankDto(b))
                .collect(toList());

        return result;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    static class BankDto {
        private String bankCode;
        private String bankName;
        private List<TransactionDto> transactions;

        public BankDto(Bank bank) {
            bankCode = bank.getCode();
            bankName = bank.getName();
            transactions = bank.getTransactions()
                    .stream().map(t -> new TransactionDto(t))
                    .collect(toList());
        }
    }
}
