package org.example.utill;


import lombok.Getter;
import org.example.entity.Loan;
import org.example.entity.Student;

public class SecurityContext {

    private SecurityContext() {
    }

    @Getter
    private static Student currentStudent;
    @Getter
    private static Loan currentLoan;

    public static void fillContext(Student student) {
        currentStudent = student;
    }
    public static void fillContext(Loan loan) {
        currentLoan = loan;
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