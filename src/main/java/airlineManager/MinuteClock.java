package airlineManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MinuteClock {

    private final long minuteInMilliSeconds = 4000;


    // private int notificationInterval;
    private Timer timer;
    TimerTask timerTask;
    private int minutes;
    private Collection<MinuteClockListener> listeners;

    public MinuteClock() {
        // this.notificationInterval = notificationInterval;
        this.listeners = new ArrayList<>();
        timer = new Timer(true);
    }

    public int getRunTime() { return this.minutes; }

    // public int getNotificationInterval() { return this.notificationInterval; }

    public void addListener(MinuteClockListener listener) throws IllegalArgumentException {
        if(this.listeners.contains(listener))
            throw new IllegalArgumentException(listener + " is already added.");
        this.listeners.add(listener);
        System.out.println("Added " + listener.toString() + " as a listener to " + this.toString());
    }

    public void removeListener(MinuteClockListener listener) throws IllegalArgumentException {
        if(!this.listeners.contains(listener))
            throw new IllegalArgumentException(listener + " is not a listener.");
        this.listeners.remove(listener);
        System.out.println("Removed " + listener.toString() + " as a listener to " + this.toString());
    }

    private void createTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                minutes++;
                notifyListeners();
            }
        };
    }

    public void start() {

        this.createTimerTask();
        timer.scheduleAtFixedRate(timerTask, minuteInMilliSeconds, minuteInMilliSeconds);
        
        System.out.println( this.toString() + " has been started.");
    }

    public void stop() {
        if(!Objects.isNull(timer)) timer.cancel();
        System.out.println(this.toString() + " has been stopped.");
    }

    private void notifyListeners() {
        this.listeners.forEach(listener -> listener.minuteProcedure());
        System.out.println("Minute passed");
    }


    @Override
    public String toString() {
        return "MinuteClock";
    }

    public static void main(String[] args) {
        MinuteClock clock = new MinuteClock();
        clock.start();
    }
}
