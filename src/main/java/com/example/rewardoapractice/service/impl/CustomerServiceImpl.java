package com.example.rewardoapractice.service.impl;

import com.example.rewardoapractice.pojo.entity.Customer;
import com.example.rewardoapractice.pojo.entity.ShoppingTransaction;
import com.example.rewardoapractice.repository.CustomerRepository;
import com.example.rewardoapractice.repository.TransactionRepository;
import com.example.rewardoapractice.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private static final Logger logger = Logger.getLogger(CustomerServiceImpl.class.getName());

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public int calculateTotalRewardPoints(Long customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        if (customer == null) {
            logger.log(Level.INFO, "no customer found");
            throw new EntityNotFoundException();
        }
        List<ShoppingTransaction> transactionList = customer.getTransactions();
        int totalRewardsPoint = 0;
        for (ShoppingTransaction tran : transactionList) {
            totalRewardsPoint += tran.getRewardsPoint();
        }
        return totalRewardsPoint;
    }

    @Override
    public Map<Integer, Integer> calculateMonthlyRewardPoints(Long customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        if (customer == null) {
            logger.log(Level.INFO, "no customer found");
            throw new EntityNotFoundException();
        }
        List<ShoppingTransaction> transactionList = customer.getTransactions();
        LocalDate currentDate = LocalDate.now();
        Map<Integer, Integer> monthlyRewards = new HashMap<>();

        for (ShoppingTransaction tran: transactionList){
            long diffDays = DAYS.between(tran.getDate(), currentDate);
            if (diffDays > 90) {
                continue;
            }
            int month = tran.getDate().getMonth().getValue();
            if (monthlyRewards.containsKey(month)) {
                monthlyRewards.put(month, monthlyRewards.get(month) + tran.getRewardsPoint());
            }
            else {
                monthlyRewards.put(month, tran.getRewardsPoint());
            }
        }

        return monthlyRewards;
    }

    @Override
    public ShoppingTransaction addTransaction(ShoppingTransaction transaction) {
        Long customerId = transaction.getCustomer().getCustomerId();
        Customer customer = customerRepository.findById(customerId).get();
        if (customer == null) {
            logger.log(Level.INFO, "no customer found");
            throw new EntityNotFoundException();
        }
        customer.getTransactions().add(transaction);
        transactionRepository.saveAndFlush(transaction);
        return transaction;
    }
}
