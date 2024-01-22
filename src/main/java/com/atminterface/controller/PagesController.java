package com.atminterface.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PagesController {

    /*
    *   This class Manages all pages redirections
    */
    @RequestMapping("")
    public String homePage(@NotNull Model model){
        model.addAttribute("homePageMessage","Welcome To Online Bank Service");
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

    @RequestMapping("forgetPin")
    public String forgetPin(){
        return "pages/ForgetPin";
    }

    public String indexPage(){
        return "index";
    }

    public String welcomePage(){
        return "pages/welcome";
    }

    public String transactionHistoryPage() {
        return "pages/atmServices/GetHistory";
    }

    public String atmLoginPage(){
        return "pages/AtmLogin";
    }
}
