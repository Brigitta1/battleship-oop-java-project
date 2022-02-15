package util;

import view.Display;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HighScore {
    private Display display;

    public HighScore(Display display) {
        this.display = display;
    }

    public void createFile() {
        try {
            File myObj = new File("high_score.txt");
            if (myObj.createNewFile()) {
                display.printMessage("High score will be saved.");
            }
        } catch (IOException e) {
            display.printMessage("An error occurred during saving high score.");
            e.printStackTrace();
        }
    }

    public void writeToFile(String highScore) {
        try {
            FileWriter myWriter = new FileWriter("high_score.txt");
            myWriter.write(highScore);
            myWriter.close();
            //  display.printMessage("Successfully wrote to the file.");
        } catch (IOException e) {
            display.printMessage("An error occurred during saving high score.");
            e.printStackTrace();
        }
    }

    public String readFromFile() {
        String highScore = "";
        try {
            File myObj = new File("high_score.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                highScore += data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            display.printMessage("An error occurred.");
            e.printStackTrace();
        }
        return highScore;
    }
}

