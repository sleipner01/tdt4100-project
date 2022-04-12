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
        return this.aircraft;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Airline getAirline() {
        return this.airline;
    }

    public List<Passenger> getPassengers() {
        return this.passengers;
    }

    public void addPassenger(Passenger passenger) {
        if(this.passengers.contains(passenger))
            throw new IllegalArgumentException(passenger + "is already in the plane");
        this.passengers.add(passenger);
    }

    public void removePassenger(Passenger passenger) {
        if(!this.passengers.contains(passenger))
            throw new IllegalArgumentException("The plane haven't boarded " + passenger);
        this.passengers.remove(passenger);
    }

    public void clearPassengers() {
        this.passengers.removeAll(this.passengers);
    }
    
}
