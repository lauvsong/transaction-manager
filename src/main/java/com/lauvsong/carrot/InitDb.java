package com.lauvsong.carrot;

import com.lauvsong.carrot.domain.Bank;
import com.lauvsong.carrot.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initBanks();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final BankRepository bankRepository;

        private Map<String, String> bankMap = new HashMap<>() {{
            put("004", "국민은행");
            put("011", "농협은행");
            put("020", "우리은행");
            put("088", "신한은행");
            put("090", "카카오뱅크");
        }};

        public void initBanks() {
            List<Bank> banks = new ArrayList<>();

            for (Map.Entry<String, String> elem : bankMap.entrySet()) {
                Bank bank = Bank.createBank(elem.getKey(), elem.getValue());
                banks.add(bank);
            }

            bankRepository.saveAll(banks);
        }
    }
}
