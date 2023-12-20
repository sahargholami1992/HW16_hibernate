package org.example.service;

import org.example.base.service.BaseService;
import org.example.entity.BankCard;
import org.example.entity.Loan;
import org.example.entity.Student;
import org.example.service.dto.LoanRequest;
import java.util.Collection;


public interface LoanService extends BaseService<Integer, Loan> {
    Collection<Loan> findAllByNationalCode(String nationalCode);
    void applyLoan(LoanRequest loanRequest);
    long findByBankCard(Loan loan,String cardNumber);
    BankCard extracted(Student student);

}
