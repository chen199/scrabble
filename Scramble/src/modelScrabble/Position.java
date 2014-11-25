
package modelScrabble;

public class Position {
    
    int rows;
    int cols;
    int lastValue;
    char lastChar;
    
    public Position(int row, int col){
        this.rows = row;
        this.cols = col;
    }

    public int getLastValue() {
        return lastValue;
    }

    public void setLastValue(int lastValue) {
        this.lastValue = lastValue;
    }

    public char getLastChar() {
        return lastChar;
    }

    public void setLastChar(char lastChar) {
        this.lastChar = lastChar;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
    
    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }
    
    
    
   
}
