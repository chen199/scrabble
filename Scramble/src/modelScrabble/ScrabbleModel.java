package modelScrabble;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ScrabbleModel {

    GameBoard _gameBoard;
    TilesList _tilesList;
    List<Player> _pList;
    int _numOfPlayers = 0;
    int _numOfHumanPlayers = 0;
    int _numOfComputerPlayers = 0;
    boolean isFirstWordOnBoard;

    public ScrabbleModel() {
        this._gameBoard = new GameBoard();
        this._tilesList = new TilesList();
        this._pList = new ArrayList<Player>();
        this.isFirstWordOnBoard = false;

     //   this.printList();
    }

    public boolean isIsFirstWordOnBoard() {
        return isFirstWordOnBoard;
    }

    public void setIsFirstWordOnBoard(boolean isFirstWordOnBoard) {
        this.isFirstWordOnBoard = isFirstWordOnBoard;
    }

    public TilesList getTilesList() {
        return _tilesList;
    }

    public int getNumOfHumanPlayers() {
        return _numOfHumanPlayers;
    }

    public int getNumOfComputerPlayers() {
        return _numOfComputerPlayers;
    }

    public int getNumOfPlayers() {
        return _numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this._numOfPlayers = numOfPlayers;
    }

    public void setNumOfHumanPlayers(int numOfHumanPlayers) {
        this._numOfHumanPlayers = numOfHumanPlayers;
    }

    public void setNumOfComputerPlayers(int numOfComputerPlayers) {
        this._numOfComputerPlayers = numOfComputerPlayers;
    }

    public GameBoard getGameBoard() {
        return _gameBoard;
    }

    public List<Player> getpList() {
        return _pList;
    }

    public void setPlayers() {
        for (int i = 1; i <= _numOfHumanPlayers; i++) {
            _pList.add(new PlayerHuman("HumanPlayer" + i));
        }
        for (int i = 0; i < _numOfComputerPlayers; i++) {
            _pList.add(new PlayerComputer("ComputerPlayer" + i));
        }

        for (Player player : _pList) {
            player.setLetters(this._tilesList.getTilesBag());
        }
    }

    public void printList() {
        int i = 1;
        LinkedList<Tile> l = _tilesList.getTilesBag();
        for (Tile l1 : l) {
            System.out.println("tile #" + i + " and the letter is " + l1.getLetter() + " and the valur is " + l1.getValue());
            i++;
        }
    }
}
