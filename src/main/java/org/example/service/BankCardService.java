package org.example.service;

import org.example.base.service.BaseService;
import org.example.entity.BankCard;
import org.example.entity.Student;



public interface BankCardService extends BaseService<Integer, BankCard> {
    void deposit(double amount,String cardNumber);
    void registerBankCard(BankCard bankCard);
    BankCard findByStudent(Student student,String cardNumber);
    boolean findByCcv2AndDate(String cardNumber,int ccv2);

}
