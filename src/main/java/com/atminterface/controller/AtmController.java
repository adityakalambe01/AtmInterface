package com.atminterface.controller;

import com.atminterface.entity.Atm;
import com.atminterface.entity.BankAccount;
import com.atminterface.entity.TransactionHistory;
import com.atminterface.repository.AtmRepository;
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
import java.util.prefs.Preferences;

@Controller
public class AtmController {
    /*
    All required classes interfaces and their references
    */
    @Autowired
    AtmService atmService;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    PagesController page;

    @Autowired
    AtmRepository atmRepository;


    static HttpSession httpSession;

    /*
    *     ATM Login Logic
    */
    @RequestMapping("login")
    public String login(Atm atm, HttpServletRequest request, Model model){

        Boolean login = atmService.loginIntoAtm(atm);
        if (login==null){
            model.addAttribute("invalidAccountNumber","Invalid Account Number");
            return page.atmLoginPage();

        } else if (login) {
            httpSession = request.getSession();
            httpSession.setAttribute("holderAccountNumber", atm.getAccountNumber());
            httpSession.setAttribute("holderAtmPin",atm.getAtmPin());

            model.addAttribute("message","Welcome "+bankAccountRepository.findById(atm.getAccountNumber()).get().getAccountHolderName());

            System.out.println(httpSession.getId());
        }else{
            model.addAttribute("invalidPin","Wrong Pin Entered");
            return page.atmLoginPage();
        }
        return page.welcomePage();
    }

    /*
    *    ATM Logout Logic
    */
    @RequestMapping("logout")
    public String logout() {
        HttpSession httpSession1 = AtmController.httpSession;
        httpSession1.invalidate();
        System.out.println(httpSession1.getId());
        System.out.println("Logout");
        return page.indexPage();
    }

    /*
    *    Deposit Balance Login
    */
    @RequestMapping("depositBalance")
    public String depositBalance(Double amount, Model model){
        System.out.println(amount);
        HttpSession httpSession1 = AtmController.httpSession;
        System.out.println(httpSession1.getId());
        Long accNo = (Long)httpSession1.getAttribute("holderAccountNumber");
        if (

                amount!=0 && bankAccountService.depositAmount(amount, accNo)
        ){
            System.out.println("Amount Deposit "+amount);
            model.addAttribute("message","Successfully deposit amount "+amount);
            return page.welcomePage();
        }
        System.out.println("something went wrong");
        model.addAttribute("message","Something went wrong when depositing amount "+amount);
        return page.welcomePage();
    }


    /*
    *       Withdraw Amount Logic
    */
    @RequestMapping("withdrawBalance")
    public String withdrawBalance(Double amount, Model model){
        HttpSession httpSession1 = AtmController.httpSession;
        System.out.println(httpSession1.getId());
        Long accNo = (Long)httpSession1.getAttribute("holderAccountNumber");

        System.out.println(amount);
        System.out.println(accNo);

//        boolean value = bankAccountService.withdrawAmount(accNo, amount);
//        System.out.println(value);
        if (
                amount!=0 && bankAccountService.withdrawAmount(accNo, amount)
        ){
            System.out.println("withdraw");
            model.addAttribute("message","Successfully withdraw amount "+amount);
            return page.welcomePage();
        }
        System.out.println("something went wrong");

        model.addAttribute("message","Invalid withdrawal amount "+amount);
        return page.welcomePage();
    }



    /*
    *   Create Atm using Account Number
    */
    @PostMapping("addAtmIntoDb")
    public String addAtmIntoDb(Long MobileNumber,Integer atmPin, Model model){

        try {
            Long accountNumber = bankAccountRepository.findByMobileNumber(MobileNumber).getAccountNumber();
            if(accountNumber==null) {
                model.addAttribute("accountNumber","Mobile Number is not registered!");
                throw new Exception();
            }else if (atmService.getAtmData(accountNumber)!=null){
                model.addAttribute("accountNumber","ATM already exists!");
                throw new Exception();
            }

            Atm atm = new Atm();
            atm.setAccountNumber(accountNumber);
            atm.setAtmPin(atmPin);
            atmService.saveAtm(atm);

        }catch (Exception e){
            return page.indexPage();
        }
        return page.atmLoginPage();
    }


    /*
    *   ATM Pin Change Logic
    */
    @RequestMapping("changePin")
    public String validatePin(Integer amount){
        System.out.println("Inside Change Pin");

        HttpSession httpSession1 = AtmController.httpSession;
        Long accountNumber =(Long) httpSession1.getAttribute("holderAccountNumber");
        if (!atmService.updatePin(accountNumber, amount)){
            return page.welcomePage();
        }

        return page.welcomePage();
    }


    /*
    *   Bank Balance will Be shown
    */
    @RequestMapping("getBalance")
    public String getBalance(@NotNull Model model){
        HttpSession httpSession1 = AtmController.httpSession;
        Long accountNumber =(Long) httpSession1.getAttribute("holderAccountNumber");
        System.out.println(
                bankAccountRepository.findById(accountNumber).get().getAccountHolderBalance()
        );
        model.addAttribute("message",
                "your balance is "+bankAccountService.currentBalance(accountNumber));

        return page.welcomePage();
    }


    /*
    *    Transaction History Logic using Account Number
    */
    @RequestMapping("accountHistory")
    public String getHistory(Model model){
        HttpSession httpSession1 = AtmController.httpSession;
        Long accountNumber =(Long) httpSession1.getAttribute("holderAccountNumber");
        model.addAttribute("transactionList",
                transactionHistoryRepository.getTransactionHistoryByAccountNumberOrderByTransactionTimeDesc(accountNumber)
                );
        return page.transactionHistoryPage();
    }


    /*
    *   Update Pin using Account Number, Mobile Number, Updated Pin
    */
    @RequestMapping("updatePin")
    public String updatePin(Long accountNumber, Long mobileNumber, Integer atmPin, Model model){
        try {
            if (
                    bankAccountRepository.findByMobileNumber(mobileNumber)==null
            ){
                model.addAttribute("invalidMobileNumber","Invalid Mobile Number");
                throw new Exception();
            }  else if (atmRepository.findByAccountNumber(accountNumber)==null) {
                model.addAttribute("invalidAccountNumber","Invalid Account Number");
                throw new Exception();
            }
            Atm atm = atmService.getAtmData(accountNumber);
            atm.setAtmPin(atmPin);
            atmService.saveAtm(atm);
        }catch (Exception e){
            return page.forgetPin();
        }
        return page.atmLoginPage();
    }
}
