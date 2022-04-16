package airlineManager;

import java.util.ArrayList;
import java.util.Collection;

public class MinuteClock {
    Collection<MinuteClockListener> listeners;

    public MinuteClock() {
        this.listeners = new ArrayList<>();
    }
}
