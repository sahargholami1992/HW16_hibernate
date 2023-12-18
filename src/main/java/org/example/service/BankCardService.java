package org.example.service;

import org.example.base.service.BaseService;
import org.example.entity.BankCard;


public interface BankCardService extends BaseService<Integer, BankCard> {
    void withdraw(double amount,String cardNumber);

    boolean isValidCardNumberWithRegex(String cardNumber);

    void registerBankCard(BankCard bankCard);

}
