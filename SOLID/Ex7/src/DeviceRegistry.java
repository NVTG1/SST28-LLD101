import java.util.*;

public class DeviceRegistry {
    // Stores any object so that all of them don't have to share the same interface
    // Stores [ Projector, LightsPanel, AirConditioner, AttendanceScanner ]
    private final java.util.List<Object> devices = new ArrayList<>();

    // Adding a device
    public void add(Object d) { devices.add(d); }

    // Finds by interface
    // capability => represents the interface or class (BrightnessControl interface, etc)
    public Object getFirstOfCapability(Class<?> capability) {
        for (Object d : devices) {
            // if found
            if (capability.isInstance(d)) return d;
        }
        // if not found
        throw new IllegalStateException("Missing: " + capability.getSimpleName());
    }
}
