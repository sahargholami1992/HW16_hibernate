package org.example.repository.impl;

import org.example.base.repository.BaseRepositoryImpl;
import org.example.entity.BankCard;

import org.example.entity.Student;
import org.example.repository.BankCardRepository;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class BankCardRepositoryImpl extends BaseRepositoryImpl<Integer, BankCard>
                                 implements BankCardRepository {


    public BankCardRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<BankCard> getEntityClass() {
        return BankCard.class;
    }

    @Override
    public void update(double amount,String cardNumber) {
        double balance =+ amount;
        beginTransaction();
        Query query = entityManager.createQuery(
                "update from BankCard b SET b.balance= :balance where b.cardNumber= :cardNumber");
        query.setParameter("balance",balance);
        query.setParameter("cardNumber",cardNumber);
        query.executeUpdate();
        commitTransaction();

    }

    @Override
    public BankCard findByStudent(Student student, String cardNumber) {
        try {
            TypedQuery<BankCard> query = entityManager.createQuery(
                    "select b from BankCard b where b.student = :student and b.cardNumber = :cardNumber ", BankCard.class);
            query.setParameter("student",student);
            query.setParameter("cardNumber",cardNumber);
            return query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }
}
