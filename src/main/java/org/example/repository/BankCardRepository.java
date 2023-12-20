package org.example.repository;

import org.example.base.repository.BaseRepository;
import org.example.entity.BankCard;
import org.example.entity.Student;

public interface BankCardRepository extends BaseRepository<Integer, BankCard> {
    void update(double amount,String cardNumber);
    BankCard findByStudent(Student student,String cardNumber);
    boolean findByCcv2AndDate(String cardNumber, int ccv2);
}
