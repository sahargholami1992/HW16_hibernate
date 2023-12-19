package org.example.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    private final Scanner sc = new Scanner(System.in);

    public void mainMenu(){
        System.out.println("welcome to student loan system");
        System.out.println("enter your select");
        System.out.println("1. student loan system");
        System.out.println("2. exit");
        int choice;
        do {
            choice = input();
            sc.nextLine();
            switch (choice) {
                case 1 ->{
                    StudentMenu studentMenu = new StudentMenu();
                    studentMenu.runMenu();
                }
                case 2 -> System.out.println("exiting the program!! see you later!");
                default -> {
                    System.out.println("invalid select,enter a valid option");
                    mainMenu();
                }
            }
        }while (choice != 2);
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
                System.out.println(" enter valid number !");
            }
        }
    }
}
