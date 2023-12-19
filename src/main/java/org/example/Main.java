package org.example;

import org.example.entity.Student;
import org.example.menu.MainMenu;
import org.example.menu.StudentMenu;
import org.example.service.ProcessPaymentService;
import org.example.service.StudentService;
import org.example.utill.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        StudentMenu studentMenu = new StudentMenu();
//        studentMenu.runMenu();
        MainMenu menu = new MainMenu();
        menu.mainMenu();
//        ProcessPaymentService service = ApplicationContext.getProcessPaymentService();

    }
}