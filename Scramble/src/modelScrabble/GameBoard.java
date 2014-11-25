package modelScrabble;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import jdk.nashorn.internal.ir.ForNode;

public class GameBoard {

    final int TOPLEFTROW = 2;
    final int TOPRIGHTROW = 18;
    final int TOPLEFTCOL = 1;
    final int BOTTOMLEFTCOL = 16;

    BoardSquare[][] board;

    //TODO: CHANGE THE GAP OF THE MAIN ROW!!!!!!
    public GameBoard() {
        board = new BoardSquare[16][18]; //lines and cols 
        this.setBoard();
    }

    public BoardSquare[][] getBoard() {
        return board;
    }

    private void setBoard() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 18; j++) {
                board[i][j] = new BoardSquare(' ', 1);             
            }
        }

        //setting up the head row!
        for (int i = 3; i < 18; i++) {
            char c = 'A';
            c += i - 3;
            board[0][i] = new BoardSquare(c, 0);
        }
        //setting up the head column!
        for (int i = 1; i < 16; i++) {
            if (i < 10) {
                char c = 48;
                c += i;
                board[i][0] = new BoardSquare((char) c, 0);

            } else {
                board[i][0] = new BoardSquare('1', 0);
                //board[i][0] = '1';
                char digitchar = 59;
                digitchar -= (21 - i);
                board[i][1] = new BoardSquare(digitchar, 0);
            }
        }

        //setting up the Hashes!
        for (int i = 1; i < 16; i += 7) { //row
            for (int j = 3; j < 18; j += 7) {  //col
                board[i][j] = new BoardSquare('#', 3);
                //board[i][j] = '#';
            }
        }

        //setting up the Strudellll!
        for (int i = 2; i < 16; i++) {
            for (int j = 4; j < 18; j++) {
                if (j - i == 2 || (20 - i) - j == 2) {
                    board[i][j] = new BoardSquare('@', 2);
                }
            }
        }

        //setting up the 2's and the 3's!
        for (int i = 1; i < 16; i++) { //ROW
            for (int j = 3; j < 18; j++) { //COL
                switch (i) {
                    case 1: {
                        if (j - i == 5) {
                            board[i][j] = new BoardSquare('2', 2);
                        }
                        if (17 - j == 3) {
                            board[i][17 - (17 - j)] = new BoardSquare('2', 2);
                        }
                    }
                    break;
                    case 2: {
                        board[i][8] = new BoardSquare('3', 3);
                        board[i][12] = new BoardSquare('3', 3);
                    }
                    break;
                    case 3: {
                        board[i][9] = new BoardSquare('2', 2);;
                        board[i][11] = new BoardSquare('2', 2);;
                    }
                    break;
                    case 4: {
                        board[i][3] = new BoardSquare('2', 2);
                        board[i][10] = new BoardSquare('2', 2);
                        board[i][17] = new BoardSquare('2', 2);
                    }
                    break;
                    case 6: {
                        board[i][4] = new BoardSquare('3', 3);
                        board[i][8] = new BoardSquare('3', 3);
                        board[i][12] = new BoardSquare('3', 3);
                        board[i][16] = new BoardSquare('3', 3);
                    }
                    break;
                    case 7: {
                        board[i][5] = new BoardSquare('2', 2);
                        board[i][9] = new BoardSquare('2', 2);
                        board[i][11] = new BoardSquare('2', 2);
                        board[i][15] = new BoardSquare('2', 2);
                    }
                    break;
                    case 8: {
                        board[i][6] = new BoardSquare('2', 2);
                        board[i][14] = new BoardSquare('2', 2);
                    }
                    break;
                    case 9: {
                        board[i][5] = new BoardSquare('2', 2);
                        board[i][9] = new BoardSquare('2', 2);
                        board[i][11] = new BoardSquare('2', 2);
                        board[i][15] = new BoardSquare('2', 2);
                    }
                    break;
                    case 10: {
                        board[i][4] = new BoardSquare('3', 3);
                        board[i][8] = new BoardSquare('3', 3);
                        board[i][12] = new BoardSquare('3', 3);
                        board[i][16] = new BoardSquare('3', 3);
                    }
                    break;
                    case 12: {
                        board[i][3] = new BoardSquare('2', 2);
                        board[i][10] = new BoardSquare('2', 2);
                        board[i][17] = new BoardSquare('2', 2);
                    }
                    break;
                    case 13: {
                        board[i][9] = new BoardSquare('3', 3);
                        board[i][11] = new BoardSquare('3', 3);
                    }
                    break;
                    case 14: {
                        board[i][8] = new BoardSquare('2', 2);
                        board[i][12] = new BoardSquare('2', 2);
                    }
                    break;
                    case 15: {
                        board[i][6] = new BoardSquare('2', 2);
                        board[i][14] = new BoardSquare('2', 2);
                    }
                    break;
                }
            }
        }
    }
}
