package airlineManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
// import com.github.javafaker.Faker;

public class Airport extends City {



    private String name;
    private int gates, capacity;
    private List<Plane> planes;
    private List<Passenger> travellers;
    // Will be used often, so added as a field to prevent having to
    // create new instances every time refreshTravellers() is called
    private Random random; 



    public Airport(String airportName, int gates, int capacity, String cityName, double latitude, double longitude) {
        super(cityName, latitude, longitude);
        this.name = airportName;
        this.gates = gates;
        this.capacity = capacity;
        this.planes = new ArrayList<>();
        this.travellers = new ArrayList<>();
        this.random = new Random();
    }



    public String getAirportName() { return this.name; }

    public int getNumberOfGates() { return this.gates; }

    public int getCapacity() { return this.capacity; }

    public List<Plane> getPlanes() { return new ArrayList<>(this.planes); }
    
    public void addPlane(Plane plane) { this.planes.add(plane); }
    
    public void removePlane(Plane plane) { this.planes.remove(plane); }
    
    public List<Passenger> getTravellers() { return new ArrayList<>(this.travellers); }


    
    public void boardPassenger(Plane plane, Passenger passenger) {
        if(!this.planes.contains(plane))
            throw new IllegalArgumentException("This plane is not at " + this.getAirportName());
        
        // Cannot be used because the controller won't be refreshed
        // until a new / same plane is selected. Then the controller will show the passenger,
        // but the passenger wont actualle be at the airport.
        // if(!this.travellers.contains(passenger))
        //     throw new IllegalArgumentException(passenger.getFullName() + " is not at " + this.getAirportName());

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



    public void refreshTravellers(List<Airport> availableAirports) {
        // if(amount > this.getCapacity() || amount < 0)
        //     throw new IllegalArgumentException("The capacity has to be between 0 and " + this.getCapacity());
        if(availableAirports.size() < 1)
            throw new IllegalStateException("There has to be more airports for this method to work.");
     
        if(availableAirports.contains(this)) availableAirports.remove(this);
        this.travellers.removeAll(this.travellers);

        // Faker faker = new Faker();

        // for(int i = 0; i < this.getCapacity(); i++) travellers.add(
        //     new Passenger(faker.name().firstName() + " " + faker.name().lastName(),
        //            50,
        //                   availableAirports.get(random.nextInt(availableAirports.size())))
        // );

        for(int i = 0; i < this.getCapacity(); i++) {
            Airport randomDestination = availableAirports.get(random.nextInt(availableAirports.size()));
            travellers.add(
            new Passenger(this.createTravellerName(),
                          (int)CalculateFlightDistance.calculate(this, randomDestination),
                          randomDestination)
        );
        }
    }



    // Temporary until Faker works
    private String createTravellerName() {
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

}
