package airlineManager;

import java.util.ArrayList;
import java.util.List;

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

    public void refreshTravellers(int amount, List<Airport> availableAirports) {
        this.travellers.removeAll(this.travellers);

        for(int i = 0; i < amount; i++) travellers.add(new Passenger("Magnus", 50, availableAirports.get(0)));


    }

    @Override
    public String toString() {
        return super.toString() + ", Airport=" + this.name;
    }

}
