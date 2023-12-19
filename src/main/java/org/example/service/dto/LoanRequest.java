package org.example.service.dto;

import lombok.*;
import org.example.entity.enumuration.LoanType;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class LoanRequest implements Serializable {
    private String nationalCode;
    private String cardNumber;
//    private int ccv2;
//    private String CardExpirationDate;
    private LoanType loanType;
    private String term;
//    private String city;
//    private int housingRentalNumber;
//    private String address;
//    private double amount;
}
