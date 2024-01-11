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
    public Player(Player another) {
        this.name = another.name;
        this.board = another.board; // Assuming Board is immutable or handled separately
        this.id = another.id;
        this.playRocks = new PlayRock[another.playRocks.length];
        for (int i = 0; i < another.playRocks.length; i++) {
            // Use the copy constructor of PlayRock (which you need to implement)
            this.playRocks[i] = new PlayRock(another.playRocks[i]);
        }
    }

    public String getName() {
        return name;
    }

    public PlayRock[]  getPlayRocks() {
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