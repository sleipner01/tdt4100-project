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
    
    public int getPassengerCount() { return this.passengers.size(); }

    public boolean isInFlight() { return this.inFlight; }

    public Airport getDestination() { return this.destination; }

    private void setFlightTime(int time) { this.flightTime = time; }

    public int getRemainingFlightTime() { return this.flightTime; }

    public List<Passenger> getPassengers() {
        return new ArrayList<>(this.passengers);
    }

    public boolean hasBoarded(Passenger passenger) {
        return this.passengers.contains(passenger);
    }

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
        System.out.println("\nKicked off " + passenger + ".");
    }



    public void clearPassengers() {
        this.passengers.removeAll(this.passengers);
        System.out.println("\nPlane is now empty.");
    }



    public void setDestination(Airport destination) {
        this.destination = destination;

        // Calculate flightTime
        int time = 0;
        setFlightTime(time);
    }



    public void takeOff() {
        //TODO: If everything is good, take off
        this.inFlight = true;
        this.flightTime = 4;

        this.airport.removePlane(this);

        System.out.println("\n" + this + ": Gear up!");
    }



    public void land() {
        //TODO: Do landing stuff
        this.inFlight = false;

        this.airport = this.getDestination();
        this.destination = null;
        this.airport.addPlane(this);

        System.out.println("\nRetard, retard, retard... " + this + " just landed");


        for(int i = 0; i < this.getPassengerCount(); i++) {
            if(this.airport.equals(this.passengers.get(i).getDestination())){
                this.airline.addIncome(this.passengers.get(i).getPaying());
                this.removePassenger(this.passengers.get(i));
                i--;
            }
        }

    }
    


    @Override
    public void minuteProcedure() {

        // Currently no good solution to add the planes directly to the clock.
        // The game calles this function

        if(!this.isInFlight()) return;
        if(this.getRemainingFlightTime() <= 1) this.land();
        flightTime--;
    }


    
    @Override
    public String toString() {
        return this.nickName;
    }


    public static void main(String[] args) {
        Airport airport = new Airport("Aids", 2, 40, "Aids", 1, 2);
        Airport airport2 = new Airport("Aids2", 2, 40, "Aids", 1, 2);
        Plane plane = new Plane(new Aircraft("S", "S", "S", 200, 200, 300, 250, 4), "aids", new Airline(15000, airport), airport);
        plane.setDestination(airport2);
        System.out.println(plane.getAirport().getAirportName());
        System.out.println(plane.getDestination().getAirportName());
        plane.takeOff();
        System.out.println(plane.getAirport().getAirportName());
        System.out.println(plane.getDestination().getAirportName());
        plane.land();
        System.out.println(plane.getAirport().getAirportName());
        System.out.println(plane.getDestination().getAirportName());
    }

}
