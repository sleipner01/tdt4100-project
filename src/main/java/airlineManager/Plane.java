package airlineManager;

import java.util.ArrayList;
import java.util.List;
public class Plane {

    private Aircraft aircraft;
    private String nickName;
    private Airline airline;
    private Airport airport;
    private List<Passenger> passengers;


    public Plane(Aircraft aircraft, String nickName, Airline airline, Airport startingAirport) {
        this.aircraft = aircraft;
        this.nickName = nickName;
        this.airline = airline;
        this.airport = startingAirport;
        this.passengers = new ArrayList<>();

        System.out.println("Created " 
                           + aircraft.getManufacturer() + " " + aircraft.getModel() 
                           + " for " + airline);
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

    public Airport getAirport() {
        return this.airport;
    }

    public List<Passenger> getPassengers() {
        return this.passengers;
    }

    public int getPassengerCount() {
        return this.passengers.size();
    }

    public void addPassenger(Passenger passenger) {
        if(this.passengers.contains(passenger))
            throw new IllegalArgumentException(passenger + "is already in the plane");
        this.passengers.add(passenger);
        System.out.println("Boarded " + passenger + ".");
    }

    public void removePassenger(Passenger passenger) {
        if(!this.passengers.contains(passenger))
            throw new IllegalArgumentException("The plane haven't boarded " + passenger);
        this.passengers.remove(passenger);
        System.out.println("Kicked off " + passenger + ".");
    }

    public void clearPassengers() {
        this.passengers.removeAll(this.passengers);
        System.out.println("Plane is now empty.");
    }

    @Override
    public String toString() {
        return this.nickName;
    }
    
}
