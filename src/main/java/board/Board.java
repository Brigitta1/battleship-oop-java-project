package board;

import ship.Ship;
import square.Square;
import square.SquareStatus;


public class Board {
    Square[][] board;
    private final int length;


    public Board(int length) {
        this.length = length;
        this.board = initBoard(length);
    }

    public Square[][] getBoard() {
        return board;
    }

    public int getLength() {
        return length;
    }

    public Square[][] initBoard(int length) {
        board = new Square[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                board[i][j] = new Square(i, j, SquareStatus.EMPTY);
            }
        }
        return board;
    }

    public void handleShotInBoard(Square shotSquare, Ship ship) {
        if (board[shotSquare.getY()][shotSquare.getX()].getSquareStatus() == SquareStatus.EMPTY) {
            board[shotSquare.getY()][shotSquare.getX()].setSquareStatus(SquareStatus.MISSED);
        } else if (board[shotSquare.getY()][shotSquare.getX()].getSquareStatus() == SquareStatus.SHIP) {
            board[shotSquare.getY()][shotSquare.getX()].setSquareStatus(SquareStatus.HIT);
        }

        if (ship != null && ship.isSunk())
        {
            handleSunk(ship);
        }
    }

    public void handleSunk(Ship sunkShip) {
        for (Square square : sunkShip.getSquareList()) {
            board[square.getY()][square.getX()].setSquareStatus(SquareStatus.SUNK);
        }
    }

    public String boardToString(boolean isEnemy) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int i = 0; i < getBoard().length; i++) {
            sb.append(String.format("%3s", i + 1));
        }

        sb.append("\n");
        for (int y = 0; y < getBoard().length; y++) {
            sb.append((char) ('A' + y));
            for (int x = 0; x < getBoard()[0].length; x++) {
                if (isEnemy && getBoard()[y][x].getSquareStatus() == SquareStatus.SHIP) {
                    sb.append("  ").append(SquareStatus.EMPTY.getChar());
                } else {
                    sb.append("  ").append(getBoard()[y][x].getSquareStatus().getChar());
                }
            }
            sb.append("\n");
        }
        return String.valueOf(sb);
    }

    public boolean isPlacementOk() {
        return true;
    }
}
