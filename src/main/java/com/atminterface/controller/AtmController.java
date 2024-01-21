package com.atminterface.controller;

import com.atminterface.entity.Atm;
import com.atminterface.entity.BankAccount;
import com.atminterface.entity.TransactionHistory;
import com.atminterface.repository.BankAccountRepository;
import com.atminterface.repository.TransactionHistoryRepository;
import com.atminterface.service.AtmService;
import com.atminterface.service.BankAccountService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AtmController {
    @Autowired
    AtmService atmService;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    static HttpSession httpSession;


    @RequestMapping("login")
    public ModelAndView login(Atm atm, HttpServletRequest request){
        ModelAndView mv = new ModelAndView();

        String viewName;
        Boolean login = atmService.loginIntoAtm(atm);
        if (login==null){
            viewName = "pages/AtmLogin";

        } else if (login) {
            httpSession = request.getSession();
            httpSession.setAttribute("holderAccountNumber", atm.getAccountNumber());
            httpSession.setAttribute("holderAtmPin",atm.getAtmPin());

            System.out.println(httpSession.getId());
            viewName = "pages/welcome";
        }else{
            viewName = "pages/AtmLogin";
        }
        mv.setViewName(viewName);

    return mv;
    }


    @RequestMapping("depositBalance")
    public String depositBalance(Double amount, Model model){
        System.out.println(amount);
        HttpSession httpSession1 = AtmController.httpSession;
        System.out.println(httpSession1.getId());
        Long accNo = (Long)httpSession1.getAttribute("holderAccountNumber");
        if (

                bankAccountService.depositAmount(amount, accNo)
        ){
            System.out.println("Amount Deposit "+amount);
            model.addAttribute("message","Successfully deposit amount "+amount);
            return "pages/welcome";
        }
        System.out.println("something went wrong");
        model.addAttribute("message","Something went wrong when depositing amount "+amount);
        return "pages/welcome";
    }

    @RequestMapping("withdrawBalance")
    public String withdrawBalance(Double amount, Model model){
        HttpSession httpSession1 = AtmController.httpSession;
        System.out.println(httpSession1.getId());
        Long accNo = (Long)httpSession1.getAttribute("holderAccountNumber");

        System.out.println(amount);
        System.out.println(accNo);

        boolean value = bankAccountService.withdrawAmount(accNo, amount);
        System.out.println(value);
        if (value){
            System.out.println("withdraw");
            model.addAttribute("message","Successfully withdraw amount "+amount);
            return "pages/welcome";
        }
        System.out.println("something went wrong");

        model.addAttribute("message","Invalid withdrawal amount "+amount);
        return "pages/welcome";
    }

    @RequestMapping("logout")
    public String logout() {
        HttpSession httpSession1 = AtmController.httpSession;
        httpSession1.invalidate();
        System.out.println(httpSession1.getId());
        System.out.println("Logout");
        return "pages/AtmLogin";
    }

    @RequestMapping("addAtmIntoDb")
    public String addAtmIntoDb(Atm atm){

        try {
            Long accountNumber = bankAccountRepository.findByMobileNumber(atm.getAccountNumber()).getAccountNumber();
            if(accountNumber==null)
                throw new Exception();
            atm.setAccountNumber(accountNumber);
            atm.setAtmPin(atm.getAtmPin());
            atmService.saveAtm(atm);

        }catch (Exception e){
            return "index";
        }
        return "pages/AtmLogin";
    }

    @RequestMapping("changePin")
    public String validatePin(Integer amount){
        System.out.println("Inside Change Pin");

        HttpSession httpSession1 = AtmController.httpSession;
        Long accountNumber =(Long) httpSession1.getAttribute("holderAccountNumber");
        if (!atmService.updatePin(accountNumber, amount)){
            return "pages/welcome";
        }

        return "pages/welcome";
    }

    @RequestMapping("getBalance")
    public String getBalance(@NotNull Model model){
        HttpSession httpSession1 = AtmController.httpSession;
        Long accountNumber =(Long) httpSession1.getAttribute("holderAccountNumber");
        System.out.println(
                bankAccountRepository.findById(accountNumber).get().getAccountHolderBalance()
        );
        model.addAttribute("message",
                "your balance is "+bankAccountRepository.findById(accountNumber).get().getAccountHolderBalance());

        return "pages/welcome";
//        return "pages/atmServices/ShowBalance";
    }

    @RequestMapping("accountHistory")
    public String getHistory(Model model){
        HttpSession httpSession1 = AtmController.httpSession;
        Long accountNumber =(Long) httpSession1.getAttribute("holderAccountNumber");
        model.addAttribute("transactionList",
                transactionHistoryRepository.getTransactionHistoryByAccountNumberOrderByTransactionTimeDesc(accountNumber)
                );
        return "pages/atmServices/GetHistory";
    }
}
