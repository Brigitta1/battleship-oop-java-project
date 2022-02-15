package view;

import board.Board;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Display {
    public void printIntro() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/battleship.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);

            }
            try {
                Thread.sleep(2000); //time is in ms (1000 ms = 1 second)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printMenu() {
        String[] options = {
                "New game with random placement",
                "New game with manual placement",
                "Display high score",
                "Exit game\n",
        };

        for (int i = 0; i < options.length; i++) {
            System.out.format("(%d) %s", i + 1, options[i]);
            System.out.println();
        }
    }

    public void printBoard(Board board, boolean isEnemy) {
        System.out.println(board.boardToString(isEnemy));
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
