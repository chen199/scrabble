package controllerScrabble;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javafx.print.Collation;
import modelScrabble.Position;
import viewScrabble.ScrabbleView;
import modelScrabble.ScrabbleModel;
import modelScrabble.Player;
import modelScrabble.PlayerComputer;
import modelScrabble.PlayerHuman;
import modelScrabble.Tile;
import mta.spellchecker.SpellChecker;

public class Controller {

    ScrabbleModel model;
    ScrabbleView view;
    SpellChecker spellChecker = SpellChecker.getInstace();

    public enum TURN {

        SKIP, PLAY_LETTER, PLAY_WORD
    };

    public enum Direction {

        Horizonal,
        Vertical,
        None
    }

    public Controller(ScrabbleModel model, ScrabbleView view) {
        this.model = model;
        this.view = view;
    }

    public void startGame() {

        if (view.getInputFromUser(view.getMainMenu()) == 1) {
            model.setNumOfPlayers(view.getInputFromUser(view.getNumOfPlayersMenu()));
            view.setNumOfHumanPlayersMenu(0, model.getNumOfPlayers());
            model.setNumOfHumanPlayers(view.getInputFromUser(view.getNumOfHumanPlayersMenu()));
            model.setNumOfComputerPlayers(model.getNumOfPlayers() - model.getNumOfHumanPlayers());
            model.setPlayers();
            System.out.println("LET THE GAME BEGIN! #" + model.getNumOfHumanPlayers() + "humans, #" + model.getNumOfComputerPlayers() + " computers");
            playRound();
            //  view.printBoard(model.getGameBoard().getBoard());
        } else {

        }
    }

    private void playRound() {
        boolean isValidRound = true;
        do {
            for (Player player : model.getpList()) {
//                if (player instanceof PlayerHuman && player.isStatusIsInRound() == true && isValidRound) {
//                    System.out.println(player.getName() + " is playing");
//                    playSequence(player);
//                }
                if(player.isStatusIsInRound() == true && isValidRound){
                    System.out.println(player.getName() + " is playing");
                    playSequence(player);
                }
                isValidRound = isMoreThenCoupleInGame();
            }
        } while (isValidRound);
        System.out.println("round is over");
    }

    private boolean isMoreThenCoupleInGame() {
        int numOfPlayersInRound = 0;
        for (Player player : model.getpList()) {
            if (player.isStatusIsInRound()) {
                numOfPlayersInRound++;
            }
        }
        return numOfPlayersInRound > 1;
    }
    
    private void playSequence(Player player) {
        view.printBoard(model.getGameBoard().getBoard());
        //view.getInputFromUser(view.getTurnMenu());
        //switch (view.getHumanPlayerTurnMenuMove(player)) {
        switch (getPlayerMove(player)) {
            case 1: {
                view.printMessage(player.getName() + " skipped his turn..");
                view.printMessage("------------------------");
            }
            break;
            case 2: {
                view.printMessage(player.getName() + " is switching a letter..");
                if(player instanceof PlayerHuman){
                    letterSequenceHuman(player);
                } else {
                    letterSequenceComputer(player);
                }
                view.printMessage("------------------------");
            }
            break;
            case 3: {
                System.out.println(player.getName() + " enterd a word sequence..");
                if(player instanceof PlayerHuman){
                    wordSequenceHuman(player);
                } else {
                    wordSequenceComputer(player);
                }
                System.out.println("------------------------");
            }
            break;
            case 4: {
                player.setStatusIsInRound(false);
                System.out.println(player.getName() + " has quit round..");
                System.out.println("------------------------");
            }
        }
    }

    private int getPlayerMove(Player player) {
        int playerMoveInput = 1;
        if (player.isStatusIsInRound()) {
            if (player instanceof PlayerHuman) {
                playerMoveInput = view.getHumanPlayerTurnMenuMove(player);
            } else {
                playerMoveInput = getComputerPlayerMove(player);
            }
        }
        return playerMoveInput;
    }

    private int getComputerPlayerMove(Player player) {
        Random rand = new Random();
        int range = 4;
        return rand.nextInt() % range;
    }

