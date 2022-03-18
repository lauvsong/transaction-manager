package com.lauvsong.carrot.controller;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lauvsong.carrot.domain.TransactionType;
import com.lauvsong.carrot.domain.User;
import com.lauvsong.carrot.dto.TransactionDto;
import com.lauvsong.carrot.service.UserService;
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

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * 유저 별 거래내역 조회
     */
    @GetMapping()
    public List<UserDto> findByUser(@RequestParam(value = "date", required = false) String stringDate,
                                                          @RequestParam(value = "type", required = false) TransactionType transactionType) {

        LocalDate date = stringDate != null ? LocalDate.parse(stringDate, DateTimeFormatter.ISO_DATE) : null;

        List<User> users = userService.findTransactions(Optional.ofNullable(date),
                Optional.ofNullable(transactionType));
        List<UserDto> result = users.stream()
                .map(u -> new UserDto(u))
                .collect(toList());

        return result;
    }

    @Data
    @JsonNaming(SnakeCaseStrategy.class)
    static class UserDto {
        private Long userId;
        private List<TransactionDto> transactions;

        public UserDto(User user) {
            userId = user.getId();
            transactions = user.getTransactions()
                    .stream().map(t -> new TransactionDto(t))
                    .collect(toList());
        }
    }
}
