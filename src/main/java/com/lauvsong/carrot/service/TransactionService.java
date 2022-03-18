package com.lauvsong.carrot.service;

import com.lauvsong.carrot.domain.*;
import com.lauvsong.carrot.repository.TransactionRepository;
import com.lauvsong.carrot.repository.UserRepository;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.lauvsong.carrot.repository.BankRepository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionService {

    private static final String FILE_PATH = "C:\\Users\\jenny\\projects\\carrot\\src\\main\\java\\com\\lauvsong\\carrot\\tmpFiles";

    private final TransactionRepository transactionRepository;
    private final BankRepository bankRepository;
    private final UserRepository userRepository;

    /**
     * CSV 파일 저장
     */
    @Transactional
    public void save(MultipartFile csvFile) throws IOException, ParseException {
        String fileName = csvFile.getOriginalFilename();

        if (!fileName.isEmpty()) {
            File file = new File(FILE_PATH, fileName);
            csvFile.transferTo(file);

            writeCsv(file.getAbsolutePath());
            file.delete();
        }
    }

    /**
     * CSV 파싱 및 DB 동기화
     */
    @Transactional
    public void writeCsv(String filePath) throws IOException, ParseException {
        FileReader fileReader = new FileReader(filePath);
        CSVReader reader = new CSVReader(fileReader);
        String[] nextLine;

        List<User> users = new ArrayList<>();
        List<Transaction> rows = new ArrayList<>();

        while((nextLine = reader.readNext()) != null) {
            CsvRow row = new CsvRow(nextLine);
            Transaction transaction = createTransactionByRow(row);

            users.add(transaction.getUser());
            rows.add(transaction);
        }

        userRepository.saveAll(users);
        transactionRepository.saveAll(rows);

        fileReader.close();
        reader.close();
    }

    /**
     * CSV 행을 Transaction 엔티티 규격으로 변환
     */
    private Transaction createTransactionByRow(CsvRow row) throws ParseException {
        Transaction transaction = new Transaction();
        transaction.setId(row.getId());

        LocalDate date = convertDate(row.getYear(), row.getMonth(), row.getDay());
        transaction.setCreatedDate(date);

        User user = User.createUser(row.getUserId());
        transaction.setUser(user);

        Optional<Bank> bank = bankRepository.findById(row.getBankCode());
        transaction.setBank(bank.get());

        transaction.setTransactionAmount(row.getAmount());
        transaction.setTransactionType(row.getType());

        return transaction;
    }

    /**
     * 날짜 요소 -> LocalDate 변환
     */
    private LocalDate convertDate(int year, int month, int day) throws ParseException {
        LocalDate date = LocalDate.of(year, month, day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date.format(formatter);

        return date;
    }
}
