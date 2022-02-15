package square;

public class Square {
    private int x, y;
    private SquareStatus squareStatus;

    public Square(int y, int x) {
        this.y = y;
        this.x = x;
        this.squareStatus = SquareStatus.EMPTY;
    }

    public Square(int y, int x, SquareStatus status) {
        this.y = y;
        this.x = x;
        this.squareStatus = status;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public SquareStatus getSquareStatus() {
        return squareStatus;
    }

    public void setSquareStatus(SquareStatus squareStatus) {
        this.squareStatus = squareStatus;
    }

    public char getSquareChar() {
        return squareStatus.getChar();
    }

    @Override
    public String toString() {
        return "" + x + " " + y + "";
    }
}
