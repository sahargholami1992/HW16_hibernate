package org.example.utill;

import org.example.entity.Student;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Validation {
    public static boolean isValidNationalCodeWithRegex(String nationalCode) {
        Pattern pattern =
                Pattern.compile("^[0-9]{10}$");
        return nationalCode.matches(pattern.pattern());
    }
    public static String generatePassword() {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&])(?=.*\\\\d).{8,}$";

        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder();

        while (!password.toString().matches(passwordPattern)) {
            password.setLength(0);  // Clear the StringBuilder
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


}
