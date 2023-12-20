package org.example.repository;

import org.example.base.repository.BaseRepository;
import org.example.entity.Loan;
import org.example.entity.enumuration.LoanAmountOfEducationLevel;
import org.example.entity.enumuration.LoanType;

import java.util.Collection;

public interface LoanRepository extends BaseRepository<Integer, Loan> {
    Loan findByNationalCodeLoanHousing(String nationalCode, LoanAmountOfEducationLevel educationLevel);
    Loan findByLoanType(String nationalCode, int year, LoanType loanType);
    Collection<Loan> findAllByNationalCode(String nationalCode);
    long findByBankCard(Loan loan,String cardNumber);
}
