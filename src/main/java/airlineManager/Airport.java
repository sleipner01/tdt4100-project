package airlineManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.github.javafaker.Faker;

public class Airport extends City {

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


    public String getAirportName() {
        return this.name;
    }

    public int getNumberOfGates() {
        return this.gates;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public List<Plane> getPlanes() {
        return new ArrayList<>(this.planes);
    }

    public void addPlane(Plane plane) {
        this.planes.add(plane);
    }

    public void removePlane(Plane plane) {
        this.planes.remove(plane);
    }

    public List<Passenger> getTravellers() {
        return new ArrayList<>(this.travellers);
    }

    public void refreshTravellers(List<Airport> availableAirports) {
        // if(amount > this.getCapacity() || amount < 0)
        //     throw new IllegalArgumentException("The capacity has to be between 0 and " + this.getCapacity());

        this.travellers.removeAll(this.travellers);

        Faker faker = new Faker();
        String name = faker.name().fullName();


        Random random = new Random();

        for(int i = 0; i < this.getCapacity(); i++) travellers.add(new Passenger("Magnus", 50, availableAirports.get(random.nextInt(availableAirports.size()))));


    }

    @Override
    public String toString() {
        return super.toString() + ", Airport=" + this.name;
    }

}
