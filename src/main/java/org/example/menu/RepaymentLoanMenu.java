package org.example.menu;


import org.example.entity.Loan;
import org.example.entity.Student;
import org.example.service.BankCardService;
import org.example.service.LoanService;
import org.example.service.ProcessPaymentService;
import org.example.utill.ApplicationContext;
import org.example.utill.SecurityContext;
import org.example.utill.Validation;

import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RepaymentLoanMenu {
    private final Scanner sc = new Scanner(System.in);
    LoanService loanService = ApplicationContext.getStudentLoanService();
    ProcessPaymentService processPaymentService = ApplicationContext.getProcessPaymentService();
    BankCardService bankCardService  = ApplicationContext.getBankCardService();
    public void paymentSystem(){
        System.out.println("************* payment service and display loan installment *************");
        System.out.println("enter your select");
        System.out.println("1. register payment");
        System.out.println("2. back");
        int choice;
        do {
            choice = input();
            switch (choice) {
                case 1 -> registerRepayment();
                case 2 -> {
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.mainMenu();
                }
                default -> {
                    System.out.println("invalid select,enter a valid option");
                    paymentSystem();
                }
            }
        }while (choice != 3);
    }
    public void registerRepayment() {
        Student student = SecurityContext.getCurrentStudent();
        Date currentTime = SecurityContext.getCurrentTime();
        if (Validation.activationRepayment(student,currentTime)){
            loanService.findAllByNationalCode(student.getNationalCode()).forEach(System.out::println);
            System.out.println("enter loan id");
            int loanId = input();
            if (loanService.load(loanId)!=null){
                Loan loan = loanService.load(loanId);
                SecurityContext.fillContext(loan);
                if (processPaymentService.findByLoan(loanId).size()==0){
                    processPaymentService.generateInstallment(loan);
                    System.out.println("your installments for this loan register");
                    typeOfRepayment();
                }else{
                    System.out.println("your installments for this loan has already been registered!!!");
                    typeOfRepayment();
                }
            }else{
                System.out.println("id does not exist!!!!!");
                paymentSystem();
            }

        }else {
            System.out.println("You can not repayment your loan you have not graduated yet!!!");
            paymentSystem();
        }
    }
    public void typeOfRepayment(){
        String rePayment = """
                1- installments paid
                2- unpaid installments
                3- installment payment
                4- back 
                """;
        System.out.println(rePayment);
        int selectOption = input();
        switch (selectOption){
            case 1 -> installmentsPaid();
            case 2 -> unpaidInstallments();
            case 3 -> installmentsPayment();
            case 4 -> paymentSystem();
        }
    }
    private void installmentsPayment() {
        Loan loan = SecurityContext.getCurrentLoan();
        System.out.println("enter your card number");
        String cardNumber = sc.next();
        while (!Validation.isValidCardNumberWithRegex(cardNumber)){
            System.out.println("card number length in invalid");
            System.out.println("enter correct card number!!!!!");
            cardNumber = sc.next();
        }
        System.out.println("enter ccv2");
        int ccv2 = input();
        while (ccv2<99 || ccv2>9999){
            System.out.println("ccv2 is not current");
            ccv2 = input();
        }
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
        if(bankCardService.findByCcv2AndDate(cardNumber,ccv2)){
            if (loanService.findByBankCard(loan,cardNumber)==1){
                processPaymentService.findByPending(loan).stream()
                        .map(row -> Arrays.stream(row)
                                .map(Object::toString)
                                .collect(Collectors.joining(" ")))
                        .forEach(System.out::println);
                System.out.println("choose number Of Installment ");
                int numberOfInstallment = input();
                processPaymentService.updateInstallmentStatus(loan, numberOfInstallment);
                System.out.println("your installment update ");
                typeOfRepayment();
            }else {
                System.out.println("your card number is not match");
                typeOfRepayment();
            }
        }else {
            System.out.println("this card does not exit");
            typeOfRepayment();
        }

    }

    private void unpaidInstallments() {
        Loan loan = SecurityContext.getCurrentLoan();
        processPaymentService.findByPending(loan).stream()
                .map(row -> Arrays.stream(row)
                        .map(Object::toString)
                        .collect(Collectors.joining(" ")))
                .forEach(System.out::println);
        typeOfRepayment();
    }
    private void installmentsPaid() {
        Loan loan = SecurityContext.getCurrentLoan();
        if (processPaymentService.findByPaid(loan) != null){
            processPaymentService.findByPaid(loan).stream()
                    .map(row -> Arrays.stream(row)
                            .map(Object::toString)
                            .collect(Collectors.joining(" ")))
                    .forEach(System.out::println);
            typeOfRepayment();
        }else{
            System.out.println(" no installment have been paid!!!! ");
            typeOfRepayment();
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
                System.out.println(" enter valid number !");
            }
        }
    }
}
