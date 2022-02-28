package airlineManager;

import java.util.ArrayList;
import java.util.List;

public class Plane {

    private Aircraft aircraft;
    private String nickName;
    private Airline airline;
    private List<Passenger> passengers;


    public Plane(Aircraft aircraft, String nickName, Airline airline) {
        this.aircraft = aircraft;
        this.nickName = nickName;
        this.airline = airline;
        this.passengers = new ArrayList<>();
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Airline getAirline() {
        return airline;
    }

    public List<Passenger> getPassengers() {
        return this.passengers;
    }

    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }

    public void clearPassengers() {
        this.passengers.removeAll(this.passengers);
    }
    
}
