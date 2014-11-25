/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewScrabble;

import java.util.LinkedList;
import java.util.Scanner;
import modelScrabble.BoardSquare;
import modelScrabble.Position;
import modelScrabble.Player;
import modelScrabble.Tile;

public class ScrabbleView {

    Scanner scanner = new Scanner(System.in);
    Menu _mainMenu;
    Menu _numOfPlayersMenu;
    Menu _numOfHumanPlayersMenu;
    Menu _turnMenu;
    Menu directionmMenu;

    public Menu getDirectionmMenu() {
        return directionmMenu;
    }

    public Menu getTurnMenu() {
        return _turnMenu;
    }

    public Menu getNumOfHumanPlayersMenu() {
        return _numOfHumanPlayersMenu;
    }

    public Menu getMainMenu() {
        return _mainMenu;
    }

    public Menu getNumOfPlayersMenu() {
        return _numOfPlayersMenu;
    }

    public ScrabbleView() {
        setMainMenu();
        setGetNumOfPlayers();
        setTurnMenu();
    }

    private void setMainMenu() {
        String[] args = {
            "Please choose a valid option",
            "1. Start a new game",
            "2. Load a game from xml file"
        };
        _mainMenu = new Menu(1, 2, args);
    }

    private void setGetNumOfPlayers() {
        String[] args = {"Please choose a number of players between " + 2 + " to " + 4};
        _numOfPlayersMenu = new Menu(2, 4, args);
    }

    public void printBoard(BoardSquare[][] board) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 18; j++) {
                if (board[i][j].getSign() == '2' && j != 0 && j != 1) {
                    System.out.print("\u001B[36m" + board[i][j].getSign());
                } else if (board[i][j].getSign() == '3' && j != 0 && j != 1) {
                    System.out.print("\u001B[34m" + board[i][j].getSign());
                } else if (board[i][j].getSign() == '@') {
                    System.out.print("\u001B[35m" + board[i][j].getSign());
                } else if (board[i][j].getSign() == '#') {
                    System.out.print("\u001B[31m" + board[i][j].getSign());
                } else {
                    System.out.print("\u001B[30m" + board[i][j].getSign());
                }
            }
            System.out.println("");
        }
    }

    public int getInputFromUser(Menu menu) {

        boolean isValid = true;
        int choice = 0;
        String[] args = menu.getMenu();
        do {
            try {
                for (String arg : args) {
                    System.out.println(arg);
                }
                choice = scanner.nextInt();
                isValid = (choice >= menu.getMinVal() && choice <= menu.getMaxVal());
            } catch (Exception e) {
                System.out.println("Invalid Input");
                scanner.nextLine();
                isValid = false;
            }
        } while (!isValid);

        return choice;
    }

    public void setNumOfHumanPlayersMenu(int minHuman, int maxHuman) {
        _numOfHumanPlayersMenu = new Menu(
                minHuman,
                maxHuman,
                "Please enter a number of human players, between " + minHuman + " to " + maxHuman);
    }

    private void setTurnMenu() {
        String args[] = {
            "Select your move",
            "1. skip turn",
            "2. switch a letter",
            "3. create a word",
            "4. quit"
        };
        _turnMenu = new Menu(1, args.length - 1, args);
    }

    public void setDirectionmMenu() {
        String args[] = {
            "please choose direction",
            "Horizontal",
            "Vetical"
        };
        this.directionmMenu = new Menu(1, 2, args);
    }

    public int getHumanPlayerTurnMenuMove(Player player) {
        String word = null;
        System.out.println("your letters are: ");
        for (Tile tile : player.getTilessList()) {
            System.out.print(tile.getLetter() + "(" + tile.getValue() + ") ");
        }
        System.out.println("");
        return getInputFromUser(_turnMenu);

    }

    private boolean isValidWord() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getLetterFromUser(LinkedList<Tile> list) {
        boolean isLegalInput = true;
        int userSelection;
        do {
            System.out.println("please choose a letter. press 9 to confirm");
            scanner.nextLine();
            for (int i = 0; i < list.size(); i++) {
                System.out.print(i+". "+list.get(i).getLetter()+" | ");
            }
            userSelection = scanner.nextInt();
            if(userSelection < 0 || userSelection > list.size()){
                isLegalInput = false;
            }
            if(userSelection == 9){
                isLegalInput = true;
            }
        } while (!isLegalInput);
        return userSelection;
    }

    public String getLetterPositionInput() {
        boolean isLegalInput = true;
        scanner.nextLine();
        System.out.println("enter a position between A1 to O15");
        String input = scanner.next();
        return input;
    }
    
    public void printMessage(String str){
        System.out.println(str);
    }

}
