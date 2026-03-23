public class Player {
    private String name;
    private int position = 0;

    public Player(String name){
        this.name = name;
    }

    // Getter
    public String getName(){
        return name;
    }
    
    public int getPosition(){
        return position;
    }

    // Setter
    public void setPosition(int position){
        this.position = position;
    }
}
