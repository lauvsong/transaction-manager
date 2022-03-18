package com.lauvsong.carrot.service;

import com.lauvsong.carrot.domain.Bank;
import com.lauvsong.carrot.domain.TransactionType;
import com.lauvsong.carrot.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;

    /**
     * 은행별 입출금 내역
     */
    public List<Bank> findTransactions(Optional<LocalDate> date,
                                    Optional<String> bankCode,
                                    Optional<TransactionType> type) {

        Optional<Bank> bank = Optional.empty();
        if (bankCode.isPresent()) {
            bank = bankRepository.findById(bankCode.get());
        }
        List<Bank> banks = bankRepository.findAllWithTransaction(date, bank, type);
        return banks;
    }
}
