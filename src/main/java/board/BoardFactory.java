package board;

import game.Input;
import player.Player;
import ship.Ship;
import ship.ShipType;
import square.Square;
import square.SquareStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BoardFactory {
    private final int fleetSize = 15;
    private Input input;

    public BoardFactory(Input input) {
        this.input = input;
    }

    public void initBoard(Player player, Board board, int placement) {
        if (placement == 1) {
            player.setShipList(generateFleet(board));
            randomPlacement(player, board);   /* RANDOM PLACEMENT */
        } else if (placement == 0) {
            player.setShipList(generateManualFleet(board));

        }
    }

    /************************************************************************/
    /* RANDOM GENERATOR */

    /************************************************************************/

    private Ship createShip(Board board, ShipType type) {
        List<Square> squares = new ArrayList<>();
        Random random = new Random();
        boolean isHorizontal = random.nextBoolean();
        int startCoordinateY = random.nextInt(16 - type.getLength());
        int startCoordinateX = random.nextInt(16 - type.getLength());

        while (!doesOverlap(board, isHorizontal, type.getLength(), startCoordinateX, startCoordinateY)) {
            isHorizontal = random.nextBoolean();
            startCoordinateY = random.nextInt(16 - type.getLength());
            startCoordinateX = random.nextInt(16 - type.getLength());
        }

        if (isHorizontal) {
            for (int i = 0; i < type.getLength(); i++) {
                Square square = new Square(i + startCoordinateY, startCoordinateX, SquareStatus.SHIP);
                squares.add(square);
            }
        } else {
            for (int i = 0; i < type.getLength(); i++) {
                Square square = new Square(startCoordinateY, i + startCoordinateX, SquareStatus.SHIP);
                squares.add(square);
            }
        }

        Ship ship = new Ship(squares);
        ship.setShipType(type);
        return ship;
    }

    public List<Ship> generateFleet(Board board) {
        List<Ship> fleet = new ArrayList<>();
        for (int i = 0; i < selectQuantityOfShip(ShipType.CARRIER); i++) {
            Ship carrier = createShip(board, ShipType.CARRIER);
            fleet.add(carrier);
        }

        for (int i = 0; i < selectQuantityOfShip(ShipType.BATTLESHIP); i++) {
            Ship battle = createShip(board, ShipType.BATTLESHIP);
            fleet.add(battle);
        }

        for (int i = 0; i < selectQuantityOfShip(ShipType.CRUISER); i++) {
            Ship cruiser = createShip(board, ShipType.CRUISER);
            fleet.add(cruiser);
        }

        for (int i = 0; i < selectQuantityOfShip(ShipType.SUBMARINE); i++) {
            Ship sub = createShip(board, ShipType.SUBMARINE);
            fleet.add(sub);
        }

        for (int i = 0; i < selectQuantityOfShip(ShipType.DESTROYER); i++) {
            Ship destroyer = createShip(board, ShipType.DESTROYER);
            fleet.add(destroyer);
        }
        return fleet;
    }

    public int selectQuantityOfShip(ShipType type) {
        int quantityOfShip = 0;
        switch (type) {
            case CARRIER:
                quantityOfShip = 1;
                break;
            case BATTLESHIP:
                quantityOfShip = 2;
                break;
            case CRUISER:
                quantityOfShip = 3;
                break;
            case SUBMARINE:
                quantityOfShip = 4;
                break;
            case DESTROYER:
                quantityOfShip = 5;
                break;
        }
        return quantityOfShip;
    }

    public void placeShip(Board board, Ship ship) {
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                for (Square square : ship.getSquareList()) {
                    if (square.getX() == j && square.getY() == i) {
                        board.getBoard()[i][j] = square;
                    }
                }
            }
        }
    }

    public boolean doesOverlap(Board board, boolean isHorizontal, int shipLength, int x, int y) {
        if (isBoardEmpty(board)) {
            return true;
        }

        if (isHorizontal) {
            for (int i = 0; i < shipLength; i++) {
                if (board.getBoard()[y + i][x].getSquareChar() == '0'
                        && board.getBoard()[y + i + 1][x].getSquareChar() == '0' &&
                        board.getBoard()[y + i - 1][x].getSquareChar() == '0' &&
                        board.getBoard()[y + i][x + 1 + i].getSquareChar() == '0' &&
                        board.getBoard()[y + i][x - 1 + i].getSquareChar() == '0') {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < shipLength; i++) {
                if (board.getBoard()[y][x + i].getSquareChar() == '0' &&
                        board.getBoard()[y][x + i].getSquareChar() == '0' &&
                        board.getBoard()[y][x + i].getSquareChar() == '0' &&
                        board.getBoard()[y][x + i + 1].getSquareChar() == '0' &&
                        board.getBoard()[y][x + i - 1].getSquareChar() == '0') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isBoardEmpty(Board board) {
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if (board.getBoard()[i][j].getSquareChar() == '0') {
                    return false;
                }
            }
        }
        return true;
    }

    /************************************************************************/
    /* RANDOM PLACEMENT */

    /************************************************************************/

    public void randomPlacement(Player player, Board board) {
        boolean horizontal;
        Square square;
        boolean placementOK;

        for (Ship ship : player.getShipList()) {
            do {
                horizontal = isHorizontal();
                square = getStartPos(board, ship, horizontal);
                placementOK = validatePlacement(square, board, ship, horizontal);
            } while (!placementOK);
            if (placementOK) {
                setUpShipOnBoard(square, board, ship, horizontal);
            }
        }

    }

    private void setUpShipOnBoard(Square square, Board board, Ship ship, boolean horizontal) {
        int sY = square.getY();
        int sX = square.getX();
        for (int i = 0; i < ship.getShipType().getLength(); i++) {
            if (horizontal) {
                board.getBoard()[sY][sX + i].setSquareStatus(SquareStatus.SHIP);
                ship.getSquareList().get(i).setY(sY);
                ship.getSquareList().get(i).setX(sX + i);
                ship.getSquareList().get(i).setSquareStatus(SquareStatus.SHIP);
            } else {
                board.getBoard()[sY + i][sX].setSquareStatus(SquareStatus.SHIP);
                ship.getSquareList().get(i).setY(sY + i);
                ship.getSquareList().get(i).setX(sX);
                ship.getSquareList().get(i).setSquareStatus(SquareStatus.SHIP);
            }
        }
    }

    private boolean validatePlacement(Square square, Board board, Ship ship, boolean horizontal) {
        if (horizontal) {
            if (!checkHorizontal(square, board, ship)) return false;
        } else {
            if (!checkVertical(square, board, ship)) return false;
        }
        return true;
    }

    private boolean checkHorizontal(Square square, Board board, Ship ship) {
        int sY = square.getY();
        int sX = square.getX();
        if (square.getX() != 0) if (board.getBoard()[sY][sX - 1].getSquareStatus() != SquareStatus.EMPTY) return false;
        if (square.getX() < board.getBoard().length - ship.getShipType().getLength() - 1)
            if (board.getBoard()[sY][sX + ship.getShipType().getLength()].getSquareStatus() != SquareStatus.EMPTY)
                return false;
        for (int i = 0; i < ship.getSquareList().size(); i++) {
            if (board.getBoard()[sY][sX + i].getSquareStatus() != SquareStatus.EMPTY) return false;
            if (sY > 0) if (board.getBoard()[sY - 1][sX + i].getSquareStatus() != SquareStatus.EMPTY) return false;
            if (sY < board.getBoard().length - 1)
                if (board.getBoard()[sY + 1][sX + i].getSquareStatus() != SquareStatus.EMPTY) return false;
        }
        return true;
    }

    private boolean checkVertical(Square square, Board board, Ship ship) {
        int sY = square.getY();
        int sX = square.getX();
        if (square.getY() != 0) if (board.getBoard()[sY - 1][sX].getSquareStatus() != SquareStatus.EMPTY) return false;
        if (square.getY() < board.getBoard().length - ship.getShipType().getLength() - 1)
            if (board.getBoard()[sY + ship.getShipType().getLength()][sX].getSquareStatus() != SquareStatus.EMPTY)
                return false;
        for (int i = 0; i < ship.getSquareList().size(); i++) {
            if (board.getBoard()[sY + i][sX].getSquareStatus() != SquareStatus.EMPTY) return false;
            if (sX > 0) if (board.getBoard()[sY + i][sX - 1].getSquareStatus() != SquareStatus.EMPTY) return false;
            if (sX < board.getBoard().length - 1)
                if (board.getBoard()[sY + i][sX + 1].getSquareStatus() != SquareStatus.EMPTY) return false;
        }
        return true;
    }

    private boolean isHorizontal() {
        Random random = new Random();
        return random.nextBoolean();
    }

    private Square getStartPos(Board board, Ship ship, boolean hor) {
        Random random = new Random();
        Square square;
        if (hor) {
            int startY = random.nextInt(board.getBoard().length);
            int startX = random.nextInt(board.getBoard().length - ship.getShipType().getLength());
            square = new Square(startY, startX);
        } else {
            int startY = random.nextInt(board.getBoard().length - ship.getShipType().getLength());
            int startX = random.nextInt(board.getBoard().length);
            square = new Square(startY, startX);
        }
        return square;
    }

    /**********************************************************************/
    /*  MANUAL GENERATOR */

    /**********************************************************************/

    private List<Ship> generateManualFleet(Board board) {
        List<Ship> fleet = new ArrayList<>();
        for (int i = 0; i < fleetSize; i++) {
            ShipType type = getShipType(i);
            Ship newShip = createManualShip(board, type);
            fleet.add(newShip);
        }
        return fleet;
    }

    private Ship createManualShip(Board board, ShipType type) {
        List<Square> squares = new ArrayList<>();
        Map<int[], Boolean> coordinates = input.askForShipPlacing(board.getBoard());
        boolean isHorizontal = coordinates.values().contains(false);
        int startCoordinateY = 0;
        int startCoordinateX = 0;
        for (int[] key : coordinates.keySet()) {
            startCoordinateY = key[0];
            startCoordinateX = key[1];
        }

        while (!doesOverlap(board, isHorizontal, type.getLength(), startCoordinateX, startCoordinateY)) {
            isHorizontal = coordinates.values().contains(false);
            for (int[] key : coordinates.keySet()) {
                startCoordinateY = key[0];
                startCoordinateX = key[1];
            }
        }

        if (isHorizontal) {
            for (int i = 0; i < type.getLength(); i++) {
                Square square = new Square(i + startCoordinateY, startCoordinateX, SquareStatus.SHIP);
                squares.add(square);
            }
        } else {
            for (int i = 0; i < type.getLength(); i++) {
                Square square = new Square(startCoordinateY, i + startCoordinateX, SquareStatus.SHIP);
                squares.add(square);
            }
        }

        Ship ship = new Ship(squares);
        ship.setShipType(type);
        placeShip(board, ship);
        return ship;
    }

    private ShipType getShipType(int num) {
        ShipType type = null;
        switch (num) {
            case 0:
                type = ShipType.CARRIER;
                break;
            case 1:
            case 2:
                type = ShipType.BATTLESHIP;
                break;
            case 3:
            case 4:
            case 5:
                type = ShipType.CRUISER;
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                type = ShipType.SUBMARINE;
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                type = ShipType.DESTROYER;
        }
        return type;
    }


}
