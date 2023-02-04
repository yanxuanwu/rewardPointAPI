package com.example.rewardoapractice.service;

import com.example.rewardoapractice.pojo.entity.ShoppingTransaction;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CustomerService {
    int calculateTotalRewardPoints(Long customerId);
    Map<Integer, Integer> calculateMonthlyRewardPoints(Long customerId);
    ShoppingTransaction addTransaction(ShoppingTransaction transaction);
}
