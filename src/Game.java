import java.util.ArrayList;
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
            Player currentPlayer = turn ? player1 : player2;
            Player otherPlayer = turn ? player2 : player1;

            DiceRolls rand = new DiceRolls();
            rand.rollDice();


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
            System.out.println("--------------------------------------------------\n");
            turn = !turn;
        }

        scanner.close();
        System.out.println("sahozy : Game over. Thank you for playing!");
    }


    private void handleDiceRollAndMove(Player currentPlayer, Player otherPlayer, DiceRolls rand) {

        boolean AllRocksInBoard = false;
        List<DiceRolls> wel3t = new ArrayList<>();
        for (PlayRock rock : currentPlayer.getPlayRocks()) {

            if (rock.getPosition() != -1) {
                System.out.println(rock.getPosition());
                AllRocksInBoard = true;
                break;
            }
        }

        if (!AllRocksInBoard) {
            for (int j = 0; j <= 2; j++) {
                rand.rollDice();
                rand.printState();
                String diceState = rand.countOnesAndNameState();
                if (diceState.equals("Dest") || diceState.equals("Bunja")) {
                    break;
                } else {
                    if (diceState.equals("Shakka") || diceState.equals("Bara")) {
                        if (j == 2) {
                            j--;
                            continue;
                        }
                    } else if (rand.countOnesAndNameState() != "Dest" && rand.countOnesAndNameState() != "Bunja") {
                        if (rand.countOnesAndNameState() == "Arba'a" || rand.countOnesAndNameState() == "Thalatha" || rand.countOnesAndNameState() == "Duwag") {
                            if (j == 2) {
                                return;
                            }
                            continue;

                        } else {
                            System.out.println("end");
                            return;
                        }
                    } else {
                        return;
                    }
                }


            }

        }


        System.out.println("You rolled a :");
        rand.printState();


        PlayRock[] availableRocks = currentPlayer.getPlayRocks();
        int rockNumber;

        wel3t.add(rand);

        String diceResult = rand.countOnesAndNameState();
        HandleFrequentDice(diceResult,wel3t,AllRocksInBoard,rand);

//        while (wel3t.size() < 10 && AllRocksInBoard) {
//            diceResult = rand.getDiceState();
//            if (diceResult.equals("Shakka") || diceResult.equals("Dest") ||
//                    diceResult.equals("Bunja") || diceResult.equals("Bara")) {
//
//                DiceRolls temp = new DiceRolls(); // Create a new DiceRolls object
//                System.out.println("Re DiceRoll :");
//                temp.rollDice(); // Roll the dice for the new object
//                diceResult=temp.countOnesAndNameState();
//
//                System.out.println("ya khara");
//                temp.printState();
//                wel3t.add(temp); // Add the new object to the list
//
//                if (diceResult.equals("Shakka") || diceResult.equals("Dest") ||
//                        diceResult.equals("Bunja") || diceResult.equals("Bara")) {
//                    System.out.println("shno hatha");
//                    DiceRolls temp2 = new DiceRolls();
//                    temp2.rollDice();
//                    diceResult=temp2.countOnesAndNameState();
//
//                    temp2.printState();// Roll the dice for the new object
//                    wel3t.add(temp2); // Add the new object to the list
//                    rand=temp2;
//
//                } else {
//
//                    break;
//                }
//            }
//            else {
//
//                break;
//            }
//        }
        System.out.println(" wel3t size  :" + wel3t.size());
        for (DiceRolls w : wel3t) {
            w.printState();
        }

        int counter =0;
        boolean MultiDice = true;
        ChooseFrequentDice(MultiDice,wel3t,counter,availableRocks,currentPlayer);
//        while(MultiDice && wel3t.size()>=2){
//            System.out.println("Couter value befor:" + counter);
//            // Display all DiceRolls in the wel3t list
//            System.out.println(" Wel3t OBJECTS : ");
//            for (DiceRolls diceRoll : wel3t) {
//                diceRoll.printState();
//            }
//            availableRocks = currentPlayer.getPlayRocks();
//            for (int i = 0; i < availableRocks.length; i++) {
//                if (!availableRocks[i].finish && availableRocks[i].getPosition() != -1) {
//                    System.out.println("Rock " + (i + 1) + ": Position " + availableRocks[i]);
//                }
//            }
//
//            // Let the player choose a rock
//            System.out.println("Choose a rock:");
//            int rockChoice = scanner.nextInt();
//            PlayRock chosenRock = availableRocks[rockChoice - 1];
//            // Give the player an option to choose a DiceRoll
//            System.out.println("Choose a DiceRoll:");
//            int diceChoice = scanner.nextInt();
//            DiceRolls chosenDiceRoll = wel3t.get(diceChoice);
//            String DS= chosenDiceRoll.countOnesAndNameState();
//
//            Move.DoMove(chosenRock,board,DS);
//
//           // wel3t.set(diceChoice,chosenDiceRoll);
//
//            counter++;
//            System.out.println("Couter value after:" + counter);
//            if (counter>=wel3t.size()){MultiDice=false;}
//        }

        if (diceResult.equals("Dest") || diceResult.equals("Bunja")) {

            System.out.println("Sahozy : \n1- Add a new rock to the board\n2. Move an available rock 1 position");
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
        }
        else {
            for (int i = 0; i < availableRocks.length; i++) {
                if (!availableRocks[i].finish && availableRocks[i].getPosition() != -1) {
                    System.out.println("Rock " + (i + 1) + ": Position " + availableRocks[i]);
                }
            }
            System.out.println("Choose rock to move :");
            rockNumber = scanner.nextInt();
            Move.DoMove(availableRocks[rockNumber - 1], board, diceResult);
        }


        boolean x = Rules.kill(board, availableRocks[rockNumber - 1], otherPlayer.getPlayRocks());
        board.printBoard();
    }

    private void HandleFrequentDice (String diceResult,List<DiceRolls>wel3t,boolean AllRocksInBoard,DiceRolls rand){
        while (wel3t.size() < 10 && AllRocksInBoard) {
            diceResult = rand.getDiceState();
            if (diceResult.equals("Shakka") || diceResult.equals("Dest") ||
                    diceResult.equals("Bunja") || diceResult.equals("Bara")) {

                DiceRolls temp = new DiceRolls(); // Create a new DiceRolls object
                System.out.println("Re DiceRoll :");
                temp.rollDice(); // Roll the dice for the new object
                diceResult=temp.countOnesAndNameState();

                System.out.println("ya khara");
                temp.printState();
                wel3t.add(temp); // Add the new object to the list

                if (diceResult.equals("Shakka") || diceResult.equals("Dest") ||
                        diceResult.equals("Bunja") || diceResult.equals("Bara")) {
                    System.out.println("shno hatha");
                    DiceRolls temp2 = new DiceRolls();
                    temp2.rollDice();
                    diceResult=temp2.countOnesAndNameState();

                    temp2.printState();// Roll the dice for the new object
                    wel3t.add(temp2); // Add the new object to the list
                    rand=temp2;

                } else {

                    break;
                }
            }
            else {

                break;
            }
        }
    }
    private void ChooseFrequentDice (boolean MultiDice,List<DiceRolls>wel3t,int counter,PlayRock[]availableRocks,Player currentPlayer){
        while(MultiDice && wel3t.size()>=2){
            System.out.println("Couter value befor:" + counter);
            // Display all DiceRolls in the wel3t list
            System.out.println(" Wel3t OBJECTS : ");
            for (DiceRolls diceRoll : wel3t) {
                diceRoll.printState();
            }
            availableRocks = currentPlayer.getPlayRocks();
            for (int i = 0; i < availableRocks.length; i++) {
                if (!availableRocks[i].finish && availableRocks[i].getPosition() != -1) {
                    System.out.println("Rock " + (i + 1) + ": Position " + availableRocks[i]);
                }
            }

            // Let the player choose a rock
            System.out.println("Choose a rock:");
            int rockChoice = scanner.nextInt();
            PlayRock chosenRock = availableRocks[rockChoice - 1];
            // Give the player an option to choose a DiceRoll
            System.out.println("Choose a DiceRoll:");
            int diceChoice = scanner.nextInt();
            DiceRolls chosenDiceRoll = wel3t.get(diceChoice);
            String DS= chosenDiceRoll.countOnesAndNameState();

            Move.DoMove(chosenRock,board,DS);

            // wel3t.set(diceChoice,chosenDiceRoll);

            counter++;
            System.out.println("Couter value after:" + counter);
            if (counter>=wel3t.size()){MultiDice=false;}
        }
    }
}