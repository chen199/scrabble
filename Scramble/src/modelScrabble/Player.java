package modelScrabble;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class Player {


    private String _name;
    private String playerCurrentWord;
    private List<Tile> playerCurrntWordTile;
    private int    score;
    private int     missingTilesCount;
    private LinkedList<Tile> _tilessList;
    private Random _randomGenerator;
    private boolean  _statusIsInRound;
    
    public Player(String name){
        this._randomGenerator = new Random();
        this._name = name;
        this._tilessList = new LinkedList<Tile>();
        this.score = 0;
    }
    
    public void setLetters(LinkedList<Tile> bag){
        int tilesListSize = this._tilessList.size();
        for (int i = 0; i < 7-tilesListSize; i++) {
            int select = _randomGenerator.nextInt(bag.size());
            Tile tileRetrieved= bag.get(select);
            bag.remove(select);
            this._tilessList.add(tileRetrieved);
            this._statusIsInRound = true;
        }
    }

    public String getName() {
        return _name;
    }

    public int getScore() {
        return score;
    }
    
    
    public void setScore(int score) {
        this.score = score;
    }
    
    

    public LinkedList<Tile> getTilessList() {
        return _tilessList;
    }

    public boolean isStatusIsInRound() {
        return _statusIsInRound;
    }

    public void setStatusIsInRound(boolean _statusIsInRound) {
        this._statusIsInRound = _statusIsInRound;
    }

    public String getPlayerCurrentWord() {
        return playerCurrentWord;
    }

    public void setPlayerCurrentWord(String playerCurrentWord) {
        this.playerCurrentWord = playerCurrentWord;
    }
    
   
    
    public int getMissingTilesCount() {
        return missingTilesCount;
    }

    public void setMissingTilesCount(int missingTilesCount) {
        this.missingTilesCount = missingTilesCount;
    }

    public List<Tile> getPlayerCurrntWordTile() {
        return playerCurrntWordTile;
    }

    public void setPlayerCurrntWordToTiles() {
        this.playerCurrntWordTile = playerCurrntWordTile;
    }

    public void addScore(int score){
        this.score+=score;
    }
    
    public void addFine(){
        this.score -= 5;
    }
}
