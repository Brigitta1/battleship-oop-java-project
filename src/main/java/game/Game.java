package game;

import board.Board;
import player.Player;
import ship.Ship;
import square.Square;
import square.SquareStatus;
import util.HighScore;
import view.Display;

public class Game {
    private int actPlayer = 0;
    private Player[] player;
    private Board[] board;
    private Display display;
    private Input input;
    private int[] score;
    private HighScore highScore;

    public Game(Display display, Input input, Player[] player, Board[] board, HighScore highScore) {
        this.display = display;
        this.input = input;
        this.player = player;
        this.board = board;
        this.score = new int[]{0, 0};
        this.highScore = highScore;
    }

    public void runGame() {
        do {
            display.printMessage("Score: " + score[actPlayer]);
            display.printBoard(board[actPlayer == 0 ? 1 : 0], true);
            display.printMessage("Player " + actPlayer + " comes");
            makeShot(actPlayer);
            display.printMessage("Here is the result: ");
            display.printBoard(board[actPlayer == 0 ? 1 : 0], true);

            try {
                Thread.sleep(2000); //time is in ms (1000 ms = 1 second)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            actPlayer = actPlayer == 0 ? 1 : 0;
        } while (player[0].isAlive() && player[1].isAlive());
        displayWinner();
        saveHighScore(score[0] > score[1] ? score[0] : score[1]);
    }

    private void saveHighScore(int score) {
        highScore.createFile();
        highScore.writeToFile(String.valueOf(score));
    }

    private void makeShot(int actPlayer) {
        int[] pos = input.askForNextShot(board[actPlayer == 0 ? 1 : 0].getBoard());
        Square shot = new Square(pos[0], pos[1], SquareStatus.HIT);
        player[actPlayer == 0 ? 1 : 0].handleShot(shot);
        Ship ship = player[actPlayer == 0 ? 1 : 0].getShipByPosition(shot);
        if (ship != null) setScore(ship.getShotStatus(shot));
        board[actPlayer == 0 ? 1 : 0].handleShotInBoard(shot, ship); // -- set the board
    }

    private void setScore(SquareStatus st) {
        switch (st) {
            case HIT -> {
                score[actPlayer]++;
                break;
            }
            case SUNK -> {
                score[actPlayer] += 10;
            }
        }
    }

    private void displayWinner() {
        if (player[0].isAlive() && player[1].isAlive()) display.printMessage("Game stopped");
        else if (player[0].isAlive()) display.printMessage("Winner is: Player 0");
        else display.printMessage("Winner is: Player 1");
    }
}

