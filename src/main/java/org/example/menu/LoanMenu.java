package org.example.menu;


import org.example.entity.BankCard;
import org.example.entity.Student;
import org.example.entity.enumuration.BankName;
import org.example.entity.enumuration.LoanType;
import org.example.service.BankCardService;
import org.example.service.LoanService;
import org.example.service.dto.LoanRequest;
import org.example.utill.ApplicationContext;
import org.example.utill.SecurityContext;
import org.example.utill.Validation;

import java.time.ZoneId;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LoanMenu {
    private final Scanner sc = new Scanner(System.in);
    LoanService loanService = ApplicationContext.getStudentLoanService();
    RepaymentLoanMenu repaymentLoanMenu = new RepaymentLoanMenu();
    BankCardService bankCardService  = ApplicationContext.getBankCardService();

    public void studentLoanService() {
        System.out.println("*************** user panel ******************");
        System.out.println("1.bank card register");
        System.out.println("2.loan service");
        System.out.println("3.repayment service");
        System.out.println("4.back");
        System.out.println("5.exit");
        System.out.println("enter your select");
        int select;
        do {
            select = input();
            switch (select) {
                case 1 -> bankCardsRegister();
                case 2 -> loanSystem();
                case 3 -> repaymentLoanMenu.paymentSystem();
                case 4 -> {
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.mainMenu();
                }
                case 5 -> System.out.println("exiting the program!! see you later!");
                default -> System.out.println("invalid select,enter a valid option");
            }
        }while (select !=5);
        sc.close();
    }
    private void bankCardsRegister() {
        Student student = SecurityContext.getCurrentStudent();
        BankCard bankCard = new BankCard();
        bankCard.setStudent(student);
        System.out.println("enter your bank name");
        String bank = """
                1- TEJARAT
                2- MASKAN
                3- MELLI
                4- REFAH
                """;
        System.out.println(bank);
        int nameBank = input();
        BankName bankName = null;
        switch (nameBank) {
            case 1 -> bankName = BankName.TEJARAT;
            case 2 -> bankName = BankName.MASKAN;
            case 3 -> bankName = BankName.MELLI;
            case 4 -> bankName = BankName.REFAH;
            default -> {
                System.out.println("invalid input ! ");
                bankCardsRegister();
            }
        }
        bankCard.setBankName(bankName);
        System.out.println("enter your card number");
        String cardNumber = sc.nextLine();
        while (!Validation.isValidCardNumberWithRegex(cardNumber)){
            System.out.println("card number length in invalid");
            System.out.println("enter correct card number!!!!!");
            cardNumber = sc.next();
        }
        bankCard.setCardNumber(cardNumber);
        System.out.println("enter ccv2");
        int ccv2 = input();
        while (ccv2<99 || ccv2>9999){
            System.out.println("ccv2 is not current");
            ccv2 = input();
        }
        bankCard.setCcv2(ccv2);
        System.out.println("enter Card Expiration year");
        int year = input();
        while (year>3000 || year <1900){
            System.out.println("it dose not current year!!");
            year = input();
        }
        System.out.println("enter Card Expiration month");
        int month = input();
        while (month>13 || month <=0){
            System.out.println("it dose not current month!!");
            month = input();
        }

        Date cardExpirationDate = Validation.getDate(year, month, 1);
        bankCard.setCardExpirationDate(cardExpirationDate);
        bankCardService.registerBankCard(bankCard);
        System.out.println("your bank card register");
        studentLoanService();
    }
    public void loanSystem(){
        System.out.println("************* loan receiving and display service *************");
        System.out.println("1- loan apply");
        System.out.println("2- your loans");
        System.out.println("3- back");
        System.out.println("enter your request");
        int request ;
        do {
            request= input();
            switch (request){
                case 1 -> loanRegister();
                case 2 -> findLoansByStudent();
                case 3 -> studentLoanService();
            }
        }while (request!=4);
    }
    private void findLoansByStudent() {
        Student student = SecurityContext.getCurrentStudent();
        loanService.findAllByNationalCode(student.getNationalCode()).forEach(System.out::println);
        loanSystem();
    }
    public void loanRegister() {
        Date currentTime = SecurityContext.getCurrentTime();
        int year = currentTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
        Student student = SecurityContext.getCurrentStudent();
        if (Validation.activationLoanRequest(student,currentTime)){
            if (Validation.isValidDateToGetLoan(year,currentTime)){

                String nationalCode = student.getNationalCode();
                System.out.println("enter your loan type");
                String loanS = """
                1- TUITION
                2- EDUCATIONAL
                3- HOUSING_DEPOSIT
                4 - back to loan system
                """;
                System.out.println(loanS);
                int loanType = input();
                LoanType type = null;
                switch (loanType) {
                    case 1 -> type = LoanType.TUITION;
                    case 2 -> type = LoanType.EDUCATIONAL;
                    case 3 -> type = LoanType.HOUSING_DEPOSIT;
                    case 4 -> loanSystem();
                    default -> {
                        System.out.println("invalid input ! ");
                        loanRegister();
                    }
                }
                System.out.println("enter your card number");
                String cardNumber = sc.nextLine();
                while (!Validation.isValidCardNumberWithRegex(cardNumber)){
                    System.out.println("card number length in invalid");
                    System.out.println("enter correct card number!!!!!");
                    cardNumber = sc.nextLine();
                }
                if (bankCardService.findByStudent(student,cardNumber)== null) bankCardsRegister();
                LoanRequest loanRequest = new LoanRequest(nationalCode,cardNumber,type,currentTime,year);
                loanService.applyLoan(loanRequest);
                loanRegister();
            }else {
                System.out.println("The target date is outside the range.");
                studentLoanService();
            }
        }else {
            System.out.println("You have graduated");
            studentLoanService();
        }

    }
    public Integer input() {
        int i;
        while (true) {
            try {
                i = sc.nextInt();
                sc.nextLine();
                return i;
            } catch (InputMismatchException in) {
                sc.nextLine();
                System.out.println("enter valid number !");
            }
        }
    }
}
