package com.example.rewardoapractice.repository;

import com.example.rewardoapractice.pojo.entity.ShoppingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<ShoppingTransaction, Long> {
}
