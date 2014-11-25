/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelScrabble;

public class Tile {

    private char _letter;
    private int _value;

    public Tile(char letter, int value) {
        this._letter = letter;
        this._value = value;
    }
    
    public char getLetter() {
        return _letter;
    }

    public int getValue() {
        return _value;
    }
   
}
