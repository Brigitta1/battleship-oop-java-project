import board.Board;
import board.BoardFactory;
import game.Game;
import game.Input;
import player.Player;
import util.HighScore;
import view.Display;

public class Battleship {
    private final int PLAYERNUMBER = 2;
    private final int MANUALPLACEMENT = 0;
    private final int RANDOMPLACEMENT = 1;
    private final int BOARDSIZE = 15;
    private Display display;
    private Input input;
    private Game game;
    private HighScore highScore;

    public Battleship() {
        this.display = new Display();
        this.input = new Input(display);
        this.game = null;
        highScore = new HighScore(display);
    }

    public void startGame() {
        display.printIntro();
        do {
            String userInput = input.askForMainMenu();
            switch (userInput) {
                case "1":
                    startNewHumanGame(RANDOMPLACEMENT);  //Human-human random replacement
                    break;
                case "2":
                    startNewHumanGame(MANUALPLACEMENT);  //Human-human manual replacement
                    break;
               /* case "3":
                    startNewHumanAiGame(MANUALPLACEMENT);  //Human-AI manual replacement ?
                    break;
                case "4":
                    startNewHumanAiGame(RANDOMPLACEMENT); //Human-AI random replacement ?
                    break;*/
                case "3":
                    showHighScore(); //- read and display high score
                    break;
                case "4":
                    System.exit(0);
                    break;
            }
        } while (true);
    }

    private void startNewHumanGame(int placement) {
        Player[] player = new Player[PLAYERNUMBER];
        Board[] board = new Board[PLAYERNUMBER];
        BoardFactory boardFactory = new BoardFactory(input);
        player[0] = new Player(null);
        player[1] = new Player(null);
        board[0] = new Board(BOARDSIZE);
        board[1] = new Board(BOARDSIZE);


        boardFactory.initBoard(player[0], board[0], placement);  //- create and place the ships on the board and add to player
        boardFactory.initBoard(player[1], board[1], placement);
        game = new Game(display, input, player, board, highScore);
        display.printBoard(board[0], false);
        display.printBoard(board[1], false);
        game.runGame();
    }

    private void startNewHumanAiGame(int placement) {
    }

    public void showHighScore() {
        String score = highScore.readFromFile();
        System.out.println("The highest score is: " + score);
    }

}

