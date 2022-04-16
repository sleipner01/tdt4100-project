package airlineManager;

import java.util.ArrayList;
import java.util.List;
public class Plane implements MinuteClockListener {

    private Aircraft aircraft;
    private String nickName;
    private Airline airline;
    private Airport airport;
    private List<Passenger> passengers;
    private Airport destination;
    private int flightTime;
    private boolean inFlight;


    public Plane(Aircraft aircraft, String nickName, Airline airline, Airport startingAirport) {
        this.aircraft = aircraft;
        this.nickName = nickName;
        this.airline = airline;
        this.airport = startingAirport;
        startingAirport.addPlane(this);
        this.passengers = new ArrayList<>();
        

        System.out.println("Created " 
                           + aircraft.getManufacturer() + " " + aircraft.getModel() 
                           + " for " + airline);
    }

    public Aircraft getAircraft() { return this.aircraft; }

    public String getNickName() { return this.nickName; }

    public void setNickName(String nickName) { this.nickName = nickName; }

    public Airline getAirline() { return this.airline; }

    public Airport getAirport() { return this.airport; }
    

    public List<Passenger> getPassengers() {
        return new ArrayList<>(this.passengers);
    }

    public int getPassengerCount() { return this.passengers.size(); }

    public boolean hasBoarded(Passenger passenger) {
        return this.passengers.contains(passenger);
    }

    public boolean isInFlight() { return this.inFlight; }

    public boolean hasMoreEmptySeats() { 
        return this.getPassengerCount() < this.aircraft.getSeats();
    }

    public void addPassenger(Passenger passenger) {
        if(this.passengers.contains(passenger))
            throw new IllegalArgumentException(passenger + "is already in the plane");
        if(!this.hasMoreEmptySeats())
            throw new IllegalArgumentException("The plane cannot board any more passengers");
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

    public Airport getDestination() { return this.destination; }

    public void setDestination(Airport destination) {
        this.destination = destination;

        // Calculate flightTime
        int time = 0;
        setFlightTime(time);
    }

    public int getFlightTime() { return this.flightTime; }

    private void setFlightTime(int time) {
        this.flightTime = time;
    }

    public void takeOff() {
        //TODO: If everything is good, take off
        this.inFlight = true;

        this.airport.removePlane(this);

        System.out.println(this + ": Gear up!");
    }

    public void land() {
        //TODO: Do landing stuff
        this.inFlight = false;

        this.airport = this.getDestination();
        this.destination = null;
        this.airport.addPlane(this);

        System.out.println("Retard, retard, retard... " + this + " just landed");


        for(Passenger passenger : this.passengers) {
            if(this.airport.equals(passenger.getDestination())){
                this.airline.addIncome(passenger.getPaying());
            }

            this.removePassenger(passenger);
        }

    }

    @Override
    public String toString() {
        return this.nickName;
    }

    @Override
    public void minute() {
        // TODO Auto-generated method stub
        
    }
    
}
