

package airlineManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class Plane implements SecondClockListener {



    private Aircraft aircraft;
    private String nickName;
    private Airline airline;
    private Airport airport;
    private List<Passenger> passengers;
    private Airport destination;
    private int flightTime;
    private boolean inFlight;
    private int flightCost;



    private final int DEFAULT_FLIGHTTIME = 30;


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



    public Plane(Aircraft aircraft, String nickName, Airline airline, Airport airport, Airport destination, boolean inFlight, int flightTimeInMinutes, List<Passenger> passengers) {
        this.aircraft = aircraft;
        this.nickName = nickName;
        this.airline = airline;
        this.airport = airport;
        this.destination = destination;
        this.inFlight = inFlight; 
        if(!inFlight) airport.addPlane(this);
        this.flightTime = flightTimeInMinutes * 60;
        this.passengers = passengers;
    }



    public Aircraft getAircraft() { return this.aircraft; }

    public String getNickName() { return this.nickName; }

    public void setNickName(String nickName) { this.nickName = nickName; }

    public Airline getAirline() { return this.airline; }

    public Airport getAirport() { return this.airport; }
    
    public int getPassengerCount() { return this.passengers.size(); }

    public boolean isInFlight() { return this.inFlight; }

    public Airport getDestination() { return this.destination; }

    // Using real world minutes as seconds in the game.
    private void setFlightTime(int time) { this.flightTime = time; }

    private int getRemainingFlightTime() { return this.flightTime; }
    
    public int getRemainingFlightTimeInMinutes() { return this.flightTime/60 + 1; }

    public int getFlightCost() { return this.flightCost; }



    public int getIncome() {
        return passengers.stream()
                         .filter(passenger -> passenger.getDestination().equals(this.getDestination()))
                         .mapToInt(passenger -> passenger.getPaying())
                         .sum();
    }



    public int getProfit() {
        return this.getIncome() - this.getFlightCost();
    }



    public List<Passenger> getPassengers() {
        return new ArrayList<>(this.passengers);
    }

    public boolean hasBoarded(Passenger passenger) {
        return this.passengers.contains(passenger);
    }

    public boolean hasMoreEmptySeats() { 
        return this.getPassengerCount() < this.aircraft.getSeats();
    }

    public boolean isReadyForTakeOff() {
        if(Objects.isNull(this.destination)) return false;
        if(!this.isInRange(this.destination)) return false;
        if(this.airline.getCoinAmount() < this.getFlightCost()) return false;
        return true;
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



    private void unBoardPassengers() {
        for(int i = 0; i < this.getPassengerCount(); i++) {
            if(this.getAirport().equals(this.passengers.get(i).getDestination())){
                this.removePassenger(this.passengers.get(i));
                i--;
            }
        }
    }



    public void setDestination(Airport destination) throws IllegalArgumentException {

        if(!this.isInRange(destination))
            throw new IllegalArgumentException("This airport is out of range for this aircraft");

        this.destination = destination;

        this.setFlightTime(calculateFlightTime());
        this.setFlightCost();
    }



    public boolean isInRange(Airport destination) {
        if(CalculateFlightDistance.calculate(this.airport, destination) > this.getAircraft().getRange())
            return false;
        return true;
    }



    private int calculateFlightTime() throws IllegalStateException {
        if(Objects.isNull(this.airport))
            throw new IllegalStateException("The plane isn't currently at an airport.");
        if(Objects.isNull(this.destination))
            throw new IllegalStateException("Destination is not set.");

        double distance = CalculateFlightDistance.calculate(this.getAirport(), this.getDestination());

        if(distance <= 0) return DEFAULT_FLIGHTTIME;
        
        return (int)(distance / this.getAircraft().getSpeed() * 60);
    }



    private void setFlightCost() {
        this.flightCost = calculateFlightCost();
    }

    private int calculateFlightCost() throws IllegalStateException {
        if(Objects.isNull(this.airport))
            throw new IllegalStateException("The plane isn't currently at an airport.");
        if(Objects.isNull(this.destination))
            throw new IllegalStateException("Destination is not set.");

        return (int) (CalculateFlightDistance.calculate(this.getAirport(), this.getDestination()) * this.getAircraft().getEfficiency());
    }



    public void takeOff() throws IllegalArgumentException, IllegalStateException {
        //TODO: If everything is good, take off
        if(this.isInFlight())
            throw new IllegalArgumentException("The plane is already in flight");
        if(Objects.isNull(this.destination)) 
            throw new IllegalStateException("Destination is not set.");

        this.inFlight = true;

        this.airline.addExpense(this.getFlightCost());

        this.airport.removePlane(this);

        System.out.println("\n" + this + ": Gear up!");
    }



    public void land() {
        if(!this.isInFlight()) 
            throw new IllegalArgumentException("The plane isn't flying");
        //TODO: Do landing stuff
        this.inFlight = false;

        this.airline.addIncome(this.getIncome());
        
        this.airport = this.getDestination();
        this.destination = null;
        this.airport.addPlane(this);

        this.unBoardPassengers();
        
        System.out.println("\nRetard, retard, retard... " + this + " just landed");
    }
    


    @Override
    public void tick() {

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


}
