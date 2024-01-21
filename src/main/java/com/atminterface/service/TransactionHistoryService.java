package com.atminterface.service;

import com.atminterface.entity.TransactionHistory;
import com.atminterface.repository.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionHistoryService {
    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;
    public void saveTransaction(TransactionHistory transactionHistory){
        transactionHistoryRepository.save(transactionHistory);
    }
}
