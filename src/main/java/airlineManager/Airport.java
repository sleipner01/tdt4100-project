package airlineManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
// import com.github.javafaker.Faker;

public class Airport extends City implements Comparable<Airport> {



    private String name;
    private int gates, capacity;
    private List<Plane> planes;
    private List<Passenger> travellers;
    private int airportID;
    // Will be used often, so added as a field to prevent having to
    // create new instances every time refreshTravellers() is called
    private Random random;
    // Faker faker = new Faker();




    public Airport(String airportName, int gates, int capacity, String cityName, double latitude, double longitude, int airportID)
    throws IllegalArgumentException {
        super(cityName, latitude, longitude);

        if(!isValidIntegers(gates, capacity))
            throw new IllegalArgumentException("The integers put into the Airport constructor must be positive");

        this.name = airportName;
        this.gates = gates;
        this.capacity = capacity;
        this.airportID = airportID;
        this.planes = new ArrayList<>();
        this.travellers = new ArrayList<>();
        this.random = new Random();
    }

    private boolean isValidIntegers(int... integers) {
        for (int i : integers) {
            if(i < 0) return false;
        }

        return true;
    }

    private boolean isValidDecimalCoordinates(double latitude, double longitude) {
        if(latitude < -90) return false;
        if(latitude > 90) return false;
        if(longitude < -180) return false;
        if(longitude > 180) return false;
        return true;
    }



    public String getAirportName() { return this.name; }

    public int getNumberOfGates() { return this.gates; }

    public int getCapacity() { return this.capacity; }

    public List<Plane> getPlanes() { return new ArrayList<>(this.planes); }
    
    public void addPlane(Plane plane) { this.planes.add(plane); }
    
    public void removePlane(Plane plane) { this.planes.remove(plane); }
    
    public List<Passenger> getTravellers() { return new ArrayList<>(this.travellers); }

    public int getAirportID() { return this.airportID; }


    
    public void boardPassenger(Plane plane, Passenger passenger) throws IllegalArgumentException {
        if(!this.isValidPlane(plane))
            throw new IllegalArgumentException("This plane is not at " + this.getAirportName());
        if(!this.travellers.contains(passenger))
            throw new IllegalArgumentException(passenger.getFullName() + " is not at " + this.getAirportName());

        plane.addPassenger(passenger);
        this.travellers.remove(passenger);
    }



    public void unBoardPassenger(Plane plane, Passenger passenger) throws IllegalArgumentException {
        if(!this.isValidPlane(plane))
            throw new IllegalArgumentException("This plane is not at " + this.getAirportName());
        if(!plane.hasBoarded(passenger))
            throw new IllegalArgumentException("The plane havn't boarded " + passenger.getFullName());

        plane.removePassenger(passenger);
        this.travellers.add(passenger);
    }

    public boolean isValidPlane(Plane plane) {
        if(this.planes.contains(plane)) return true;
        return false;
    }



    public void refreshTravellers(List<Airport> availableAirports) {
        if(availableAirports.size() < 1)
            return;
     
        if(availableAirports.contains(this)) availableAirports.remove(this);
        this.travellers.removeAll(this.travellers);

        for(int i = 0; i < this.getCapacity(); i++) {
            Airport randomDestination = availableAirports.get(random.nextInt(availableAirports.size()));
            travellers.add(
            new Passenger(this.createTravellerName(),
                          this,
                          randomDestination)
        );
        }
    }



    private String createTravellerName() {

        // I wanted to use an external library to create random names, but the compiler wasn't able to add it as a module in module-info.java
        // return faker.name().firstName() + " " + faker.name().lastName();

        List<String> firstNames = new ArrayList<>(
            Arrays.asList("Magnus", "James", "Tarald", "Eivind", "Ida", "Karen", "Johanne", "Emilie", "Muhammed", "Ismail", "Gaule", "Gyrsel", "Kristine", "Nils", "Sigurd", "Ingrid", "Yuki", "Zhou", "Carlos", "George", "Valtteri", "Kevin", "Mick", "Grinsild", "Dino", "Stikjær", "Napoleon", "Jøllebølle", "Toto", "Pete", "Christian"));
        List<String> surNames = new ArrayList<>(
            Arrays.asList("Islamabad", "Byrkjeland", "Mohammed", "Muhammed", "Olsen", "Bakke", "Systad", "Timm", "Smith", "Leclerc", "Hamilton", "Andersen", "Ghuany", "Tsunoda", "Gasly", "Sainz", "Russel", "Bottas", "Magnussen", "Shumacher", "Hoel", "Narui", "Zienolddiny", "Palizban", "Utne", "Gürktürk", "PingLee", "Midtskill", "Bonnington", "Wolff", "Horner"));

        return firstNames.get(random.nextInt(firstNames.size())) + " " +
                      surNames.get(random.nextInt(surNames.size()));

    }


    @Override
    public String toString() {
        return super.toString() + ", Airport=" + this.name;
    }



    @Override
    public int compareTo(Airport airport) {
        return (int)CalculateFlightDistance.calculate(this, airport);
    }

}
