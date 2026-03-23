import java.util.*;

public class Main {
    public static void main(String args[]){
        Scanner scnr = new Scanner(System.in);

        int n = scnr.nextInt();
        int p = scnr.nextInt();

        List<String> names = new ArrayList<>();
        for(int i = 0; i<p; i++){
            names.add(scnr.next());
        }

        String level = scnr.nextLine();

        DiceRuleStrategy rule = level.equalsIgnoreCase("hard")? new HardMode(): new EasyMode();

        Game game = new Game(n, names, rule);
        game.start();

        scnr.close();
    }
}
