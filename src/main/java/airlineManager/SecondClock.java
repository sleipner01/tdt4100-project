package airlineManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;

public class SecondClock {



    private final long secondInMilliSeconds = 100;

    // private int notificationInterval;
    private Timer timer;
    private TimerTask timerTask;
    private int seconds;
    private Collection<SecondClockListener> listeners;


    
    public SecondClock() {
        // this.notificationInterval = notificationInterval;
        this.listeners = new ArrayList<>();
        timer = new Timer(true);
    }



    public int getRunTimeInSeconds() { return this.seconds; }



    // public int getNotificationInterval() { return this.notificationInterval; }



    public void addListener(SecondClockListener listener) throws IllegalArgumentException {
        if(this.listeners.contains(listener))
            throw new IllegalArgumentException(listener + " is already added.");
        this.listeners.add(listener);
        System.out.println("Added " + listener.toString() + " as a listener to " + this.toString());
    }



    public void removeListener(SecondClockListener listener) throws IllegalArgumentException {
        if(!this.listeners.contains(listener))
            throw new IllegalArgumentException(listener + " is not a listener.");
        this.listeners.remove(listener);
        System.out.println("Removed " + listener.toString() + " as a listener to " + this.toString());
    }



    private void createTimerTask() {
        // timerTask = new TimerTask() {
        //     public void run() {
        //         seconds++;
        //         notifyListeners();
        //     }
        // };

        timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        seconds++;
                        notifyListeners();
                    }
                });
            }
        };
    }



    public void start() {

        this.createTimerTask();
        timer.scheduleAtFixedRate(timerTask, secondInMilliSeconds, secondInMilliSeconds);
        
        System.out.println( this.toString() + " has been started.");
    }



    public void stop() {
        if(!Objects.isNull(timer)) timer.cancel();
        System.out.println(this.toString() + " has been stopped.");
    }



    private void notifyListeners() {
        this.listeners.forEach(listener -> listener.tick());
    }



    @Override
    public String toString() {
        return "SecondClock";
    }
}
