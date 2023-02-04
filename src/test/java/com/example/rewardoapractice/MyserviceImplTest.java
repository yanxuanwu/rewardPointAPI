package com.example.rewardoapractice;
import static org.junit.Assert.*;
import com.example.rewardoapractice.pojo.entity.Customer;
import com.example.rewardoapractice.pojo.entity.ShoppingTransaction;
import com.example.rewardoapractice.repository.CustomerRepository;
import com.example.rewardoapractice.service.CustomerService;
import com.example.rewardoapractice.service.impl.CustomerServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MyserviceImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    public void setUp(){
        customer1 = new Customer();
        customer1.setCustomerId(1L);
        customer1.setName("Roy");

        List<ShoppingTransaction> transactionList1 = new ArrayList<>();
        ShoppingTransaction trans1 = new ShoppingTransaction(1L, customer1, 120.0, LocalDate.of(2022,11,23));
        ShoppingTransaction trans2 = new ShoppingTransaction(2L, customer1, 50.0, LocalDate.of(2022,8,23));
        transactionList1.add(trans1);
        transactionList1.add(trans2);

        customer1.setTransactions(transactionList1);

        customer2 = new Customer();
        customer2.setCustomerId(2L);
        customer2.setName("Ann");

        List<ShoppingTransaction> transactionList2 = new ArrayList<>();
        ShoppingTransaction trans3 = new ShoppingTransaction(1L, customer1, 50.0, LocalDate.of(2022,11,23));
        ShoppingTransaction trans4 = new ShoppingTransaction(2L, customer1, 100.0, LocalDate.of(2022,12,23));
        transactionList2.add(trans3);
        transactionList2.add(trans4);

        customer2.setTransactions(transactionList2);
    }


    @Test
    public void getPointTest(){
        when(customerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.ofNullable(customer1));
        Map<String, Integer> expectedRewardsPerMonth1 = new HashMap<>();
        expectedRewardsPerMonth1.put(String.valueOf(11), 90);
        int totalRewardPoints = customerServiceImpl.calculateTotalRewardPoints(customer1.getCustomerId());
        assertEquals(totalRewardPoints, 90);

        Map<Integer, Integer> expectedRewardsPerMonth2 = new HashMap<>();
        expectedRewardsPerMonth2.put(11, 0);
        expectedRewardsPerMonth2.put(12,50);
        when(customerRepository.findById(customer2.getCustomerId())).thenReturn(Optional.ofNullable(customer2));
        Map<Integer, Integer> monthlyRewardPoints = customerServiceImpl.calculateMonthlyRewardPoints(customer2.getCustomerId());
        assertEquals(expectedRewardsPerMonth2, monthlyRewardPoints);
    }
}
