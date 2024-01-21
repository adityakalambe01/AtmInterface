package com.atminterface.repository;

import com.atminterface.entity.Atm;
import com.atminterface.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtmRepository extends JpaRepository<Atm, Long> {
    Atm findByAccountNumber(Long accountNumber);
}
