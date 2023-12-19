package org.example.service.Impl;

import org.example.base.service.BaseServiceImpl;
import org.example.entity.ProcessPayment;
import org.example.entity.Loan;
import org.example.entity.Student;
import org.example.entity.enumuration.InstallmentStatus;
import org.example.repository.ProcessPaymentRepository;
import org.example.service.ProcessPaymentService;
import org.example.service.LoanService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ProcessPaymentServiceImpl extends BaseServiceImpl<Integer, ProcessPayment, ProcessPaymentRepository>
                               implements ProcessPaymentService {

    private final LoanService loanService;
    public ProcessPaymentServiceImpl(ProcessPaymentRepository repository, LoanService loanService) {
        super(repository);
        this.loanService = loanService;
    }
//    @Override
//    public void processRepayment(Loan loan) {
//        ProcessPayment repayment = new ProcessPayment();
//        repayment.setLoan(loan);
//        repayment.setRepaymentDate(repaymentDate(loan.getStudent()));
//        generateInstallment(loan);
//    }
    @Override
    public List<ProcessPayment> generateInstallment(Loan loan) {
        List<ProcessPayment> installments = new ArrayList<>();
        double paymentAmount = loan.getAmount() + loan.getAmount() * 0.04;
        double coefficient = (double) Math.round(paymentAmount/372);
        Date dueDate = repaymentDate(loan.getStudent());
        for (int i = 0; i < 60; i++) {
            ProcessPayment repayment = new ProcessPayment();
            repayment.setLoan(loan);
            repayment.setRepaymentAmount(paymentAmount);
            repayment.setRepaymentDate(repaymentDate(loan.getStudent()));
            repayment.setInstallmentStatus(InstallmentStatus.PENDING);
            if (i < 12) {
                repayment.setInstallmentAmount(coefficient);
                repayment.setDueDate(dueDate);
                repayment.setNumberOfInstallment(i);
                repository.saveOrUpdate(repayment);
                installments.add(repayment);
                dueDate = getDate(repayment);
            }
            if ( i >= 12 && i < 24) {
                repayment.setInstallmentAmount(coefficient*2);
                repayment.setDueDate(dueDate);
                repayment.setNumberOfInstallment(i);
                repository.saveOrUpdate(repayment);
                installments.add(repayment);
                dueDate = getDate(repayment);
            }
            if ( i >= 24 && i < 36) {
                repayment.setInstallmentAmount(coefficient*4);
                repayment.setDueDate(dueDate);
                repayment.setNumberOfInstallment(i);
                repository.saveOrUpdate(repayment);
                installments.add(repayment);
                dueDate = getDate(repayment);
            }
            if ( i >= 36 && i < 48) {
                repayment.setInstallmentAmount(coefficient*8);
                repayment.setDueDate(dueDate);
                repayment.setNumberOfInstallment(i);
                repository.saveOrUpdate(repayment);
                installments.add(repayment);
                dueDate = getDate(repayment);
            }
            if (i >= 48) {
                repayment.setInstallmentAmount(coefficient*16);
                repayment.setDueDate(dueDate);
                repayment.setNumberOfInstallment(i);
                repository.saveOrUpdate(repayment);
                installments.add(repayment);
                dueDate = getDate(repayment);
            }
        }
        return installments;
    }

    private static Date getDate(ProcessPayment repayment) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(repayment.getDueDate());
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    public boolean activationRepayment(Student student,Date currentDate){
        LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        switch (student.getEducationLevel()) {
            case ASSOCIATE ,BACHELOR_DISCONTINUOUS , MASTER_DISCONTINUOUS -> {
                if (localDate.getYear()<=student.getEntranceYear()+2){
                    return false;
                }
            }
            case BACHELOR -> {
                if (localDate.getYear()<=student.getEntranceYear()+4) {
                    return false;
                }
            }
//            case BACHELOR_DISCONTINUOUS ->
            case MASTER -> {
                if (localDate.getYear()<=student.getEntranceYear()+6) {
                    return false;
                }
            }
//            case MASTER_DISCONTINUOUS ->
            case DOCTORATE , PHD-> {
                if (localDate.getYear()<=student.getEntranceYear()+5) {
                    return false;
                }
            }
//            case PHD ->
            default -> throw new IllegalArgumentException("Unsupported level type: " + student.getEducationLevel());
        }
        return true;
    }

    @Override
    public List<Object[]> findByPending(Loan loan) {
        return repository.findByPending(loan);
    }

    @Override
    public List<Object[]> findByPaid(Loan loan) {
        return repository.findByPaid(loan);
    }

    @Override
    public List<ProcessPayment> findByLoan(Integer loanId) {
        return repository.findByLoan(loanId);
    }

    @Override
    public void updateInstallmentStatus(Loan loan,int numberOfInstallment) {
        repository.updateInstallmentStatus(loan,numberOfInstallment);
    }
    public Date repaymentDate(Student student){
        int year;
        switch (student.getEducationLevel()) {
            case ASSOCIATE ,BACHELOR_DISCONTINUOUS , MASTER_DISCONTINUOUS -> year = student.getEntranceYear() + 2;
            case BACHELOR -> year = student.getEntranceYear() + 4;

//            case BACHELOR_DISCONTINUOUS ->
            case MASTER -> year = student.getEntranceYear() + 6;

//            case MASTER_DISCONTINUOUS ->
            case DOCTORATE , PHD-> year = student.getEntranceYear() + 5;

//            case PHD ->
            default -> throw new IllegalArgumentException("Unsupported level type: " + student.getEducationLevel());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }
}
