package com.example.rewardoapractice.pojo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;
    @Column
    private double amount;
    @Column
    private LocalDate date;

    public int getRewardsPoint(){
        int rewardPoints = 0;
        if (amount > 100) {
            rewardPoints += 2 * (amount - 100) + 50;
        }
        else if (amount > 50) {
            rewardPoints += 1 * (amount - 50);
        }
        else {
            rewardPoints = 0;
        }
        return rewardPoints;
    }
}
