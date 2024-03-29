package org.example.utill;


import org.example.repository.*;
import org.example.repository.impl.*;
import org.example.service.*;
import org.example.service.Impl.*;


import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class ApplicationContext {
    private static final EntityManager entityManager =
            Persistence.createEntityManagerFactory(
                    "default"
            ).createEntityManager();
    private static final LoanRepository STUDENT_LOAN_REPOSITORY;
    private static final StudentRepository STUDENT_REPOSITORY;
    private static final ProcessPaymentRepository PROCESS_PAYMENT_REPOSITORY;

    private static final BankCardRepository BANK_CARD_REPOSITORY;

    private static final LoanService STUDENT_LOAN_SERVICE;
    private static final StudentService STUDENT_SERVICE;
    private static final ProcessPaymentService PROCESS_PAYMENT_SERVICE;

    private static final BankCardService BANK_CARD_SERVICE;

    static {
        STUDENT_LOAN_REPOSITORY = new LoanRepositoryImpl(entityManager);
        PROCESS_PAYMENT_REPOSITORY = new ProcessPaymentRepositoryImpl(entityManager);
        STUDENT_REPOSITORY = new StudentRepositoryImpl(entityManager);
        BANK_CARD_REPOSITORY = new BankCardRepositoryImpl(entityManager);
        STUDENT_SERVICE = new StudentServiceImpl(STUDENT_REPOSITORY);
        BANK_CARD_SERVICE = new BankCardServiceImpl(BANK_CARD_REPOSITORY);
        STUDENT_LOAN_SERVICE = new LoanServiceImpl(STUDENT_LOAN_REPOSITORY,STUDENT_SERVICE,BANK_CARD_SERVICE);
        PROCESS_PAYMENT_SERVICE = new ProcessPaymentServiceImpl(PROCESS_PAYMENT_REPOSITORY);

    }

    public static LoanService getStudentLoanService(){
        return STUDENT_LOAN_SERVICE;
    }
    public static StudentService getStudentService(){
        return STUDENT_SERVICE;
    }
    public static BankCardService getBankCardService(){
        return BANK_CARD_SERVICE;
    }
    public static ProcessPaymentService getProcessPaymentService(){
        return PROCESS_PAYMENT_SERVICE;
}

}
