package square;

public enum SquareStatus {
    EMPTY('.'), SHIP('0'), HIT('X'), MISSED('*'), SUNK('$');

    private final char character;

    SquareStatus(char character) {
        this.character = character;
    }

    public char getChar() {
        return character;
    }
}

