import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome To Compelrat Game"+'\n' + "Press 1 if you want to play \" Two player \" mode "
                +'\n'+"Press 2 if you want to play \" With Computer \" mode");
        Scanner scanner=new Scanner(System.in);
        String i = scanner.next();
        ;
        switch (i) {
            case "1": {
                new Game().run();
            }
            break;
            case "2": {
                System.out.println("Enter your name");
                String name = scanner.next();
                new Game(name,"Computer").runForOnePlayer();
            }
            break;

        }
    }
}