public class SmartBoard implements PowerControl, ConnectInput {
    public void powerOn() {}
    public void powerOff() {}
    public void connectInput(String port) {
        System.out.println("SmartBoard connected to " + port);
    }
}