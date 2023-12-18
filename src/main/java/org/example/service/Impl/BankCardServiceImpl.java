package org.example.service.Impl;

import org.example.base.service.BaseServiceImpl;
import org.example.entity.BankCard;
import org.example.repository.BankCardRepository;
import org.example.service.BankCardService;

import java.util.regex.Pattern;


public class BankCardServiceImpl extends BaseServiceImpl<Integer, BankCard, BankCardRepository>
                               implements BankCardService {

    public BankCardServiceImpl(BankCardRepository repository) {
        super(repository);
    }

    @Override
    public void withdraw(double amount,String cardNumber) {
        repository.update(amount,cardNumber);
    }

    @Override
    public boolean isValidCardNumberWithRegex(String cardNumber) {
        Pattern pattern =
                Pattern.compile("^[0-9]{16}$");
        return cardNumber.matches(pattern.pattern());
    }

    @Override
    public void registerBankCard(BankCard bankCard) {
        repository.saveOrUpdate(bankCard);
    }
}
