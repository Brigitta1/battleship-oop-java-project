package game;

import square.Square;
import square.SquareStatus;
import view.Display;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Input {

    private final int ASCII_DEC_CODE_UPPERCASE_LETTER_A = 65;
    private final int INDEX_CORRECTION = 1;
    private Display display;

    public Input(Display display) {
        this.display = display;
    }

    public String askForMainMenu() {
        String userInput;
        boolean validMenu;
        do {
            validMenu = true;
            display.printMenu();
            userInput = readInput("Which menu item do you choose: ");
            if (!isMenuInputValid(userInput)) {
                validMenu = false;
                display.printMessage("No such menu item. Please retry.\n");
            }
        } while (!validMenu);
        return userInput;
    }

    public Map<int[], Boolean> askForShipPlacing(Square[][] board) {
        Map<int[], Boolean> dataForShipPlacing = new HashMap<>();

        int[] coordinate = coordinateForShipPlacing(board);
        Boolean horizontal = isHorizontal();
        dataForShipPlacing.put(coordinate, horizontal);

        return dataForShipPlacing;
    }

    private Boolean isHorizontal() {
        String userInput;
        boolean validDirection;

        do {
            validDirection = true;
            userInput = readInput("Should the boat be horizontal? (y or n): ");
            if (!isDirectionInputFormatValid(userInput)) {
                display.printMessage("You can only give Y or N. Please retry.");
                validDirection = false;
            }
        } while (!validDirection);

        return userInput.equals("Y");
    }

    private int[] coordinateForShipPlacing(Square[][] board) {
        int[] coordinate;
        boolean validCoordinate;
        do {
            validCoordinate = true;
            String userInput = readInput("Enter the starting coordinate of your ship: ");

            if (!isCoordinatesInputFormatValid(userInput)) {
                display.printMessage("Invalid input format. Please retry.");
                validCoordinate = false;
            }

            coordinate = convertToCoordinate(userInput);
            int y = coordinate[0];
            int x = coordinate[1];
            if (!isInBoard(board, y, x)) {
                display.printMessage("Your shot is outside the board. Please retry.");
                validCoordinate = false;
            }
            if (!isEmptySquare(board, y, x)) {
                display.printMessage("You have already placed a ship here. Please retry.");
                validCoordinate = false;
            }
        }
        while (!validCoordinate);

        return coordinate;
    }

    public int[] askForNextShot(Square[][] board) {
        int[] coordinate;
        boolean validMove;
        do {
            validMove = true;
            String userInput = readInput("Enter the coordinate where you want to shoot.");

            if (!isCoordinatesInputFormatValid(userInput)) {
                display.printMessage("Invalid input format. Please retry.");
                validMove = false;
            }
            coordinate = convertToCoordinate(userInput);
            int y = coordinate[0];
            int x = coordinate[1];
            if (!isInBoard(board, y, x)) {
                display.printMessage("Your shot is outside the board. Please retry.");
                validMove = false;
            }

            if (validMove && !notShotYet(board, y, x)) {
                display.printMessage("You have already shot here. Please retry.");
                validMove = false;
            }
        }
        while (!validMove);
        return coordinate;
    }

    private String readInput(String prompt) {
        display.printMessage(prompt + " ");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine().toUpperCase();
        if (userInput.equals("QUIT")) System.exit(0);
        return userInput;
    }

    private int[] convertToCoordinate(String userInput) {
        int[] coordinates = new int[2];
        coordinates[0] = (userInput.charAt(0) - ASCII_DEC_CODE_UPPERCASE_LETTER_A);
        coordinates[1] = (Integer.parseInt(userInput.substring(1)) - INDEX_CORRECTION);
        return coordinates;
    }

    private boolean isMenuInputValid(String userInput) {
        return userInput.matches("[1-9]");
    }

    private boolean isCoordinatesInputFormatValid(String userInput) {
        return userInput.matches("[A-Z]\\d{1,2}$");
    }

    private boolean isDirectionInputFormatValid(String userInput) {
        return userInput.matches("[NYny]$");
    }

    private boolean isInBoard(Square[][] board, int y, int x) {
        return (0 <= x && board[0].length > x && 0 <= y && board.length > y);
    }

    private boolean isEmptySquare(Square[][] board, int y, int x) {
        return (board[y][x].getSquareStatus().equals(SquareStatus.EMPTY));
    }

    private boolean notShotYet(Square[][] board, int y, int x) {
        return (board[y][x].getSquareStatus().equals(SquareStatus.EMPTY))
                || board[y][x].getSquareStatus().equals(SquareStatus.SHIP);
    }
}
