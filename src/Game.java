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
        this.board = new Board(69, 7);
        this.player1 = new Player("EMPLOYEE", board, 1);
        this.player2 = new Player("THE Arsis", board, 2);
    }
    public Game(String playerName1, String playerName2) {
        this.turn = true;
        this.scanner = new Scanner(System.in);
        this.board = new Board(69, 7);
        this.player1 = new Player(playerName1, board, 1);
        this.player2 = new Player(playerName2, board, 2);
    }

    public void run() {
        while (true) {
            DiceRolls rand = new DiceRolls();
            rand.rollDice();

            Player currentPlayer = turn ? player1 : player2;
            Player otherPlayer = turn ? player2 : player1;

//            State state = new State(board,currentPlayer,otherPlayer,true,rand);
//            List<State> nextStates= state.getNextStates();
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
                handleDiceRollAndMove(currentPlayer, otherPlayer, rand);
            } else {
                System.out.println("sahozy : Invalid input!");
            }
            System.out.println("dddddddddddddddd : " + currentPlayer.getPlayRocks()[0].counter);
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

    private void handleDiceRollAndMoveForComputer(Player currentPlayer, Player otherPlayer, DiceRolls rand) {
        rand.printState();
        boolean notAllRocksOutBoard = false;

        for (PlayRock rock : otherPlayer.getPlayRocks()) {

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
        System.out.println("Computer rolled a :");
        rand.printState();
        PlayRock[] availableRocks = otherPlayer.getPlayRocks();


        int rockNumber;

        String diceResult = rand.countOnesAndNameState();
        if (diceResult.equals("Dest") || diceResult.equals("Bunja")) {
            ////////////////////////

            System.out.println("Sahozy : Add a new rock to the board\n2. Move an available rock 1 position");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("Sahozy : a new playRock will be added for : " + otherPlayer.getName());
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

    private void getComputerMove() {
//        System.out.println("kkk");
        DiceRolls rand2 = new DiceRolls();
        rand2.rollDice();

        Player user = turn ? player1 : player2;
        Player computer = turn ? player2 : player1;


        if (computer.hasWon(computer.getPlayRocks()))
        {
            scanner.close();
            System.out.println("sahozy : " + computer.getName() + " has won the game!");
            // break;
        }
        handleDiceRollAndMoveForComputer(user, computer, rand2);



    }

    public void runForOnePlayer(){
        while (true){
            System.out.println("___________________"+player1.getName()+"___________________");
//            getUserMove();
            DiceRolls rand1 = new DiceRolls();
            rand1.rollDice();

            Player user = turn ? player1 : player2;
            Player computer = turn ? player2 : player1;


            if (user.hasWon(user.getPlayRocks()))
            {
                scanner.close();
                System.out.println("sahozy : " + user.getName() + " has won the game!");
                break;
            }

            System.out.println("sahozy : Press 1 to roll the dice or 0 to exit : player : " + user.getName());
            int input = scanner.nextInt();

            if (input == 0) {
                break;
            } else if (input == 1) {
                handleDiceRollAndMove(user, computer, rand1);
            } else {
                System.out.println("sahozy : Invalid input!");
            }
            turn = !turn;
            if(turn = !turn){
                System.out.println("___________________Computer___________________");
                getComputerMove();
            }



        }}

}