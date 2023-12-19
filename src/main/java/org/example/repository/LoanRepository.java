package org.example.repository;

import org.example.base.repository.BaseRepository;
import org.example.entity.Loan;
import org.example.entity.enumuration.LoanAmountOfEducationLevel;
import java.util.Collection;

public interface LoanRepository extends BaseRepository<Integer, Loan> {
    Loan findByNationalCodeLoanHousing(String nationalCode, LoanAmountOfEducationLevel educationLevel);
    Loan findByTuition(String nationalCode,String term);
    Loan findByEducational(String nationalCode,String term);
    Collection<Loan> findAllByNationalCode(String nationalCode);
    long findByBankCard(Loan loan,String cardNumber);
}
