package airlineManager;

import java.util.ArrayList;
import java.util.List;

public class Flight {

    private Plane plane;
    private List<Passenger> passengers;
    private Airport origin;
    private Airport destination;


    public Flight(Plane plane, List<Passenger> passengers, Airport origin) {
        this.plane = plane;
        this.passengers = new ArrayList<>();
        this.origin = origin;
    }

    public Plane getPlane() {
        return this.plane;
    }

    public List<Passenger> getPassengers() {
        return this.passengers;
    }


    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }

    public Airport getOrigin() {
        return this.origin;
    }

    public Airport getDestination() {
        return this.destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

}
