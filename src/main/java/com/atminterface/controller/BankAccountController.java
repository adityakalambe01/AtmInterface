package com.atminterface.controller;

import com.atminterface.entity.BankAccount;
import com.atminterface.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;

    @RequestMapping("addAccountIntoDb")
    public String addAccountIntoDb(BankAccount bankAccount){
        bankAccount.setAccountHolderBalance(0.0);
        System.out.println(
                bankAccountService.addAccount(bankAccount)
        );
        return "pages/AtmLogin";
    }

    @RequestMapping("findAccount")
    public ModelAndView findAccount(Long mobileNumber){
        ModelAndView mv = new ModelAndView();
        String viewName;
        if(
                bankAccountService.getAccount(mobileNumber)==null
        ){
            viewName = "pages/404";
        }else
            viewName= "pages/SetPin";
        mv.setViewName(viewName);
        return mv;
    }
}
