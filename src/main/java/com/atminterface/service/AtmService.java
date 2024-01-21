package com.atminterface.service;

import com.atminterface.entity.Atm;
import com.atminterface.entity.BankAccount;
import com.atminterface.repository.AtmRepository;
import com.atminterface.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtmService {
    @Autowired
    AtmRepository atmRepository;
    @Autowired
    BankAccountService bankAccountService;

    public void setPin(Atm atm){
        atmRepository.save(atm);
    }

    public boolean updatePin(Long accountNumber, int updatedPin){
        try {
            Atm dbAtm = atmRepository.findByAccountNumber(accountNumber);
            if(dbAtm==null) throw new Exception();
            dbAtm.setAtmPin(updatedPin);
            atmRepository.save(dbAtm);
        }catch (Exception e){
            return false;
        }
        return true;
    }


    public void saveAtm(Atm atm){
        atmRepository.save(atm);
    }

    public Boolean loginIntoAtm(Atm atm){
        try {
            Atm dbAtm = atmRepository.findByAccountNumber(atm.getAccountNumber());
            if (dbAtm==null) throw new Exception();
            System.out.println(dbAtm);
            if (atm.getAtmPin()==dbAtm.getAtmPin()){
                return true;
            }
        }catch (Exception e){
            return null;
        }
        return false;

    }
}
