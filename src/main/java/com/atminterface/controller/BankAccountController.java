package com.atminterface.controller;

import com.atminterface.entity.BankAccount;
import com.atminterface.repository.BankAccountRepository;
import com.atminterface.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BankAccountController {
    /*
    All required classes interfaces and their references
    */
    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    PagesController page;


    /*
    *   Add Account using Account Holder Name And Mobile Number
    */
    @PostMapping("addAccountIntoDb")
    public String addAccountIntoDb(BankAccount bankAccount, Model model){
        try {
            if (bankAccountRepository.findByMobileNumber(bankAccount.getMobileNumber())!=null){
                throw new Exception();
            }
            bankAccount.setAccountHolderBalance(0.0);
            Long accountNumber = bankAccountService.addAccount(bankAccount);
            System.out.println(
                    accountNumber
            );
            model.addAttribute("homePageMessage","your Account Number is "+accountNumber);
        }catch (Exception e){
            model.addAttribute("homePageMessage","Mobile Number is already exists!");
            return page.indexPage();
        }
        return page.indexPage();
    }


    /*
    *   Logic To Find Account from Database
    */
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
