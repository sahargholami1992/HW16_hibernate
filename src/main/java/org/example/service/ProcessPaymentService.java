package org.example.service;

import org.example.base.service.BaseService;
import org.example.entity.Loan;
import org.example.entity.ProcessPayment;
import org.example.entity.Student;
import org.example.entity.enumuration.InstallmentStatus;

import java.util.Date;
import java.util.List;


public interface ProcessPaymentService extends BaseService<Integer, ProcessPayment> {
    List<ProcessPayment> generateInstallment(Loan loan);
    List<Object[]> findByPending(Loan loan);
    List<Object[]> findByPaid(Loan loan);
    List<ProcessPayment> findByLoan(Integer loanId);
    void updateInstallmentStatus(Loan loan,int numberOfInstallment);
    Date repaymentDate(Student student);
}
