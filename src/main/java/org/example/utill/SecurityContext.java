package org.example.utill;


import lombok.Getter;
import org.example.entity.Loan;
import org.example.entity.Student;

import java.util.Date;

public class SecurityContext {

    private SecurityContext() {
    }

    @Getter
    private static Student currentStudent;
    @Getter
    private static Loan currentLoan;
    @Getter
    private static Date currentTime;

    public static void fillContext(Student student) {
        currentStudent = student;
    }
    public static void fillContext(Loan loan) {
        currentLoan = loan;
    }
    public static void fillContext(Date date) {
        currentTime = date;
    }

    public static void logout() {
        currentStudent = null;
    }


    public static boolean isAnyoneAuthenticated() {
        return currentStudent != null;
    }

    public static Integer getCurrentUserId() {
        return currentStudent.getId();
    }

//    public static String getCurrentUserType() {
//        return currentUser.getUserType();
//    }

}
