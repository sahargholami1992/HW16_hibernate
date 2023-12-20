package org.example.repository.impl;

import org.example.base.repository.BaseRepositoryImpl;
import org.example.entity.BankCard;
import org.example.entity.Student;
import org.example.repository.BankCardRepository;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;



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

    @Override
    public boolean findByCcv2AndDate(String cardNumber, int ccv2) {
        try {
            TypedQuery<Long> query =
                    entityManager.createQuery(
                    "select count(b) from BankCard b where b.cardNumber = :cardNumber  and b.ccv2 = :ccv2 "
                    , Long.class);
            query.setParameter("cardNumber",cardNumber);
            query.setParameter("ccv2",ccv2);
            return query.getSingleResult()>0;
        }catch (Exception e){
            return false;
        }
    }

}
