package com.lauvsong.carrot.service;

import com.lauvsong.carrot.domain.TransactionType;
import com.lauvsong.carrot.domain.User;
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
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired private UserService userService;
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
    public void 유저별_조회() throws Exception {
        //given
        String stringDate = "2021-01-01";
        LocalDate date = LocalDate.parse(stringDate, DateTimeFormatter.ISO_DATE);

        TransactionType type = TransactionType.WITHDRAW;

        //when
        List<User> users = userService.findTransactions(of(date), of(type));

        //then
        assertThat(users.size()).isEqualTo(7);
    }

    @Test
    public void 유저별_조회_NULL파라미터() throws Exception {
        //given
        Optional<LocalDate> date = empty();
        Optional<TransactionType> type = empty();

        //when
        List<User> users = userService.findTransactions(date, type);

        //then
        assertThat(users.size()).isEqualTo(25);
    }
}
