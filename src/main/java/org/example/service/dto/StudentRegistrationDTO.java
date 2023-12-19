package org.example.service.dto;

import lombok.*;
import org.example.entity.enumuration.LoanAmountOfEducationLevel;
import org.example.entity.enumuration.UniversityType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class StudentRegistrationDTO implements Serializable {


    private String firstName;
    private String lastName;
    private String fatherName;
    private String motherName;
    private String identityNumber;
    @Pattern(regexp = "^[0-9]{10}$")
    private String nationalCode;
    private Date birthDate;
    private String studentId;
    private String universityName;
    @Enumerated(EnumType.STRING)
    private UniversityType universityType;
    private int entranceYear;
    private String currentTerm;
    @Enumerated(EnumType.STRING)
    private LoanAmountOfEducationLevel educationLevel;
    private boolean isMarried;
    @Pattern(regexp = "^[0-9]{10}$")
    private String spouseNationalCode;
    private String city;
    private boolean stayInDorm;
    private int housingRentalNumber;
    private String address;
    private int postalCode;



}
