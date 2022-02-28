package airlineManager;

import java.util.ArrayList;
import java.util.List;

public class Airport extends City{

    private String name;
    private int gates;
    private List<Plane> planes = new ArrayList<>();
    private List<Passenger> travellers = new ArrayList<>();


    public Airport(String airportName, int gates, String cityName, int longitude, int latitude) {
        super(cityName, longitude, latitude);
        this.name = airportName;
        this.gates = gates;
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

    public List<Passenger> getTravellers() {
        return new ArrayList<>(this.travellers);
    }

}
