package org.example.menu;

import org.example.entity.Student;
import org.example.entity.enumuration.LoanAmountOfEducationLevel;
import org.example.entity.enumuration.UniversityType;
import org.example.service.StudentService;
import org.example.service.dto.StudentRegistrationDTO;
import org.example.utill.ApplicationContext;
import org.example.utill.SecurityContext;
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
        System.out.println("Student management");
        System.out.println("1.register your account");
        System.out.println("2. get user name and password");
        System.out.println("3.log in");
        System.out.println("4.log out");
        System.out.println("enter your select");
    }
    public void runMenu(){
        int select;
        do {
            displayMenu();
            select = input();
            sc.nextLine();
            switch (select) {
                case 1 -> studentRegister();
                case 2 -> getPassword();
                case 3 -> studentLogIn();
                case 4 -> System.out.println("exiting the program!! see you later!");
                default -> {
                    System.out.println("invalid select,enter a valid option");
                    runMenu();
                }
            }
        }while (select != 4);
    }

    private void studentRegister()  {
        System.out.println("enter your first name");
        String firstName = sc.next();
        System.out.println("enter your last name");
        String lastName = sc.next();
        System.out.println("enter your father name");
        String fatherName = sc.next();
        System.out.println("enter your mother name");
        String motherName = sc.next();
        System.out.println("enter your identity number");
        String identityNumber = sc.next();
        System.out.println("enter your national code");
        String nationalCode = sc.next();
        while (!studentService.isValidNationalCodeWithRegex(nationalCode)){
            System.out.println("national code length in invalid");
            System.out.println("enter correct national code!!!!!");
            nationalCode = sc.next();
        }
        System.out.println("enter your birth year");
        int year = input();
        System.out.println("enter your birth month");
        int month = input();
        System.out.println("enter your birth day");
        int day = input();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date birthDay = calendar.getTime();
        System.out.println("enter your student id");
        String studentId = sc.next();
        System.out.println("enter your university name");
        String universityName = sc.next();
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
        System.out.println("enter your married state");
        boolean isMarried = sc.nextBoolean();
        System.out.println("enter your spouse national code");
        String spouseNationalCode = sc.next();
        while (!studentService.isValidNationalCodeWithRegex(spouseNationalCode)){
            System.out.println("password length in invalid");
            System.out.println("enter correct national code!!!!!");
            spouseNationalCode = sc.next();
        }
        System.out.println("enter your city");
        String city = sc.next();
        System.out.println("enter your stay in dorm state");
        boolean stayInDorm = sc.nextBoolean();
        System.out.println("enter your rental housing number");
        int rental = input();
        System.out.println("enter your address");
        String address = sc.next();
        System.out.println("enter your postal code");
        int postalCode = input();
        StudentRegistrationDTO dto =
                new StudentRegistrationDTO(
                        firstName,lastName,fatherName,motherName,identityNumber,nationalCode,birthDay,studentId,universityName,type,entranceYear,level,isMarried,spouseNationalCode,city,stayInDorm,rental,address,postalCode);
            studentService.studentRegister(dto);
            System.out.println("you register successfully");
            runMenu();
    }
    private void studentLogIn() {
        System.out.println("enter your user name");
        userName = sc.next();
        System.out.println("enter your password");
        password = sc.next();
        if (studentService.studentLogIn(userName,password)) {
            System.out.println("successfully logged in");
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
        System.out.println("enter current month");
        int month = input();
        System.out.println("enter current day");
        int day = input();
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
