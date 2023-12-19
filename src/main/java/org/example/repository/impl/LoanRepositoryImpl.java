package org.example.repository.impl;

import org.example.base.repository.BaseRepositoryImpl;
import org.example.entity.BankCard;
import org.example.entity.Loan;
import org.example.entity.enumuration.LoanAmountOfEducationLevel;
import org.example.entity.enumuration.LoanType;
import org.example.repository.LoanRepository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

public class LoanRepositoryImpl extends BaseRepositoryImpl<Integer, Loan>
                                 implements LoanRepository {


    public LoanRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Loan> getEntityClass() {
        return Loan.class;
    }

    @Override
    public Loan findByNationalCodeLoanHousing(String nationalCode, LoanAmountOfEducationLevel educationLevel) {
        try {
            TypedQuery<Loan> query = entityManager.createQuery(
                    "select l from Loan l where l.student.nationalCode = :nationalCode and l.loanType = :loanType AND l.educationLevel= :educationLevel",
                    Loan.class);
            query.setParameter("nationalCode",nationalCode);
            query.setParameter("loanType",LoanType.HOUSING_DEPOSIT);
            query.setParameter("educationLevel",educationLevel);
            return query.getSingleResult();
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public Loan findByTuition(String nationalCode,String term) {
        try {
            TypedQuery<Loan> query = entityManager.createQuery(
                    "select l from Loan l where l.student.nationalCode = :nationalCode and l.loanType = 'TUITION' and l.student.currentTerm = :term ",
                    Loan.class);
            query.setParameter("nationalCode",nationalCode);
            query.setParameter("term",term);
            return query.getSingleResult();
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public Loan findByEducational(String nationalCode,String term) {
        try {
            TypedQuery<Loan> query = entityManager.createQuery(
                    "select l from Loan l where l.student.nationalCode = :nationalCode and l.loanType = 'EDUCATIONAL' and l.student.currentTerm = :term",
                    Loan.class);
            query.setParameter("nationalCode",nationalCode);
            query.setParameter("term",term);
            return query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Collection<Loan> findAllByNationalCode(String nationalCode) {
        try {
            TypedQuery<Loan> query = entityManager.createQuery(
                    "select l from Loan l where l.student.nationalCode = :nationalCode",
                    Loan.class);
            query.setParameter("nationalCode",nationalCode);
            return query.getResultList();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public long findByBankCard(Loan loan,String cardNumber) {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "select count(l) from Loan l where l.bankCard.cardNumber = :cardNumber and l = :loan ",
                    Long.class);
            query.setParameter("cardNumber",cardNumber);
            query.setParameter("loan",loan);
            return query.getSingleResult();
        }catch (Exception e){
            return 0;
        }
    }
}
