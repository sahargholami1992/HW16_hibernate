package org.example.entity;

import lombok.*;
import org.example.base.entity.BaseEntity;
import org.example.entity.enumuration.BankName;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class BankCard extends BaseEntity<Integer> {
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Student student;
    @Column(nullable = false)
    private BankName bankName;
    @Column(unique = true)
    private String cardNumber;
    private int ccv2;
    private String CardExpirationDate;
    private double balance;

}
