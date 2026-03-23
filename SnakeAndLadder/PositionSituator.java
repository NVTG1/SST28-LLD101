public class PositionSituator {
    public int getNextPosition(int pos, Board board){
        boolean moved;

        // As we have to apply the Snake/Ladder once, we use do-while

        do{
            moved = false;

            for(Snake s: board.getSnakes()){
                if(s.getHead() == pos){
                    System.out.println("Oh No! Snake bit you.");
                    pos = s.getTail();
                    moved = true;
                }
            }

            for(Ladder l: board.getLadders()){
                if(l.getStart() == pos){
                    System.out.println("Yayy! You climbed a ladder.");
                    pos = l.getEnd();
                    moved = true;
                }
            }
        }
        while(moved);

        return pos;
    }
}