    private void wordSequenceHuman(Player player) {

        LinkedList<Tile> tilesSelectionList = new LinkedList<>();
        LinkedList<Tile> playerList = player.getTilessList();
        LinkedList<Position> positionsList = new LinkedList<Position>();
        boolean isEndOfSequence = false;

        do {
            int userTileListIndexSelection = view.getLetterFromUser(playerList);
            if (userTileListIndexSelection == 9) {
                isEndOfSequence = true;
            } else {
                /* ------------------- */
                addTileToSelectdTilesList(tilesSelectionList, playerList.get(userTileListIndexSelection));
                removoeTileFromPlayerTiles(playerList, userTileListIndexSelection);
                handlePosition(positionsList);
                addLastTileToBoard(tilesSelectionList, positionsList);
                System.out.println("updated board:");
                view.printBoard(model.getGameBoard().getBoard());
            }
        } while (!isEndOfSequence);
        handleWordCheck(tilesSelectionList, positionsList, player);

    }

    //placing on board, remove tiles from player list to current list, update locations list (1)
    private void addTileToSelectdTilesList(LinkedList<Tile> listToUpdate, Tile tileToAdd) {
        listToUpdate.addLast(tileToAdd);
    }

    private void removoeTileFromPlayerTiles(LinkedList<Tile> listToUpdate, int userTileListIndexSelection) {
        listToUpdate.remove(userTileListIndexSelection);
    }

    private void handlePosition(LinkedList<Position> positionsList) {
        boolean isLagalPosition = false;
        int col, row;
        Position position;
        String userInput;
        do {
            userInput = view.getLetterPositionInput();
            row = analizeRowLocation(userInput);
            col = analizeColLocation(userInput);
            System.out.println("for user input: row is #" + row + " and col is #" + col);
            if (row > 0 && row < 16 && col != -1) {

                position = new Position(row, col);
                if (isLegalPositionOnBoard(position)) {
                    if (isOneLineSequence(positionsList, position)) {
                        // System.out.println("for 2nd user input: row is #" + position.getRows() + " and col is #" + position.getCols());
                        positionsList.addLast(position);
                        isLagalPosition = true;
                    } else {
                        System.out.println("not in one line");
                        position = null;
                    }
                }
            } else {
                view.printMessage("invalid input for location. please try again");
            }
        } while (!isLagalPosition);
    }

    private boolean isOneLineSequence(LinkedList<Position> positionsList, Position position) {
        boolean isOneLine = false;
        Direction sequenceDirection;
        if (positionsList.size() == 0) {
            isOneLine = true;
        } else {
            isOneLine = checkList(positionsList, position);
        }
        return isOneLine;
    }

    private boolean checkList(LinkedList<Position> positionsList, Position position) {
        if (positionsList.size() == 1) {
            return compareSecondTile(positionsList.getLast(), position);
        } else {
            return compareThirdAndAbove(positionsList, position);
        }
    }

    private boolean compareSecondTile(Position last, Position position) {
        System.out.println("comapring 2 tiles!");
        boolean isLegal = false;
        if (last.getCols() == position.getCols() - 1 || last.getCols() == position.getCols() + 1) {
            isLegal = true;
        } else if (last.getRows() == position.getRows() - 1 || last.getRows() == position.getRows() + 1) {
            isLegal = true;
        }
        return isLegal;
    }

    private boolean compareThirdAndAbove(LinkedList<Position> positionsList, Position position) {
        System.out.println("comparing 3 and above!");
        boolean isLegal = false;
        Direction listDirection = getListDirection(positionsList);
        if (listDirection == Direction.Horizonal) {
            System.out.println("the list is horizontal");
            if (position.getRows() == positionsList.getLast().getRows()) {
                System.out.println("same rows");
                if (position.getCols() == positionsList.getLast().getCols() + 1) {
                    isLegal = true;
                }
            }
        } else {
            System.out.println("the list is vertical");
            if (position.getCols() == positionsList.getLast().getCols()) {
                System.out.println("same cols");
                if (position.getRows() == positionsList.getLast().getRows() + 1) {
                    isLegal = true;
                }
            }
        }

        return isLegal;
    }

    private Direction getListDirection(LinkedList<Position> positionsList) {
        Direction dirToRerutn;
        int rowStart = positionsList.getFirst().getRows();
        int rowEnd = positionsList.getLast().getRows();
        if (positionsList.size() == 1) {
            dirToRerutn = Direction.None;
        } else {
            dirToRerutn = (rowStart == rowEnd) ? Direction.Horizonal : Direction.Vertical;
        }
        return dirToRerutn;
    }

