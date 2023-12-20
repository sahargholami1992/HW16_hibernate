package org.example.utill;

import org.apache.commons.lang3.StringUtils;
import org.example.entity.Student;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validation {
    public static boolean isValidNationalCodeAndPostalCodeWithRegex(String nationalCode) {
        Pattern pattern =
                Pattern.compile("^[0-9]{10}$");
        return nationalCode.matches(pattern.pattern());
    }
    public static String generatePassword() {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&])(?=\\S+$).{8,}$";

        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder();

        while (!password.toString().matches(passwordPattern)) {
            password.setLength(0);
            for (int i = 0; i < 12; i++) {
                char randomChar = (char) (random.nextInt(94) + 33);
                password.append(randomChar);
            }
        }
        return password.toString();
    }
    public static boolean activationRepayment(Student student, Date currentDate){
        LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        switch (student.getEducationLevel()) {
            case ASSOCIATE ,BACHELOR_DISCONTINUOUS , MASTER_DISCONTINUOUS -> {
                if (localDate.getYear()<=student.getEntranceYear()+2){
                    return false;
                }
            }
            case BACHELOR -> {
                if (localDate.getYear()<=student.getEntranceYear()+4) {
                    return false;
                }
            }
//            case BACHELOR_DISCONTINUOUS ->
            case MASTER -> {
                if (localDate.getYear()<=student.getEntranceYear()+6) {
                    return false;
                }
            }
//            case MASTER_DISCONTINUOUS ->
            case DOCTORATE , PHD-> {
                if (localDate.getYear()<=student.getEntranceYear()+5) {
                    return false;
                }
            }
//            case PHD ->
            default -> throw new IllegalArgumentException("Unsupported level type: " + student.getEducationLevel());
        }
        return true;
    }
    public static boolean isValidDateToGetLoan(int year,Date currentTime) {
        Calendar instance = Calendar.getInstance();
        instance.set(year,10,23);
        Date date1 = instance.getTime();
        instance.add(Calendar.DATE,7);
        Date date2 = instance.getTime();
        instance.set(year,2,14);
        Date date3 = instance.getTime();
        instance.add(Calendar.DATE,7);
        Date date4 = instance.getTime();
        if (isDateWithinRange(currentTime, date1, date2)) {
            System.out.println("The target date is within the range part1.");
            return true;
        }
        else if (isDateWithinRange(currentTime, date3, date4)){
            System.out.println("The target date is within the range part 2.");
            return true;
        }
        else{
            System.out.println("The target date is outside the range.");
            return false;
        }
    }
    private static boolean isDateWithinRange(Date targetDate, Date startDate, Date endDate) {
        return !(targetDate.before(startDate) || targetDate.after(endDate));
    }

    public static boolean isValidCardNumberWithRegex(String cardNumber) {
        Pattern pattern =
                Pattern.compile("^[0-9]{16}$");
        return cardNumber.matches(pattern.pattern());
    }

    public static boolean validateString(String string) {
        return !StringUtils.isBlank(string);
    }
    public static boolean getBooleanInputWithValidation(String prompt, Scanner scanner) {
        boolean validInput = false;
        boolean userInput = false;

        while (!validInput) {
            System.out.print(prompt + " (true/false): ");

            try {
                userInput = scanner.nextBoolean();
                validInput = true; // If no exception is thrown, the input is valid
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter 'true' or 'false'.");
                scanner.nextLine(); // Consume the invalid input to avoid an infinite loop
            }
        }

        return userInput;
    }

    public static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }
    public static boolean activationLoanRequest(Student student,Date currentDate){
        LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        switch (student.getEducationLevel()) {
            case ASSOCIATE ,BACHELOR_DISCONTINUOUS , MASTER_DISCONTINUOUS -> {
                if (localDate.getYear()>=student.getEntranceYear()+2){
                    return false;
                }
            }
            case BACHELOR -> {
                if (localDate.getYear()>=student.getEntranceYear()+4) {
                    return false;
                }
            }
//            case BACHELOR_DISCONTINUOUS ->
            case MASTER -> {
                if (localDate.getYear()>=student.getEntranceYear()+6) {
                    return false;
                }
            }
//            case MASTER_DISCONTINUOUS ->
            case DOCTORATE , PHD-> {
                if (localDate.getYear()>=student.getEntranceYear()+5) {
                    return false;
                }
            }
//            case PHD ->
            default -> throw new IllegalArgumentException("Unsupported level type: " + student.getEducationLevel());
        }
        return true;
    }

    public static String generatePasswordddd() {
        Random random = new Random();
        String validCharacters = "@#$%&abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String password;
        do {
            StringBuilder passwordBuilder = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                int randomIndex = random.nextInt(validCharacters.length());
                passwordBuilder.append(validCharacters.charAt(randomIndex));
            }
            password = passwordBuilder.toString();
        } while (!isValidPassword(password));

        return password;
    }

    private static boolean isValidPassword(String password) {
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        if (!password.matches(".*[@#$%&].*")) {
            return false;
        }

        if (!password.matches(".*\\d.*")) {
            return false;
        }

        return true;
    }

}
