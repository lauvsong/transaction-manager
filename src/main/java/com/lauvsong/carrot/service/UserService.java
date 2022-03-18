package com.lauvsong.carrot.service;

import com.lauvsong.carrot.domain.TransactionType;
import com.lauvsong.carrot.domain.User;
import com.lauvsong.carrot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 유저별 입출금 내역
     */
    public List<User> findTransactions(Optional<LocalDate> date,
                                    Optional<TransactionType> type) {
        List<User> users = userRepository.findAllWithTransaction(date, type);
        return users;
    }
}
