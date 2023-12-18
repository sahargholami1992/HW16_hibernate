package org.example.entity;
import lombok.*;
import org.example.base.entity.BaseEntity;
import org.example.entity.enumuration.InstallmentStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class ProcessPayment extends BaseEntity<Integer> {

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Loan loan;
    private int numberOfInstallment;
    private Double installmentAmount;
    @Enumerated(EnumType.STRING)
    private InstallmentStatus installmentStatus;
    private double repaymentAmount;
    @Temporal(TemporalType.DATE)
    private Date repaymentDate;
    @Temporal(TemporalType.DATE)
    private Date dueDate;

}
