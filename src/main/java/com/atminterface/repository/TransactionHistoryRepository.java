package com.atminterface.repository;

import com.atminterface.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    List<TransactionHistory> getTransactionHistoryByAccountNumberOrderByTransactionTimeDesc(Long accountNumber);
}
