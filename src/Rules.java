public class Rules {
    static int[] safeBlocks = {6};

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
    public static boolean win(Player player)
    {
        for (PlayRock playRock :
                player.getPlayRocks()) {
            if (!playRock.finish)
            {
                return false;
            }
        }
        return true;
    }


}
