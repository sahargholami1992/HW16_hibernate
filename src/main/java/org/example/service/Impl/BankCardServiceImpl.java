package org.example.service.Impl;

import org.example.base.service.BaseServiceImpl;
import org.example.entity.BankCard;
import org.example.entity.Student;
import org.example.repository.BankCardRepository;
import org.example.service.BankCardService;

import java.util.List;
import java.util.regex.Pattern;


public class BankCardServiceImpl extends BaseServiceImpl<Integer, BankCard, BankCardRepository>
                               implements BankCardService {

    public BankCardServiceImpl(BankCardRepository repository) {
        super(repository);
    }

    @Override
    public void deposit(double amount,String cardNumber) {
        repository.update(amount,cardNumber);
    }

    @Override
    public void registerBankCard(BankCard bankCard) {
        repository.saveOrUpdate(bankCard);
    }

    @Override
    public BankCard findByStudent(Student student,String cardNumber) {
        return repository.findByStudent(student,cardNumber );
    }
}
