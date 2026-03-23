import java.util.*;

public class Game {
    private Board board;
    private Dice dice;
    private PositionSituator positionSituator;
    private DiceRuleStrategy diceRule;

    private Queue<Player> players;

    public Game(int n, List<String> names, DiceRuleStrategy diceRule) {
        this.board = new Board(n);
        this.dice = new Dice();
        this.positionSituator = new PositionSituator();
        this.diceRule = diceRule;

        this.players = new LinkedList<>();
        new BoardInitialiser().initialise(board, n);

        for (String name : names) {
            players.offer(new Player(name));
        }
    }

   public void start(){
    while(players.size() > 1){
        Player current = players.poll();
        int consecutiveSixes = 0;

        while(true){
            int roll = dice.roll();
            System.out.println(current.getName() + " rolled: " + roll);

            if(roll == 6) consecutiveSixes++;
            else consecutiveSixes = 0;

            if(!diceRule.canContinue(consecutiveSixes)){
                System.out.println("3 consecutive 6s! You cannot take your turn");
                break;
            }

            int newPos = current.getPosition() + roll;

            if(newPos <= board.getWinningCell()){
                newPos = positionSituator.getNextPosition(newPos, board);
                current.setPosition(newPos);
            }

            System.out.println(current.getName() + "is at " + current.getPosition());

            if(current.getPosition() == board.getWinningCell()){
                System.out.println("Congratulations! " + current.getName() + " Wins");
                break;
            }

            if(roll!=6) break;
        }

        if(current.getPosition() != board.getWinningCell()){
            players.add(current);
        }
    }
   }
}
