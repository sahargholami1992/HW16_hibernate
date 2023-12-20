package org.example.service;

import org.example.base.service.BaseService;
import org.example.entity.BankCard;
import org.example.entity.Student;

import java.util.List;


public interface BankCardService extends BaseService<Integer, BankCard> {
    void deposit(double amount,String cardNumber);

    void registerBankCard(BankCard bankCard);
    BankCard findByStudent(Student student,String cardNumber);

}
