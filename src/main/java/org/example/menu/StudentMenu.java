package org.example.menu;

import org.example.entity.Student;
import org.example.entity.enumuration.LoanAmountOfEducationLevel;
import org.example.entity.enumuration.UniversityType;
import org.example.service.StudentService;
import org.example.service.dto.StudentRegistrationDTO;
import org.example.utill.ApplicationContext;
import org.example.utill.SecurityContext;
import org.example.utill.Validation;

import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentMenu {
    StudentService studentService = ApplicationContext.getStudentService();
    LoanMenu loanMenu = new LoanMenu();
    private final Scanner sc = new Scanner(System.in);
    String userName;
    String password;

    public void displayMenu(){
        System.out.println("******************Student management*******************");
        System.out.println("1.register your account");
        System.out.println("2.get user name and password");
        System.out.println("3.log in");
        System.out.println("4.back");
        System.out.println("enter your select");
    }
    public void runMenu(){
        int select;
        do {
            displayMenu();
            select = input();
            switch (select) {
                case 1 -> studentRegister();
                case 2 -> getPassword();
                case 3 -> studentLogIn();
                case 4 -> {
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.mainMenu();
                }
                default -> {
                    System.out.println("invalid select,enter a valid option");
                    runMenu();
                }
            }
        }while (select != 5);
        sc.close();
    }

    private void studentRegister()  {
        System.out.println("enter your first name");
        String firstName = sc.nextLine();
        while (!Validation.validateString(firstName)){
            System.out.println("empty field!!!");
            firstName = sc.nextLine();
        }
        System.out.println("enter your last name");
        String lastName = sc.nextLine();
        while (!Validation.validateString(lastName)){
            System.out.println("empty field!!!");
            lastName = sc.nextLine();
        }
        System.out.println("enter your father name");
        String fatherName = sc.nextLine();
        System.out.println("enter your mother name");
        String motherName = sc.nextLine();
        System.out.println("enter your identity number");
        String identityNumber = sc.nextLine();
        while (!Validation.validateString(identityNumber)){
            System.out.println("empty field!!!");
            identityNumber = sc.nextLine();
        }
        System.out.println("enter your national code");
        String nationalCode = sc.nextLine();
        while (!Validation.isValidNationalCodeAndPostalCodeWithRegex(nationalCode)){
            System.out.println("national code length in invalid");
            System.out.println("enter correct national code!!!!!");
            nationalCode = sc.nextLine();
        }
        System.out.println("enter your birth year");
        int year = input();
        while (year>3000 || year <1900){
            System.out.println("it dose not current year!!");
            year = input();
        }
        System.out.println("enter your birth month");
        int month = input();
        while (month>13 || month <=0){
            System.out.println("it dose not current month!!");
            month = input();
        }
        System.out.println("enter your birth day");
        int day = input();
        while (day>31 || day <0){
            System.out.println("it dose not current day!!");
            day = input();
        }
        Date birthDay = Validation.getDate(year, month, day);
        System.out.println("enter your student id");
        String studentId = sc.nextLine();
        while (!Validation.validateString(studentId)){
            System.out.println("empty field!!!");
            studentId = sc.nextLine();
        }
        System.out.println("enter your university name");
        String universityName = sc.nextLine();
        while (!Validation.validateString(universityName)){
            System.out.println("empty field!!!");
            universityName = sc.nextLine();
        }
        System.out.println("enter your university type");
        String uniTypes = """
                1- DOLLATI_ROOZANEH
                2- DOLLATI_SHABANEH
                3- DOLLATI_PARDIS
                4- AZAD
                5- PAYAM_NOOR
                6- ELMI_KARBORDI
                7- GHEIR_E_ENTEFAEE
                8- ZARFIAT_MAZAD
                """;
        System.out.println(uniTypes);
        int uniType = input();
        UniversityType type = null;
        switch (uniType) {
            case 1 -> type = UniversityType.DOLLATI_ROOZANEH;
            case 2 -> type = UniversityType.DOLLATI_SHABANEH;
            case 3 -> type = UniversityType.DOLLATI_PARDIS;
            case 4 -> type = UniversityType.AZAD;
            case 5 -> type = UniversityType.PAYAM_NOOR;
            case 6 -> type = UniversityType.ELMI_KARBORDI;
            case 7 -> type = UniversityType.GHEIR_E_ENTEFAEE;
            case 8 -> type = UniversityType.ZARFIAT_MAZAD;
            default -> {
                System.out.println("invalid input ! ");
                studentRegister();
            }
        }
        System.out.println("enter your entrance year");
        int entranceYear = input();
        while (entranceYear>3000 || entranceYear <1900){
            System.out.println("it dose not current year!!");
            entranceYear = input();
        }
        System.out.println("enter your educational level");
        String eduLevels = """
                1- ASSOCIATE
                2- BACHELOR
                3- BACHELOR_DISCONTINUOUS
                4- MASTER
                5- MASTER_DISCONTINUOUS
                6- DOCTORATE
                7- PHD
                """;
        System.out.println(eduLevels);
        int educationalLevel = input();
        LoanAmountOfEducationLevel level = null;
        switch (educationalLevel) {
            case 1 -> level = LoanAmountOfEducationLevel.ASSOCIATE;
            case 2 -> level = LoanAmountOfEducationLevel.BACHELOR;
            case 3 -> level = LoanAmountOfEducationLevel.BACHELOR_DISCONTINUOUS;
            case 4 -> level = LoanAmountOfEducationLevel.MASTER;
            case 5 -> level = LoanAmountOfEducationLevel.MASTER_DISCONTINUOUS;
            case 6 -> level = LoanAmountOfEducationLevel.DOCTORATE;
            case 7 -> level = LoanAmountOfEducationLevel.PHD;
            default -> {
                System.out.println("invalid input ! ");
                studentRegister();
            }
        }
        boolean isMarried = Validation.getBooleanInputWithValidation("enter your married state",sc);
        sc.nextLine();
        String spouseNationalCode = null;
        if (isMarried){
            System.out.println("enter your spouse national code");
            spouseNationalCode = sc.nextLine();
            while (!Validation.isValidNationalCodeAndPostalCodeWithRegex(spouseNationalCode)){
                System.out.println("national code length in invalid");
                System.out.println("enter correct national code!!!!!");
                spouseNationalCode = sc.nextLine();
            }
        }
        System.out.println("enter your city");
        String city = sc.nextLine();
        while (!Validation.validateString(city)){
            System.out.println("empty field!!!");
            city = sc.nextLine();
        }
        boolean stayInDorm = Validation.getBooleanInputWithValidation("enter your stay in dorm state",sc);
        int rental = 0;
        String address = null;
        String postalCode = null;
        if (!stayInDorm){
            System.out.println("enter your rental housing number");
            rental = input();
            System.out.println("enter your address");
            address = sc.nextLine();
            while (!Validation.validateString(address)){
                System.out.println("empty field!!!");
                address = sc.nextLine();
            }
            System.out.println("enter your postal code");
            postalCode = sc.nextLine();
            while (Validation.isValidNationalCodeAndPostalCodeWithRegex(postalCode)){
                System.out.println("postal code length in invalid");
                System.out.println("enter correct postal code!!!!!");
                postalCode = sc.nextLine();
            }

        }

        StudentRegistrationDTO dto =
                new StudentRegistrationDTO(
                        firstName,lastName,fatherName,motherName,identityNumber,nationalCode,birthDay,studentId,universityName,type,entranceYear,level,isMarried,spouseNationalCode,city,stayInDorm,rental,address,postalCode);
        String password = studentService.studentRegister(dto);
        System.out.println("************you register successfully***************");
        System.out.println("your password is " + password + " and userName is " + nationalCode);
        runMenu();
    }
    private void studentLogIn() {
        System.out.println("enter your user name");
        userName = sc.next();
        System.out.println("enter your password");
        password = sc.next();
        if (studentService.studentLogIn(userName,password)) {
            System.out.println("*************successfully logged in*****************");
            Student student = studentService.findByNationalCode(userName);
            SecurityContext.fillContext(student);
            setTime();
            loanMenu.studentLoanService();
        } else {
            System.out.println("invalid username and password");
            runMenu();
        }
    }
    public void setTime(){
        System.out.println("enter current year");
        int year = input();
        while (year>3000 || year <1900){
            System.out.println("it dose not current year!!");
            year = input();
        }
        System.out.println("enter current month");
        int month = input();
        while (month>13 || month <=0){
            System.out.println("it dose not current year!!");
            month = input();
        }
        System.out.println("enter current day");
        int day = input();
        while (day>32 || day <0){
            System.out.println("it dose not current year!!");
            day = input();
        }
        Calendar instance = Calendar.getInstance();
        instance.set(year, month, day);
        Date currentTime = instance.getTime();
        SecurityContext.fillContext(currentTime);
    }
    public void getPassword() {
        System.out.println("enter your national code");
        String nationalCode = sc.next();
        Student student = studentService.findByNationalCode(nationalCode);
        this.password = student.getPassword();
        System.out.println("your user name is: " + nationalCode + " and your password is: " + password);
        runMenu();
    }
    public Integer input() {
        int i;
        while (true) {
            try {
                i = sc.nextInt();
                sc.nextLine();
                return i;
            } catch (InputMismatchException in) {
                sc.nextLine();
                System.out.println("enter valid number !");
            }
        }
    }
}
