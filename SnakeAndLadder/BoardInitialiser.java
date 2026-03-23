import java.util.*;

public class BoardInitialiser {
    private Random rand = new Random();

    public void initialise(Board board, int n){
        int winningCell = board.getWinningCell();

        addSnakes(board, n, winningCell);
        addLadders(board, n, winningCell);
    }

    private void addSnakes(Board board, int n, int winningCell){
        while(board.getSnakes().size() < n){
            int head = getRandom(2, winningCell);  
            int tail = getRandom(1, head-1);

            board.getSnakes().add(new Snake(head, tail));
        }
    }

    private void addLadders(Board board, int n, int winningCell){
        while(board.getLadders().size()<n){
            int start = getRandom(1, winningCell-1);
            int end = getRandom(start+1, winningCell);

            board.getLadders().add(new Ladder(start, end));
        }
    }

    private int getRandom(int min, int max){
        return rand.nextInt(max-min+1) + min;
    }
}