    private boolean isLegalPositionOnBoard(Position position) { //and update board location status
        boolean isLagalPosition = false;
        if (model.getGameBoard().getBoard()[position.getRows()][position.getCols()].isIsAvailable()) {
            model.getGameBoard().getBoard()[position.getRows()][position.getCols()].setIsAvailable(false);
            isLagalPosition = true;
        }
        return isLagalPosition;
    }

    private int analizeColLocation(String letterPositionInput) {
        char charToCheck = letterPositionInput.charAt(0);
        int valueToReturn = 0;
        switch (charToCheck) {
            case 'A': {
                valueToReturn = 3;
            }
            case 'a': {
                valueToReturn = 3;
            }
            break;
            case 'B': {
                valueToReturn = 4;
            }
            case 'b': {
                valueToReturn = 4;
            }
            break;
            case 'C': {
                valueToReturn = 5;
            }
            break;
            case 'c': {
                valueToReturn = 5;
            }
            break;
            case 'D': {
                valueToReturn = 6;
            }
            break;
            case 'd': {
                valueToReturn = 6;
            }
            break;
            case 'E': {
                valueToReturn = 7;
            }
            break;
            case 'e': {
                valueToReturn = 7;
            }
            break;

            case 'F': {
                valueToReturn = 8;
            }
            break;
            case 'f': {
                valueToReturn = 8;
            }
            break;
            case 'G': {
                valueToReturn = 9;
            }
            break;
            case 'g': {
                valueToReturn = 9;
            }
            break;
            case 'H': {
                valueToReturn = 10;
            }
            break;
            case 'h': {
                valueToReturn = 10;
            }
            break;
            case 'I': {
                valueToReturn = 11;
            }
            break;
            case 'i': {
                valueToReturn = 11;
            }
            break;
            case 'J': {
                valueToReturn = 12;
            }
            break;
            case 'j': {
                valueToReturn = 12;
            }
            break;
            case 'K': {
                valueToReturn = 13;
            }
            break;
            case 'k': {
                valueToReturn = 13;
            }
            break;

            case 'L': {
                valueToReturn = 14;
            }
            break;

            case 'l': {
                valueToReturn = 14;
            }
            break;
            case 'M': {
                valueToReturn = 15;
            }
            break;
            case 'm': {
                valueToReturn = 15;
            }
            break;
            case 'N': {
                valueToReturn = 16;
            }
            break;
            case 'n': {
                valueToReturn = 16;
            }
            break;
            case 'O': {
                valueToReturn = 17;
            }
            break;
            case 'o': {
                valueToReturn = 17;
            }
            break;
            default:
                valueToReturn = -1;
        }

        return valueToReturn;
    }

    private int analizeRowLocation(String letterPositionInput) {
        int valToReturn = 0;
        if (letterPositionInput.length() == 3) {
            if (Character.getNumericValue(letterPositionInput.charAt(1)) == 1) {
                valToReturn = 10;
                valToReturn += Character.getNumericValue(letterPositionInput.charAt(2));
            } else {
                valToReturn = -1;
            }
        } else {
            valToReturn = Character.getNumericValue(letterPositionInput.charAt(1));
        }
        return valToReturn;
    }

    private void addLastTileToBoard(LinkedList<Tile> tilesSelectionList, LinkedList<Position> positionsList) {
        int row = positionsList.getLast().getRows();
        int col = positionsList.getLast().getCols();

        Position posToCheck = positionsList.getLast();
        //  System.out.println("for 3rd user input (POSITION LIST): row is #" + posToCheck.getRows() + " and col is #" + posToCheck.getCols());
        model.getGameBoard().getBoard()[row][col].setTile(tilesSelectionList.getLast());
        //System.out.println("the tile is: " + model.getGameBoard().getBoard()[row][col].getTile().getLetter());
        model.getGameBoard().getBoard()[row][col].setIsAvailable(false);
    }

