package com.example.rewardoapractice.service;

import com.example.rewardoapractice.pojo.entity.ShoppingTransaction;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TransactionService {
    public ShoppingTransaction getByTransactionId(Long id);
}
