package org.example.repository;

import org.example.base.repository.BaseRepository;
import org.example.entity.BankCard;


public interface BankCardRepository extends BaseRepository<Integer, BankCard> {
    void update(double amount,String cardNumber);
}
