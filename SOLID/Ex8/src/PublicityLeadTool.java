// PublicityLeadTool (Stretch Goal)
public class PublicityLeadTool implements EventAdmin {
    @Override
    public void createEvent(String name, double budget) {
        System.out.println("Publicity started for event: " + name);
    }

    @Override
    public int getEventsCount() {
        return 1;
    }
}