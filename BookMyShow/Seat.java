package BookMyShow;

public class Seat {
    private int id;
    private int row;
    private int col;
    private SeatType type;

    public Seat(int id, int row, int col, SeatType type) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public int getId() { return id; }
    public SeatType getType() { return type; }
}
