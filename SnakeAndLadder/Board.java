import java.util.ArrayList;
import java.util.List;

public class Board {
    private int size;
    private int winningCell;

    private List<Snake> snakes;
    private List<Ladder> ladders;

    public Board(int n){
        this.size = n;
        this.winningCell = n*n;
        snakes = new ArrayList<>();
        ladders = new ArrayList<>();
    }

    // Getters
    public int getWinningCell(){
        return winningCell;
    }

    public List<Snake> getSnakes(){
        return snakes;
    }

    public List<Ladder> getLadders(){
        return ladders;
    }

    public int getSize(){
        return size;
    }
}
