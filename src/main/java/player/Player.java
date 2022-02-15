package player;

import ship.Ship;
import square.Square;
import square.SquareStatus;

import java.util.List;

public class Player {
    private List<Ship> shipList;

    public Player(List<Ship> shipList) {
        this.shipList = shipList;
    }

    public List<Ship> getShipList() {
        return shipList;
    }

    public void setShipList(List<Ship> shipList) {
        this.shipList = shipList;
    }

    public Ship getShipByPosition(Square shot) {
        if (shipList != null) {
            for (Ship ship : shipList) {
                if (ship.isShipPosition(shot)) return ship;
            }
        }
        return null;
    }

    public SquareStatus getShotStatus(Square shot) {
        SquareStatus st = SquareStatus.MISSED;
        if (shipList != null) {
            for (Ship ship : shipList) {
                st = ship.getShotStatus(shot);
            }
        }
        return st;
    }

    public void handleShot(Square shot) {
        if (shipList != null) {
            for (Ship ship : shipList) {
                ship.setShot(shot);
                if (ship.isSunk()) {
                    ship.setSunk();
                }
            }
        }
    }

    public boolean isAlive() {
        if (shipList != null) {
            for (Ship ship : shipList) {
                if (!ship.isSunk()) return true;
            }
        }
        return false;
    }
}
