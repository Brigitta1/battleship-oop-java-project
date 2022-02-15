package ship;

import square.Square;
import square.SquareStatus;

import java.util.List;

public class Ship {
    private List<Square> squareList;
    private ShipType shipType;

    public Ship(List<Square> squareList) {
        this.squareList = squareList;
    }

    public List<Square> getSquareList() {
        return squareList;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public boolean isShipPosition(Square square) {
        if (squareList != null) {
            for (Square s : squareList) {
                if (s.getY() == square.getY() && s.getX() == square.getX()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSunk() {
        if (squareList != null) {
            for (Square s : squareList) {
                if (s.getSquareStatus() == SquareStatus.SHIP) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setShot(Square square) {
        if (squareList != null) {
            for (Square s : squareList) {
                if (s.getY() == square.getY() && s.getX() == square.getX()) {
                    s.setSquareStatus(SquareStatus.HIT);
                }
            }
        }
    }

    public SquareStatus getShotStatus(Square square) {
        if (squareList != null) {
            for (Square s : squareList) {
                if (s.getY() == square.getY() && s.getX() == square.getX()) {
                    return s.getSquareStatus();
                }
            }
        }
        return SquareStatus.MISSED;
    }

    public void setSunk() {
        if (squareList != null) {
            for (Square s : squareList) {
                if (s.getSquareStatus() == SquareStatus.HIT) {
                    s.setSquareStatus(SquareStatus.SUNK);
                }
            }
        }
    }

}
