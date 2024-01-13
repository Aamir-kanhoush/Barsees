public class DiceRolls {
    private int[] rolls;

    public DiceRolls() {
        this.rolls = new int[6];
    }

    public void rollDice() {
        for (int i = 0; i < 6; i++) {
            rolls[i] = (int) (Math.random() * 2) + 1;
        }
    }

    public String countOnesAndNameState() {//counting the downward facing shells
        int count = 0;

        for (int i = 0; i < 6; i++) {
            if (rolls[i] == 1) {
                count++;
            }
        }
        switch (count) {
            case 1:
                return "Dest";
            case 2:
                return "Duwag";
            case 3:
                return "Thalatha";
            case 4:
                return "Arba'a";
            case 5:
                return "Bunja";
            case 6:
                return "Shakka";
            default:
                return "Bara";
        }
//        return "Dest";
    }

    public void printRolls() {
        for (int i = 0; i < 6; i++) {
            System.out.print(rolls[i] + " ");
        }
        System.out.println();
    }

    public void printState() {
        String state = countOnesAndNameState();
        System.out.println(state);
    }

    public String getDiceState() {
        return countOnesAndNameState();
    }
}
