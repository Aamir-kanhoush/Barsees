public class Player {
    private String name;
    private PlayRock[] playRocks;
    private Board board;
    int id;

    public Player(String name, Board board, int id) {
        this.name = name;
        this.board = board;
        this.playRocks = new PlayRock[4];
        this.id = id;
        for (int i = 0; i < 4; i++) {
            playRocks[i] = new PlayRock(this, board);
        }
    }

    public Player(Player player) {
        this.id = player.id;

        this.playRocks = new PlayRock[player.playRocks.length];
        for (int i = 0; i < player.playRocks.length; i++) {
            this.playRocks[i] = new PlayRock(player.playRocks[i]);
        }
    }


    public String getName() {
        return name;
    }

    public PlayRock[] getPlayRocks() {
        return playRocks;
    }

    public static boolean hasWon(PlayRock[] playRocks) {
        for (PlayRock rock : playRocks) {
            if (!rock.finish) {
                return false;
            }
        }
        return true;
    }
}