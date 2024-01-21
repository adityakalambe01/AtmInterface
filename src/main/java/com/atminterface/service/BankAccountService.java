package com.atminterface.service;

import com.atminterface.entity.Atm;
import com.atminterface.entity.BankAccount;
import com.atminterface.entity.TransactionHistory;
import com.atminterface.repository.AtmRepository;
import com.atminterface.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class BankAccountService {
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    TransactionHistoryService transactionHistoryService;


    public Long addAccount(BankAccount bankAccount){
        Long accountNumber = bankAccountRepository.save(bankAccount).getAccountNumber();

        TransactionHistory tt = new TransactionHistory();
        tt.setAccountNumber(accountNumber);
        tt.setAmount(0.0);
        tt.setTransactionDate(LocalDate.now());
        tt.setTransactionTime(LocalTime.now());
        tt.setReason("Account Created");
        transactionHistoryService.saveTransaction(tt);

        return accountNumber;
    }



    public boolean depositAmount(Double amount, Long accountNumber){
        BankAccount dbBankAccount = bankAccountRepository.findById(accountNumber).get();
        if (amount==null || amount<0) return false;
        dbBankAccount.setAccountHolderBalance(
                dbBankAccount.getAccountHolderBalance()+amount
        );
        bankAccountRepository.save(dbBankAccount);

        TransactionHistory tt = new TransactionHistory();
        tt.setAccountNumber(accountNumber);
        tt.setAmount(amount);
        tt.setTransactionDate(LocalDate.now());
        tt.setTransactionTime(LocalTime.now());
        tt.setReason("Deposit");
        transactionHistoryService.saveTransaction(tt);

        return true;
    }

    public boolean withdrawAmount(Long accountNumber, Double amount){
        try {
            BankAccount dbBankAccount = bankAccountRepository.findById(accountNumber).get();
            if(amount==null || amount<0 || dbBankAccount.getAccountHolderBalance()-amount<0)
                throw  new Exception();
            dbBankAccount.setAccountHolderBalance(
                    dbBankAccount.getAccountHolderBalance()-amount
            );
            bankAccountRepository.save(dbBankAccount);
        }catch (Exception e){
            return false;
        }
        TransactionHistory tt = new TransactionHistory();
        tt.setAccountNumber(accountNumber);
        tt.setAmount(amount);
        tt.setTransactionDate(LocalDate.now());
        tt.setTransactionTime(LocalTime.now());
        tt.setReason("Withdraw");
        transactionHistoryService.saveTransaction(tt);
        return true;
    }


    public double currentBalance(Long accountNumber){
        return bankAccountRepository.findById(accountNumber).get().getAccountHolderBalance();
    }

    public BankAccount getAccount(Long mobileNumber){
        return bankAccountRepository.findByMobileNumber(mobileNumber);
    }
}
