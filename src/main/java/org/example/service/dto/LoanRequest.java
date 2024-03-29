package org.example.service.dto;

import lombok.*;
import org.example.entity.enumuration.LoanType;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class LoanRequest implements Serializable {
    private String nationalCode;
    private String cardNumber;
    private LoanType loanType;
    private Date currentTime;
    private int year;

}
