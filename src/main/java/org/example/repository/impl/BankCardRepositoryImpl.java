package org.example.repository.impl;

import org.example.base.repository.BaseRepositoryImpl;
import org.example.entity.BankCard;

import org.example.repository.BankCardRepository;


import javax.persistence.EntityManager;
import javax.persistence.Query;

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
        double balance =- amount;
        Query query = entityManager.createQuery(
                "update from BankCard b SET b.balance= :balance where b.cardNumber= :cardNumber");
        query.setParameter("balance",balance);
        query.setParameter("cardNumber",cardNumber);

    }
}
