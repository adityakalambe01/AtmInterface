package com.atminterface.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PagesController {
    @RequestMapping("")
    public String homePage(@NotNull Model model){
        model.addAttribute("accountNumber","Welcome To Online Bank Service");
        return "index";
    }

    @RequestMapping("AddAccount")
    public String addBankAccount(){
        return "pages/AddAccount";
    }

    @RequestMapping(value = "userLogin")
    public String userLogin(){
        return "pages/AtmLogin";
    }

    @RequestMapping("addAtm")
    public String createAtm(){
        return "pages/AddAtm";
    }

    @RequestMapping("withdrawBalancePage")
    public String withdrawBalancePage(){
        return "pages/atmServices/WithdrawalPage";
    }

    @RequestMapping("depositBalancePage")
    public String depositBalancePage(){
        return "pages/atmServices/DepositPage";

    }
    @RequestMapping("showBalancePage")
    public String showBalancePage(){
        return "pages/atmServices/ShowBalance";
    }

    @RequestMapping("changePinPage")
    public String changePinPage(){
        return "pages/atmServices/ChangePin";
    }

    @RequestMapping("forgetPin")
    public String forgetPin(){
        return "pages/ForgetPin";
    }
}
