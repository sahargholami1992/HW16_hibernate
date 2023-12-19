package org.example.repository.impl;

import org.example.base.repository.BaseRepositoryImpl;
import org.example.entity.Loan;
import org.example.entity.ProcessPayment;
import org.example.entity.enumuration.InstallmentStatus;
import org.example.repository.ProcessPaymentRepository;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class ProcessPaymentRepositoryImpl extends BaseRepositoryImpl<Integer, ProcessPayment>
                                 implements ProcessPaymentRepository {


    public ProcessPaymentRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<ProcessPayment> getEntityClass() {
        return ProcessPayment.class;
    }

    @Override
    public void saveAll(List<ProcessPayment> installments) {
        for (ProcessPayment installment : installments) {
            beginTransaction();
            entityManager.persist(installment);
            commitTransaction();
        }
    }

    @Override
    public List<Object[]> findByPending(Loan loan) {
        try {
            TypedQuery<Object[]> query = entityManager.createQuery(
                    "select p.numberOfInstallment,p.dueDate,p.installmentAmount from ProcessPayment p where p.loan = :loan and p.installmentStatus= :installmentStatus ",
                    Object[].class);
            query.setParameter("loan",loan);
            query.setParameter("installmentStatus",InstallmentStatus.PENDING);
            List<Object[]> results = query.getResultList();
            for (Object[] result : results) {
                int numberOfInstallment = (int) result[0];
                Date dueDate = (Date) result[1];
                double installmentAmount = (double) result[2];
            }
            return results;
        }catch (Exception e){
            return null;
        }
    }
    @Override
    public List<Object[]> findByPaid(Loan loan) {
        try {
            TypedQuery<Object[]> query = entityManager.createQuery(
                    "select p.numberOfInstallment,p.dueDate from ProcessPayment p where p.loan = :loan and p.installmentStatus= :installmentStatus ",
                    Object[].class);
            query.setParameter("loan",loan);
            query.setParameter("installmentStatus",InstallmentStatus.PAID);
            List<Object[]> results = query.getResultList();
            for (Object[] result : results) {
                int numberOfInstallment = (int) result[0];
                Date dueDate = (Date) result[1];
            }
            return results;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<ProcessPayment> findByLoan(Integer id) {
        try {
            TypedQuery<ProcessPayment> query = entityManager.createQuery(
                    "select p from ProcessPayment p where p.loan.id = :id ",
                    ProcessPayment.class);
            query.setParameter("id",id);
            return query.getResultList();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void updateInstallmentStatus(Loan loan, int numberOfInstallment) {
        beginTransaction();
        Query query = entityManager.createQuery("update ProcessPayment p set p.installmentStatus= :installmentStatus where p.loan= :loan and p.numberOfInstallment= :numberOfInstallment");
        query.setParameter("installmentStatus",InstallmentStatus.PAID);
        query.setParameter("loan",loan);
        query.setParameter("numberOfInstallment",numberOfInstallment);
        query.executeUpdate();
        commitTransaction();
    }
}
