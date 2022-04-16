package airlineManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// import com.github.javafaker.Faker;

public class Airport extends City implements MinuteClockListener {

    private String name;
    private int gates, capacity;
    private List<Plane> planes;
    private List<Passenger> travellers;


    public Airport(String airportName, int gates, int capacity, String cityName, int longitude, int latitude) {
        super(cityName, longitude, latitude);
        this.name = airportName;
        this.gates = gates;
        this.capacity = capacity;
        this.planes = new ArrayList<>();
        this.travellers = new ArrayList<>();
    }


    public String getAirportName() { return this.name; }

    public int getNumberOfGates() { return this.gates; }

    public int getCapacity() { return this.capacity; }

    public List<Plane> getPlanes() { return new ArrayList<>(this.planes); }

    public void addPlane(Plane plane) { this.planes.add(plane); }

    public void removePlane(Plane plane) { this.planes.remove(plane); }

    public void boardPassenger(Plane plane, Passenger passenger) {
        if(!this.planes.contains(plane))
            throw new IllegalArgumentException("This plane is not at " + this.getAirportName());
        if(!this.travellers.contains(passenger))
            throw new IllegalArgumentException("The passenger is not at " + this.getAirportName());

        plane.addPassenger(passenger);
        this.travellers.remove(passenger);
    }

    public void unBoardPassenger(Plane plane, Passenger passenger) {
        if(!this.planes.contains(plane))
            throw new IllegalArgumentException("This plane is not at " + this.getAirportName());
        if(!plane.hasBoarded(passenger))
            throw new IllegalArgumentException("The plane havn't boarded " + passenger.getFullName());

        plane.removePassenger(passenger);
        this.travellers.add(passenger);
    }

    public List<Passenger> getTravellers() { return new ArrayList<>(this.travellers); }

    public void refreshTravellers(List<Airport> availableAirports) {
        // if(amount > this.getCapacity() || amount < 0)
        //     throw new IllegalArgumentException("The capacity has to be between 0 and " + this.getCapacity());
        if(availableAirports.size() < 1) return;

        this.travellers.removeAll(this.travellers);

        // Faker faker = new Faker();
        Random random = new Random();

        // for(int i = 0; i < this.getCapacity(); i++) travellers.add(
        //     new Passenger(faker.name().firstName() + " " + faker.name().lastName(),
        //            50,
        //                   availableAirports.get(random.nextInt(availableAirports.size())))
        // );

        for(int i = 0; i < this.getCapacity(); i++) travellers.add(
            new Passenger("Magnus Byrkjeland",
                   50,
                          availableAirports.get(random.nextInt(availableAirports.size())))
        );


    }

    @Override
    public String toString() {
        return super.toString() + ", Airport=" + this.name;
    }


    @Override
    public void minute() {
        // TODO Auto-generated method stub
        
    }

}