    //verifying the sequence (2)
    private void handleWordCheck(LinkedList<Tile> tilesSelected, LinkedList<Position> positionsList, Player player) {
//        System.out.println("debug: the list from Tile list is ");
//        System.out.println(printTiles(tilesSelected));
//        System.out.println("---");
//        System.out.println("debug: the list from Positons list is ");
//        System.out.println(printPositions(positionsList));
//        System.out.println("---");
//        System.out.println("got to check the word now...");

        String wordToCheck = getWordFromBoard(positionsList);
        if (!spellChecker.spellCheck(wordToCheck)) {
            view.printMessage(" '" + wordToCheck + "' is not word. -5 points.");
            handleInvalidWord(player, positionsList);
            player.addFine();
        } else {
            view.printMessage("the word |" + wordToCheck + "| is valid word");
            if (isFirstMovePlayed()) {
                if (isTouchingOtherWord(positionsList)) {
                    handleValidWord(player, positionsList);
                } else {
                    view.printMessage("Invalid location, word need to be attached to the other words");
                    handleInvalidWord(player, positionsList);
                }
            } else {
                if (isFirstMoveIncludeInPositionList(positionsList)) {
                    // if(isTouchingOtherWord(positionsList)){
                    handleValidWord(player, positionsList);
                    //} else {
                    //  handleInvalidWord(player, positionsList);
                    //view.printMessage("the location is Invalid");
                    //}
                } else {
                    handleInvalidWord(player, positionsList);
                    System.out.println("The first word need to be on H8 location.");
                }
            }
        }
    }

    private boolean isFirstMoveIncludeInPositionList(LinkedList<Position> positionList) {
        boolean isInclude = false;
        int row, col;
        for (int i = 0; i < positionList.size() && !isInclude; i++) {
            row = positionList.get(i).getRows();
            col = positionList.get(i).getCols();
            if (row == 8 && col == 10) {
                isInclude = true;
            }
        }
        return isInclude;
    }

    private boolean isTouchingOtherWord(LinkedList<Position> positionsList) {
        boolean isValid = false;
        int wordLength = positionsList.size();
        Direction wordDir = getListDirection(positionsList);
        int wholeWordLength = getWholeWordPositionList(positionsList, wordDir).size();
        if (wordLength < wholeWordLength) {
            //System.out.println("the word LOCATION is valid");
            isValid = true;
        } else {
            //System.out.println("the word LOCATION is invalid");
        }
        return isValid;
    }

    //DEBUG ONLY
//    private String printTiles(LinkedList<Tile> tilesSelected) {
//        for (Tile tilesSelected1 : tilesSelected) {
//            System.out.print(tilesSelected1.getLetter());
//        }
//        return "";
//    }
    //DEBUG ONLY
//    private String printPositions(LinkedList<Position> positionsList) {
//        for (Position positionsList1 : positionsList) {
//            System.out.print(model.getGameBoard().getBoard()[positionsList1.getRows()][positionsList1.getCols()].getSign());
//        }
//        return "";
//    }
    //TODO: MAYBE USE GET-WHOLE-LIST
    private String getWordFromBoard(LinkedList<Position> positionsList) {
        Direction wordDircection = getListDirection(positionsList);
        LinkedList<Position> before = new LinkedList<>();
        LinkedList<Position> after = new LinkedList<>();
        String joinedString;

        if (wordDircection == Direction.Horizonal) {
            before = getBeforePositionsHorizontal(positionsList);
            after = getAfterPositionsHorizontal(positionsList);
        } else if (wordDircection == Direction.Vertical) {
            before = getBeforePositionsVertical(positionsList);
            after = getAfterPositionsVertical(positionsList);
        } else {
            System.out.println("got one letter");
            before = getBeforePositionsHorizontal(positionsList);
        }

        joinedString = assambleLists(positionsList, before, after);
        view.printMessage("The word is: '" + joinedString + "'");

        return joinedString;
    }

    private LinkedList<Position> getBeforePositionsHorizontal(LinkedList<Position> positionsList) {
        LinkedList<Position> returnList = new LinkedList<>();
        int row = positionsList.getFirst().getRows();
        int col = positionsList.getFirst().getCols() - 1;
        Position pivot = new Position(row, col);
        while (!model.getGameBoard().getBoard()[pivot.getRows()][pivot.getCols()].isIsAvailable()) {
            returnList.addFirst(new Position(pivot.getRows(), pivot.getCols()));
            col -= 1;
            pivot.setCols(col);
        }
        return returnList;
    }

    private LinkedList<Position> getAfterPositionsHorizontal(LinkedList<Position> positionsList) {
        LinkedList<Position> returnList = new LinkedList<>();
        int row = positionsList.getLast().getRows();
        int col = positionsList.getLast().getCols() + 1;
        Position pivot = new Position(row, col);
        while (!model.getGameBoard().getBoard()[pivot.getRows()][pivot.getCols()].isIsAvailable()) {
            returnList.addLast(new Position(pivot.getRows(), pivot.getCols()));
            col += 1;
            pivot.setCols(col);
        }
        return returnList;
    }

