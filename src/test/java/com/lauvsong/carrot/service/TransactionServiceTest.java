package com.lauvsong.carrot.service;

import com.lauvsong.carrot.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class TransactionServiceTest {

    @Autowired private TransactionService transactionService;
    @Autowired private TransactionRepository transactionRepository;

    @BeforeEach
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    @Test
    public void CSV_저장() throws Exception {
        //given
        final String FILE_PATH = getClass().getClassLoader().getResource("transaction.csv").getFile();
        final String FILE_NAME = "transaction.csv";
        FileInputStream fileInputStream = new FileInputStream(new File(FILE_PATH));

        MockMultipartFile file = new MockMultipartFile("files", FILE_NAME, "text/csv", fileInputStream);

        //when
        transactionService.save(file);
    
        //then
        long count = transactionRepository.count();
        assertThat(count).isEqualTo(7329);
    }
}
