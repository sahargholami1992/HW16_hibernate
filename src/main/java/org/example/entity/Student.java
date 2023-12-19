package org.example.entity;

import lombok.*;
import org.example.base.entity.BaseEntity;

import org.example.entity.enumuration.LoanAmountOfEducationLevel;
import org.example.entity.enumuration.UniversityType;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Student extends BaseEntity<Integer> {
    private String firstName;
    private String lastName;
    private String fatherName;
    private String motherName;
    private String identityNumber;
    @Pattern(regexp = "^[0-9]{10}$",message = "invalid pattern")
    @Column(unique = true)
    private String nationalCode;
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    private String studentId;
    private String universityName;
    @Enumerated(EnumType.STRING)
    private UniversityType universityType;
    private int entranceYear;
    @Enumerated(EnumType.STRING)
    private LoanAmountOfEducationLevel educationLevel;
    private boolean isMarried;
    @Pattern(regexp = "^[0-9]{10}$",message = "invalid pattern")
    @Column(unique = true)
    private String spouseNationalCode;
    private String city;
    private boolean stayInDorm;
    private int housingRentalNumber;
    private String address;
    private int postalCode;
    private String password;
    @ToString.Exclude
    @OneToMany(mappedBy = "student")
    private List<BankCard> bankCardList;
    @ToString.Exclude
    @OneToMany(mappedBy = "student")
    private List<Loan> loanList;
}