    private LinkedList<Position> getBeforePositionsVertical(LinkedList<Position> positionsList) {
        LinkedList<Position> returnList = new LinkedList<>();
        int row = positionsList.getFirst().getRows() - 1;
        int col = positionsList.getFirst().getCols();
        Position pivot = new Position(row, col);
        while (!model.getGameBoard().getBoard()[pivot.getRows()][pivot.getCols()].isIsAvailable()) {
            returnList.addFirst(new Position(pivot.getRows(), pivot.getCols()));
            row -= 1;
            pivot.setRows(row);
        }
        return returnList;
    }

    private LinkedList<Position> getAfterPositionsVertical(LinkedList<Position> positionsList) {
        LinkedList<Position> returnList = new LinkedList<>();
        int row = positionsList.getLast().getRows() + 1;
        int col = positionsList.getLast().getCols();
        Position pivot = new Position(row, col);
        while (!model.getGameBoard().getBoard()[pivot.getRows()][pivot.getCols()].isIsAvailable()) {
            returnList.addFirst(new Position(pivot.getRows(), pivot.getCols()));
            row += 1;
            pivot.setRows(row);
        }
        return returnList;
    }

    private String assambleLists(LinkedList<Position> positionsList, LinkedList<Position> before, LinkedList<Position> after) {
        StringBuilder builder = new StringBuilder();
        String start = "", middle = "", end = "";
        if (before.size() > 0) {
            start = positionsToString(before);
            //     System.out.println("the start is " + start);
        }
        middle = positionsToString(positionsList);
        // System.out.println("the middle is " + middle);
        if (after.size() > 0) {
            end = positionsToString(after);
            //   System.out.println("the end  is " + end);
        }
        builder.append(start);
        builder.append(middle);
        builder.append(end);
        //  System.out.println("AND the overall is " + builder.toString());
        return builder.toString();
    }

    private String positionsToString(LinkedList<Position> posList) {
        StringBuilder builder = new StringBuilder();
        //System.out.println("the list we check size is " + posList.size());
        int index = 0;
        for (Position position : posList) {
            //  System.out.print("the index is " + index + "; ");
            // System.out.println(" and the position is: #row " + position.getRows() + " #col " + position.getCols());
            builder.append(model.getGameBoard().getBoard()[position.getRows()][position.getCols()].getTile().getLetter());

        }
        return builder.toString();
    }

    private void handleInvalidWord(Player player, LinkedList<Position> positionsList) {
        for (Position position : positionsList) {
            player.getTilessList().add(model.getGameBoard().getBoard()[position.getRows()][position.getCols()].getTile());
            model.getGameBoard().getBoard()[position.getRows()][position.getCols()].setTile(null);
            model.getGameBoard().getBoard()[position.getRows()][position.getCols()].setIsAvailable(true);
            model.getGameBoard().getBoard()[position.getRows()][position.getCols()].setOriginalSign();
        }
    }

    private void handleValidWord(Player player, LinkedList<Position> positionsList) {
        LinkedList<Position> wholePositionList;
        Direction dir = getListDirection(positionsList);
        //wholePositionList = getWholeWordPositionList(positionsList, dir);
        //player.addScore(sumPositionsScore(wholePositionList));
        player.addScore(sumPositionsScore(positionsList));
        fillPlayerTileList(player);
        System.out.println(player.getName() + " sum is " + player.getScore());
    }

    //not using it for now
    private LinkedList<Position> getWholeWordPositionList(LinkedList<Position> positionsList, Direction wordDircection) {
        LinkedList<Position> before = new LinkedList<>();
        LinkedList<Position> after = new LinkedList<>();
        LinkedList<Position> returnListForSignal = new LinkedList<>();
        LinkedList<Position> result = null;
        if (wordDircection == Direction.Horizonal) {
            before = getBeforePositionsHorizontal(positionsList);
            after = getAfterPositionsHorizontal(positionsList);
        } else if (wordDircection == Direction.Vertical) {
            before = getBeforePositionsVertical(positionsList);
            after = getAfterPositionsVertical(positionsList);
        } else {
            System.out.println("handle the else..");
            returnListForSignal = getBeforePositionsHorizontal(positionsList);
            if (returnListForSignal.size() < 1) {
                returnListForSignal = getAfterPositionsHorizontal(positionsList);
                if (returnListForSignal.size() < 1) {
                    returnListForSignal = getBeforePositionsVertical(positionsList);
                    if (returnListForSignal.size() < 1) {
                        returnListForSignal = getAfterPositionsVertical(positionsList);
                        if (returnListForSignal.size() > 0) {
                            positionsList.addAll(returnListForSignal);
                            result = positionsList;
                        }
                    } else {
                        returnListForSignal.addAll(positionsList);
                        result = returnListForSignal;
                    }
                } else {
                    positionsList.addAll(returnListForSignal);
                    result = positionsList;
                }
            } else {
                returnListForSignal.addAll(positionsList);
                result = returnListForSignal;
            }
        }

        if (result.size() > 0) {
            System.out.println("WE TOOK THE GOOD OPTION I HOPE");
        } else {
            before.addAll(positionsList);
            before.addAll(after);
        }
        return result;
    }

