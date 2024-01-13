import java.util.List;
import java.util.Scanner;

public class Game {
    private boolean turn;
    private Scanner scanner;
    private Board board;
    private Player player1;
    private Player player2;

    public Game() {
        this.turn = true;
        this.scanner = new Scanner(System.in);
        this.board = new Board(12, 4);
        this.player1 = new Player("EMPLOYEE", board, 1);
        this.player2 = new Player("THE Arsis", board, 2);
    }

    public void run() {
        while (true) {
            DiceRolls rand = new DiceRolls();
            rand.rollDice();
            rand.printState();
            DiceRolls rand1 = new DiceRolls();
            rand1.rollDice();
            rand1.printState();

            Player currentPlayer = turn ? player1 : player2;
            Player otherPlayer = turn ? player2 : player1;
            if (currentPlayer.hasWon(currentPlayer.getPlayRocks())) {
                scanner.close();
                System.out.println("sahozy : " + currentPlayer.getName() + " has won the game!");
                break;
            }
            System.out.println("sahozy : Press 1 to roll the dice or 0 to exit : player : " + currentPlayer.getName());
            int input = scanner.nextInt();

            if (input == 0) {
                break;
            } else if (input == 1) {
//                handleDiceRollAndMove(currentPlayer, otherPlayer, rand);
            } else {
                System.out.println("sahozy : Invalid input!");
            }
//            turn = !turn;
        }

        scanner.close();
        System.out.println("sahozy : Game over. Thank you for playing!");
    }

    private void handleDiceRollAndMove(Player currentPlayer, Player otherPlayer, DiceRolls rand) {
        rand.printState();
        boolean notAllRocksOutBoard = false;

        for (PlayRock rock : currentPlayer.getPlayRocks()) {

            if (rock.getPosition() != -1) {
                System.out.println(rock.getPosition());
                notAllRocksOutBoard = true;
                break;
            }


        }
        if (!notAllRocksOutBoard) {
            for (int j = 0; j <= 1; j++) {
                if (rand.countOnesAndNameState() != "Dest" && rand.countOnesAndNameState() != "Bunja") {
                    rand.rollDice();
                    rand.printState();
                }
            }
            if (rand.countOnesAndNameState() != "Dest" && rand.countOnesAndNameState() != "Bunja") {
                return;
            }
        }
        System.out.println("You rolled a :");
        rand.printState();

        PlayRock[] availableRocks = currentPlayer.getPlayRocks();


        int rockNumber;

        String diceResult = rand.countOnesAndNameState();
        if (diceResult.equals("Dest") || diceResult.equals("Bunja")) {
            System.out.println("Sahozy : Add a new rock to the board\n2. Move an available rock 1 position");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("Sahozy : a new playRock will be added for : " + currentPlayer.getName());
                for (int i = 0; i < availableRocks.length; i++) {
                    if (availableRocks[i].getPosition() == -1) {
                        Move.Uncle(availableRocks[i], board, choice);
                        break;
                    }
                }
            } else if (choice == 2) {
                System.out.println("Sahzoy : choose playRock to move by 1");
                for (int i = 0; i < availableRocks.length; i++) {
                    if (availableRocks[i].getPosition() != -1) {
                        System.out.println("Rock " + (i + 1) + ": Position " + availableRocks[i]);
                    }
                }

                rockNumber = scanner.nextInt();
                Move.Uncle(availableRocks[rockNumber - 1], board, choice);
            }
            //patata
            System.out.println("sahozy : Choose a rock number to move:");
            for (int i = 0; i < availableRocks.length; i++) {
                if (!availableRocks[i].finish && availableRocks[i].getPosition() != -1) {
                    System.out.println("Rock " + (i + 1) + ": Position " + availableRocks[i]);
                }
            }
            rockNumber = scanner.nextInt();
            Move.DoMove(availableRocks[rockNumber - 1], board, diceResult);
        } else {
            for (int i = 0; i < availableRocks.length; i++) {
                if (!availableRocks[i].finish && availableRocks[i].getPosition() != -1) {
                    System.out.println("Rock " + (i + 1) + ": Position " + availableRocks[i]);
                }
            }
            rockNumber = scanner.nextInt();
            Move.DoMove(availableRocks[rockNumber - 1], board, diceResult);
        }
        boolean x = Rules.kill(board, availableRocks[rockNumber - 1], otherPlayer.getPlayRocks());
        board.printBoard();
    }
}