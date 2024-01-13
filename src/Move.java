import java.util.Scanner;

public class Move {

    public static void DoMove(PlayRock playRock, Board board, String diceResult) {
        String x = diceResult;
        boolean aa;
        State temp=new State(board,playRock.getPlayer(),playRock.getPlayer(),new DiceRolls());
        switch (x) {
            case "Dest": {
                if (playRock.isInTheKitchen) {
                    aa=temp.canMoveRock(playRock,10);
                    System.out.println("dest if : "+aa);
                    board.movePlayRockInKitchen(playRock, 10, board.getPlayerKitchen(playRock));

                } else {
                    aa=temp.canMoveRock(playRock,10);
                    System.out.println("dest else : "+aa);
                    board.movePlayRockInPath(playRock, 10, board.getPath());

                }
            }
            break;
            case "Bunja": {
                if (playRock.isInTheKitchen) {
                    aa=temp.canMoveRock(playRock,10);
                    System.out.println("bunja if : "+aa);
                    board.movePlayRockInKitchen(playRock, 10, board.getPlayerKitchen(playRock));


                } else {
                    aa=temp.canMoveRock(playRock,10);
                    System.out.println("bunja else : "+aa);
                    board.movePlayRockInPath(playRock, 10, board.getPath());


                }
            }
            break;
            case "Duwag":
                if (playRock.isInTheKitchen) {
                    aa=temp.canMoveRock(playRock,2);
                    System.out.println("duwag if : "+aa);
                    board.movePlayRockInKitchen(playRock, 2, board.getPlayerKitchen(playRock));


                } else {
                    aa=temp.canMoveRock(playRock,2);
                    System.out.println("duwag else : "+aa);
                    board.movePlayRockInPath(playRock, 2, board.getPath());


                }
                break;
            case "Thalatha":
                if (playRock.isInTheKitchen) {
                    aa=temp.canMoveRock(playRock,3);
                    System.out.println("thalatha if : "+aa);
                    board.movePlayRockInKitchen(playRock, 3, board.getPlayerKitchen(playRock));


                } else {
                    aa=temp.canMoveRock(playRock,3);
                    System.out.println("thalatha else : "+aa);
                    board.movePlayRockInPath(playRock, 3, board.getPath());


                }
                break;
            case "Arba'a":
                if (playRock.isInTheKitchen) {
                    aa=temp.canMoveRock(playRock,4);
                    System.out.println("Arba'a if : "+aa);
                    board.movePlayRockInKitchen(playRock, 4, board.getPlayerKitchen(playRock));


                } else {
                    aa=temp.canMoveRock(playRock,4);
                    System.out.println("Arba'a else : "+aa);
                    board.movePlayRockInPath(playRock, 4, board.getPath());


                }
                break;
            case "Bara":
                if (playRock.isInTheKitchen) {
                    aa=temp.canMoveRock(playRock,12);
                    System.out.println("bara if : "+aa);
                    board.movePlayRockInKitchen(playRock, 12, board.getPlayerKitchen(playRock));


                } else {
                    aa=temp.canMoveRock(playRock,12);
                    System.out.println("bara else : "+aa);
                    board.movePlayRockInPath(playRock, 12, board.getPath());


                }
                break;
            case "Shakka":
                if (playRock.isInTheKitchen) {
                    aa=temp.canMoveRock(playRock,6);
                    System.out.println("shakka if : "+aa);
                    board.movePlayRockInKitchen(playRock, 6, board.getPlayerKitchen(playRock));


                } else {
                    aa=temp.canMoveRock(playRock,6);
                    System.out.println("shakka else : "+aa);
                    board.movePlayRockInPath(playRock, 6, board.getPath());


                }
                break;
        }
    }

    public static void Uncle(PlayRock playRock, Board board, int choice) {

        if (choice == 1) {//new piece of cake
            board.setPieceInPlayerKitchen(0, playRock);
            playRock.setPosition(0);
//            board.printBoard();

        } else if (choice == 2) {//move one
            if (playRock.isInTheKitchen) {
                board.movePlayRockInKitchen(playRock, 1, board.getPlayerKitchen(playRock));
            } else {
                board.movePlayRockInPath(playRock, 1, board.getPath());
            }
        }

    }
}


