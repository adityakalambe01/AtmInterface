package com.atminterface.controller;

import com.atminterface.entity.Atm;
import com.atminterface.entity.BankAccount;
import com.atminterface.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AtmController {
    @Autowired
    BankAccountService bankAccountService;

    //atmcontroller methods
    @RequestMapping("login")
    public String login(Atm atm){
        if(atm.getAccountNumber()==1 && atm.getAtmPin()==1234)
            return "pages/welcome";
        return "pages/404";
    }
}
