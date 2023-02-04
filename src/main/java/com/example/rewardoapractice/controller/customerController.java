package com.example.rewardoapractice.controller;

import com.example.rewardoapractice.pojo.entity.Customer;
import com.example.rewardoapractice.pojo.entity.ShoppingTransaction;
import com.example.rewardoapractice.service.CustomerService;
import com.example.rewardoapractice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class customerController {
    private final CustomerService customerService;
    private final TransactionService transactionService;

    @Autowired
    public customerController(CustomerService customerService, TransactionService transactionService) {
        this.customerService = customerService;
        this.transactionService = transactionService;
    }

    @GetMapping("/user/{id}/total-reward")
    public int getTotalRewardsPoints(@PathVariable Long customerId){
        return customerService.calculateTotalRewardPoints(customerId);
    }

    @GetMapping("/user/{id}/monthly-reward")
    public int getMonthlyRewardPoints(@PathVariable Long customerId, @RequestParam String month) {
        return customerService.calculateMonthlyRewardPoints(customerId).getOrDefault(month, 0);
    }

    @GetMapping("/transaction/{id}")
    public ResponseEntity<ShoppingTransaction> getTransactionById(@PathVariable Long transactionId){
        return new ResponseEntity<>(transactionService.getByTransactionId(transactionId), HttpStatus.OK);
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<ShoppingTransaction> addTransactionToCustomer(@RequestBody ShoppingTransaction trans){
        return new ResponseEntity<>(customerService.addTransaction(trans), HttpStatus.CREATED);
    }
}
