/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelScrabble;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class TilesList {

    LinkedList<Tile> _TilesBag;
    static final int ZERO = 0;
    static final int ONE = 1;
    static final int TWO = 2;
    static final int THREE = 3;
    static final int FOUR = 4;
    static final int FIVE = 5;
    static final int SIX = 6;
    static final int SEVEN = 7;
    static final int EIGHT = 8;
    static final int NINE = 9;
    static final int TEN = 10;
    static final int TWELVE = 12;
    static final int NUM_OF_CHARS = 26;

    public TilesList() {
        _TilesBag = new LinkedList<Tile>();
        char currentCharToHandle = 'A';
        for (int i = 0; i < NUM_OF_CHARS; i++) {
            switch (currentCharToHandle) {
                case 'A': {
                    for (int index = 0; index < NINE; index++) {
                        createTileAndPushToBag(currentCharToHandle, ONE);
                    }
                }
                break;
                case 'B': {
                    for (int index = 0; index < TWO; index++) {
                        createTileAndPushToBag(currentCharToHandle, THREE);
                    }
                }
                break;
                case 'C': {
                    for (int index = 0; index < TWO; index++) {
                        createTileAndPushToBag(currentCharToHandle, THREE);
                        
                    }
                }
                break;
                case 'D': {
                    for (int index = 0; index < FOUR; index++) {
                        createTileAndPushToBag(currentCharToHandle, TWO);
                    }
                }
                break;
                case 'E': {
                    for (int index = 0; index < TWELVE; index++) {
                        createTileAndPushToBag(currentCharToHandle, ONE);
                    }
                }
                break;
                case 'F': {
                    for (int index = 0; index < TWO; index++) {
                        createTileAndPushToBag(currentCharToHandle, FOUR);
                    }
                }
                break;
                case 'G': {
                    for (int index = 0; index < THREE; index++) {
                        createTileAndPushToBag(currentCharToHandle, TWO);
                    }
                }
                break;
                case 'H': {
                    for (int index = 0; index < TWO; index++) {
                        createTileAndPushToBag(currentCharToHandle, FOUR);
                    }
                }
                break;
                case 'I': {
                    for (int index = 0; index < NINE; index++) {
                        createTileAndPushToBag(currentCharToHandle, ONE);
                    }
                }
                break;
                case 'J': {
                    for (int index = 0; index < ONE; index++) {
                        createTileAndPushToBag(currentCharToHandle, EIGHT);
                    }
                }
                break;
                case 'K': {
                    for (int index = 0; index < ONE; index++) {
                        createTileAndPushToBag(currentCharToHandle, FIVE);
                    }
                }
                break;
                case 'L': {
                    for (int index = 0; index < FOUR; index++) {
                        createTileAndPushToBag(currentCharToHandle, ONE);
                    }
                }
                break;
                case 'M': {
                    for (int index = 0; index < TWO; index++) {
                        createTileAndPushToBag(currentCharToHandle, THREE);
                    }
                }
                break;
                case 'N': {
                    for (int index = 0; index < SIX; index++) {
                        createTileAndPushToBag(currentCharToHandle, ONE);
                    }
                }
                break;
                case 'O': {
                    for (int index = 0; index < EIGHT; index++) {
                        createTileAndPushToBag(currentCharToHandle, ONE);
                    }
                }
                break;
                case 'P': {
                    for (int index = 0; index < TWO; index++) {
                        createTileAndPushToBag(currentCharToHandle, THREE);
                    }
                }
                break;
                case 'Q': {
                    for (int index = 0; index < ONE; index++) {
                        createTileAndPushToBag(currentCharToHandle, TEN);
                    }
                }
                break;
                case 'R': {
                    for (int index = 0; index < SIX; index++) {
                        createTileAndPushToBag(currentCharToHandle, ONE);
                    }
                }
                break;
                case 'S': {
                    for (int index = 0; index < FOUR; index++) {
                        createTileAndPushToBag(currentCharToHandle, ONE);
                    }
                }
                break;
                case 'T': {
                    for (int index = 0; index < SIX; index++) {
                        createTileAndPushToBag(currentCharToHandle, ONE);
                    }
                }
                break;
                case 'V': {
                    for (int index = 0; index < TWO; index++) {
                        createTileAndPushToBag(currentCharToHandle, FOUR);
                    }
                }
                break;
                case 'U': {
                    for (int index = 0; index < FOUR; index++) {
                        createTileAndPushToBag(currentCharToHandle, ONE);
                    }
                }
                break;
                case 'W': {
                    for (int index = 0; index < TWO; index++) {
                        createTileAndPushToBag(currentCharToHandle, FOUR);
                    }
                }
                break;
                case 'X': {
                    for (int index = 0; index < ONE; index++) {
                        createTileAndPushToBag(currentCharToHandle, EIGHT);
                    }
                }
                break;
                case 'Y': {
                    for (int index = 0; index < TWO; index++) {
                        createTileAndPushToBag(currentCharToHandle, FOUR);
                    }
                }
                break;
                case 'Z': {
                    for (int index = 0; index < ONE; index++) {
                        createTileAndPushToBag(currentCharToHandle, TEN);
                    }
                }
                break;
            }
            currentCharToHandle ++;
        }
        for (int i = 0; i < TWO; i++) {
            createTileAndPushToBag('_', 0);
        }
        
        this.shuffleTilesBag();
    }

    public LinkedList<Tile> getTilesBag() {
        return _TilesBag;
    }
    
    private void createTileAndPushToBag(char letter, int value){
        Tile tileToPush = new Tile(letter, value);
        _TilesBag.add(tileToPush);
    }
    
    private void shuffleTilesBag(){
        Collections.shuffle(_TilesBag);
    }

}
