public class PlayRock {
    private Player player;
    private int position;
    boolean tastee7;
    int counter;
    boolean isInTheKitchen = true;
    boolean finish;
    private Board board;


    public PlayRock(Player player, Board board) {
        this.player = player;
        this.board = board;
        this.position = -1;
        this.tastee7 = false;
        this.counter = -1;
    }
    public PlayRock(PlayRock another) {
        this.player = another.player; // Assume Player is immutable or you handle it appropriately
        this.board = another.board; // Assume Board is immutable or you handle it appropriately
        this.position = another.position;
        this.tastee7 = another.tastee7;
        this.counter = another.counter;
        this.isInTheKitchen = another.isInTheKitchen;
        this.finish = another.finish;
    }
    public Player getPlayer() {
        return player;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;

    }

    @Override
    public String toString() {
        return "PlayRock{" +
                "player=" + player.getName() +
                ", position=" + position +
                ", tastee7=" + tastee7 +
                ", counter=" + counter +
                ", isInTheKitchen=" + isInTheKitchen +
                ", finish=" + finish +
                '}';
    }

}

