package controllerScrabble;

import modelScrabble.ScrabbleModel;
import viewScrabble.ScrabbleView;

public class MVCScrabble {

    public static void main(String[] args) {
        ScrabbleModel model = new ScrabbleModel();
        ScrabbleView view = new ScrabbleView();
        Controller controller = new Controller(model, view);
        controller.startGame();
    }
}
