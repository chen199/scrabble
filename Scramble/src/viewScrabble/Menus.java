///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package viewScrabble;
//
//import java.util.Scanner;
//
///**
// *
// * @author USER
// */
//public class Menus {
//
//    String[] mainMenu;
//    String[] secondaryMenu;
//    Scanner scanner = new Scanner(System.in);
//
//    public String[] getMainMenu() {
//        return mainMenu;
//    }
//
//    public String[] getSecondaryMenu() {
//        return secondaryMenu;
//    }
//
//    public Menus() {
//        mainMenu = new String[3];
//        setMainMenu();
//        setSecondaryMenu();
//    }
//
//    private void setMainMenu() {
//        mainMenu[0] = "Please choose:";
//        mainMenu[1] = "1. start a new game";
//        mainMenu[2] = "2. load a game from external xml file";
//    }
//
//    private void setSecondaryMenu() {
//
//    }
//
//    public void printMenu(String[] menu) {
//       // System.out.println("please choose a valid option:");
//        for (String str : menu) {
//            System.out.println(str);
//        }
//    }
//
//    public int getInputFromMenu(String[] menu) {
//        int choice = 0;
//        boolean isValidInput = true;
//
//        do {
//            try{
//            if (!isValidInput) {
//                System.out.println("invalid option. Please enter a valid number between 1 to " + (menu.length-1));
//                printMenu(menu);
//            }
//            choice = scanner.nextInt();
//            isValidInput = (choice < menu.length && choice >= 1);
//            } catch (Exception ex ){
//                System.out.println("Invalid input");
//                isValidInput = false;
//                scanner.nextLine();
//            }
//        } while (!isValidInput);
//        return choice;
//    }
//
//}
