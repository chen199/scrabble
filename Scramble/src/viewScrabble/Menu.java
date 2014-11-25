package viewScrabble;

public class Menu {

    String[] _Menu;
    int _length;
    int _minVal;

    public int getMinVal() {
        return _minVal;
    }

    public int getMaxVal() {
        return _maxVal;
    }
    int _maxVal;

    public Menu(int minVal, int maxVal, String... args) {
        _Menu = args;
        _length = args.length;
        _minVal = minVal;
        _maxVal = maxVal;
    }

    public String[] getMenu() {
        return _Menu;
    }

    public int getLength() {
        return _length;
    }

}
