
package modelScrabble;

public class BoardSquare {
    private char sign;
    private char orgSign;
    private final int multiplier;
    private Tile tile;
    private boolean isAvailable;
    
    public BoardSquare(char sign, int multiplier){
        this.sign = sign;
        this.multiplier = multiplier;
        this.isAvailable = true;
        this.orgSign = sign;
    }
    
    public void setSign(char sign) {
        this.sign = sign;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
        if(tile != null){
            this.sign = tile.getLetter();
        }
    }

    public char getSign() {
        return sign;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public Tile getTile() {
        return tile;
    }

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    public void setOriginalSign(){
        this.sign = this.orgSign;
    }

    public char getOrgSign() {
        return orgSign;
    }
    
    
}
