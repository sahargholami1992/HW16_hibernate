package org.example.service.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class RepaymentRequest implements Serializable {
    private Integer loanId;
    private double repaymentAmount;
    private Date repaymentDate;
    private int numberOfInstallments;
}