    private int sumPositionsScore(LinkedList<Position> wholePositionList) {
        int ordinerySum = 0;
        int wordMultiplySum = 0;
        int singleMultipliersScore = 0, sumToReturn = 0;
        int wordMultiplier = 1;
        for (Position position : wholePositionList) {
            ordinerySum += model.getGameBoard().getBoard()[position.getRows()][position.getCols()].getTile().getValue();
            singleMultipliersScore += (model.getGameBoard().getBoard()[position.getRows()][position.getCols()].getMultiplier()
                    * model.getGameBoard().getBoard()[position.getRows()][position.getCols()].getTile().getValue());
            if (model.getGameBoard().getBoard()[position.getRows()][position.getCols()].getOrgSign() == '@'
                    || model.getGameBoard().getBoard()[position.getRows()][position.getCols()].getOrgSign() == '#') {
                if (model.getGameBoard().getBoard()[position.getRows()][position.getCols()].getMultiplier() > wordMultiplier) {
                    wordMultiplier = model.getGameBoard().getBoard()[position.getRows()][position.getCols()].getMultiplier();
                }
            }
        }
        if (wordMultiplier > 1) {
            ordinerySum *= wordMultiplier;
            sumToReturn = ordinerySum;
        } else {
            sumToReturn = singleMultipliersScore;
        }
        //System.out.println("the sum is: " + sumToReturn);
        return sumToReturn;
    }

    private void fillPlayerTileList(Player player) {
        LinkedList<Tile> listToUpdate = player.getTilessList();
        int numToAdd = 7 - listToUpdate.size();
        for (int i = 0; i < numToAdd; i++) {
            if (model.getTilesList().getTilesBag().size() > 0) {
                listToUpdate.add(model.getTilesList().getTilesBag().pop());
            }
        }
    }

    private boolean isFirstMovePlayed() {
        boolean isPlayed = false;
        for (Player player : model.getpList()) {
            if (player.getScore() > 0) {
                isPlayed = true;
            }
        }
//        
//        System.out.println("the sign of the first place is " + model.getGameBoard().getBoard()[8][10].getSign());
//        if(model.getGameBoard().getBoard()[8][10].getSign() == '@'){
//            //System.out.println("firstTileAvailable");
//            System.out.println("the sign is @");
//            isPlayed = false;
//        } else {
//            System.out.println("the sign is ***NOT*** @");
//            //System.out.println("firstTileAvailable NOT");
//            isPlayed = true;
//        }
        return isPlayed;
    }

    private void letterSequenceHuman(Player player) {
        if (model.getTilesList().getTilesBag().size() > 0) {
            handleTileSwitch(player);
        } else {
            view.printMessage("No tiles left! the bag is empty");
        }
    }

    private void handleTileSwitch(Player player) {
        int numOfTileToReplace;
        if (player instanceof PlayerHuman) {
            numOfTileToReplace = view.getLetterFromUser(player.getTilessList());
        } else {
            numOfTileToReplace = 3;
        }
        replaceTiles(numOfTileToReplace, player.getTilessList());

    }

    private void replaceTiles(int numOfTileToReplace, LinkedList<Tile> tilessList) {
        Tile TileToReplace = tilessList.remove(numOfTileToReplace);
        Tile TileToInsert = model.getTilesList().getTilesBag().pop();
        tilessList.add(TileToInsert);
        view.printMessage("the retrieved tile is " + TileToInsert.getLetter() + TileToInsert.getValue());
        model.getTilesList().getTilesBag().add(TileToReplace);
    }
}
