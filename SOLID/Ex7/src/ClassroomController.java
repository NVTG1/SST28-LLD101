public class ClassroomController {
    private final DeviceRegistry reg;

    public ClassroomController(DeviceRegistry reg) {
        this.reg = reg;
    }

    public void startClass() {
        PowerControl power = (PowerControl) reg.getFirstOfCapability(PowerControl.class);
        ConnectInput projector = (ConnectInput) reg.getFirstOfCapability(ConnectInput.class);
        power.powerOn();
        projector.connectInput("HDMI-1");

        BrightnessControl lights =
            (BrightnessControl) reg.getFirstOfCapability(BrightnessControl.class);
        lights.setBrightness(60);

        TemperatureControl ac =
            (TemperatureControl) reg.getFirstOfCapability(TemperatureControl.class);
        ac.setTemperatureC(24);

        AttendanceScanner scan =
            (AttendanceScanner) reg.getFirstOfCapability(AttendanceScanner.class);
        System.out.println("Attendance scanned: present=" + scan.scanAttendance());
    }

    public void endClass() {
        System.out.println("Shutdown sequence:");
        ((Projector) reg.getFirstOfCapability(Projector.class)).powerOff();
        ((LightsPanel) reg.getFirstOfCapability(LightsPanel.class)).powerOff();
        ((AirConditioner) reg.getFirstOfCapability(AirConditioner.class)).powerOff();
    }
}