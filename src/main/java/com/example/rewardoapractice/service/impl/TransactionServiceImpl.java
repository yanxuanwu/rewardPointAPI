package com.example.rewardoapractice.service.impl;

import com.example.rewardoapractice.pojo.entity.ShoppingTransaction;
import com.example.rewardoapractice.repository.TransactionRepository;
import com.example.rewardoapractice.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private static final Logger logger = Logger.getLogger(TransactionServiceImpl.class.getName());

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public ShoppingTransaction getByTransactionId(Long id) {
        ShoppingTransaction trans = transactionRepository.findById(id).get();
        if (trans == null) {
            logger.log(Level.INFO, "no transation found");
            throw new EntityNotFoundException();
        }
        return trans;
    }
}
