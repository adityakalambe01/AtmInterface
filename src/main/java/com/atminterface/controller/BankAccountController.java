package com.atminterface.controller;

import com.atminterface.entity.BankAccount;
import com.atminterface.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    PagesController page;

    @RequestMapping("addAccountIntoDb")
    public String addAccountIntoDb(BankAccount bankAccount, Model model){
        bankAccount.setAccountHolderBalance(0.0);
        Long accountNumber = bankAccountService.addAccount(bankAccount);
        System.out.println(
                accountNumber
        );
        model.addAttribute("accountNumber","Your Account Number is "+accountNumber);
        return page.indexPage();
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
