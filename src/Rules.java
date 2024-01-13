public class Rules {
    static int[] safeBlocks = {4, 15, 21, 32, 38, 49, 55, 66,};

    public static boolean kill(Board board, PlayRock playRock1, PlayRock[] playRock2) {
        boolean isSafe = false;
        if (playRock1.getPosition() != -1 && !playRock1.isInTheKitchen) {
            for (PlayRock rock : playRock2) {
                for (int i = 0; i < safeBlocks.length; i++) {
                    if (rock.getPosition() == safeBlocks[i]) {
                        isSafe = true;
                        break;
                    }
                }
                if (rock.getPosition() != -1 && !isSafe && !rock.isInTheKitchen) {
                    if (playRock1.getPosition() == rock.getPosition() && playRock1.getPlayer().id != rock.getPlayer().id) {
                        board.removePieceFromPath(rock.getPosition());
                        rock.setPosition(-1);
                        System.out.println("sahozy : اويلي عليك جيسوووس");
                        return true;
                    }
                }
            }
        }
        return false;

    }


    public static boolean isMaksora(Board board, PlayRock playRock1, PlayRock[] playRock2) {
        boolean isMaksora = false;
        if (playRock1.getPosition() != -1 && !playRock1.isInTheKitchen) {
            for (PlayRock rock : playRock2) {
                if (isSafeBlock(rock.getPosition())) {
                    for (int i = 0; i < safeBlocks.length; i++) {
                        if (rock.getPosition() == safeBlocks[i] && rock.getPosition() == playRock1.getPosition()) {
                            isMaksora = true;
                            System.out.println("you cant move this rock choose another if you had");
                        }
                    }

                    if (rock.getPosition() != -1 && !isSafeBlock(rock.getPosition()) && !rock.isInTheKitchen) {
                        isMaksora = false;

                    }
                }

            }

        }
        return isMaksora;
    }

    // isSafeBlock: to check if the position is in the list of safe blocks
    public static boolean isSafeBlock(int position) {

        for (int safeBlock : safeBlocks) {
            if (position == safeBlock) {
                return true;
            }
        }
        return false;
    }

}
