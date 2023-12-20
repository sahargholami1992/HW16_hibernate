package org.example.entity;

import lombok.*;
import org.example.base.entity.BaseEntity;
import org.example.entity.enumuration.EducationLevel;
import org.example.entity.enumuration.LoanAmountOfEducationLevel;
import org.example.entity.enumuration.LoanType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Loan extends BaseEntity<Integer> {
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Student student;
    @ToString.Exclude
    @OneToMany(mappedBy = "loan")
    private List<ProcessPayment> paymentList;
    @Enumerated(EnumType.STRING)
    private LoanType loanType;
//    private boolean accepted;
    private String city;
    private double amount;
    @Enumerated(EnumType.STRING)
    private LoanAmountOfEducationLevel educationLevel;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private BankCard bankCard;
    private int housingRentalNumber;
    private String address;
//    private String term;
    private int loanGetYear;

    @Override
    public String toString() {
        return "Loan{" +
                "id = "+getId()+
                " loanType=" + loanType +
                ", city='" + city + '\'' +
                ", amount=" + amount +
                ", educationLevel=" + educationLevel +
                ", housingRentalNumber=" + housingRentalNumber +
                ", address='" + address + '\'' +
                "} ";
    }
}
