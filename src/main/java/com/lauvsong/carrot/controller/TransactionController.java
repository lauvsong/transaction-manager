package com.lauvsong.carrot.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.lauvsong.carrot.service.TransactionService;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * CSV 파일 저장
     */
    @PostMapping()
    public SaveCsvResponse saveCsv(@RequestParam @Valid MultipartFile file) throws IOException, ParseException {
        transactionService.save(file);
        return new SaveCsvResponse("success");
    }

    @Data
    @AllArgsConstructor
    static class SaveCsvResponse {
        private String message;
    }
}
