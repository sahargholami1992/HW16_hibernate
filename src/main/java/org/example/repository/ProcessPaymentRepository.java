package org.example.repository;

import org.example.base.repository.BaseRepository;
import org.example.entity.Loan;
import org.example.entity.ProcessPayment;
import org.example.entity.enumuration.InstallmentStatus;

import java.util.List;


public interface ProcessPaymentRepository extends BaseRepository<Integer, ProcessPayment> {
    void saveAll(List<ProcessPayment> installments);
    List<Object[]> findByPending(Loan loan);
    List<Object[]> findByPaid(Loan loan);
    List<ProcessPayment> findByLoan(Integer loanId);

    void updateInstallmentStatus(Loan loan,int numberOfInstallment);

}
