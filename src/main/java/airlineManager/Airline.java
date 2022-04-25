package airlineManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Airline implements Iterable<Plane> {



    private final String DEFAULT_NAME = "Airline";



    private String name;
    // private long value;
    private int coins;
    private List<Plane> planes;
    private Airport homeAirport;



    public Airline(int coins, Airport homeAirport) {
        this.name = DEFAULT_NAME;
        this.coins = coins;
        this.homeAirport = homeAirport;
        this.planes = new ArrayList<>();

        System.out.println("\n************");
        System.out.println("Created a new airline;");
        System.out.println("Name: " + name + ", Coins: " + coins + "\n");
    }



    public Airline (String name, int coins, Airport homeAirport, List<Plane> planes) {
        this.name = name;
        this.coins = coins;
        this.homeAirport = homeAirport;
        this.planes = planes;

        System.out.println("\n************");
        System.out.println("Restored Airline");
        System.out.println("Name:" + name + ", Coins: " + coins + ", Planes:");
        for (Plane plane : planes) {
            System.out.println(plane);
        }
        System.out.println();
    }



    public String getName() { return this.name; }

    public int getCoinAmount() { return this.coins; }

    public List<Plane> getPlanes() { return new ArrayList<>(this.planes); }

    public int getNumberOfAirplanes() { return this.planes.size(); }



    public void rename(String name) {
        this.name = name;
        System.out.println("Renamed airline to: " + name);
    }



    public void addIncome(int coins) throws IllegalArgumentException {
        if(coins < 0) throw new IllegalArgumentException("Insert a positive integer.");
        this.coins += coins;
    }



    public void addExpense(int coins) {
        if(coins < 0) throw new IllegalArgumentException("Insert a positive integer.");
        this.coins -= coins;
    }



    public void buy(Aircraft aircraft) throws IllegalArgumentException {
        if(!this.canBuy(aircraft))
            throw new IllegalArgumentException("You don't have enough money for this aircraft.");

        this.addExpense(aircraft.getPrice());
        
        Plane newPlane = new Plane(aircraft, this.createTemporaryPlaneNickName(), this, this.homeAirport);
        this.planes.add(newPlane);

        System.out.println("\n" + this + " successfully bought " + aircraft);
    }



    public boolean canBuy(Aircraft aircraft) {
        if(this.getCoinAmount() > aircraft.getPrice()) return true;
        return false;
    }



    private String createTemporaryPlaneNickName() {
        int numberOfPlanes = this.getNumberOfAirplanes();
        String temporaryPlaneNickName = (numberOfPlanes == 0) ? "Plane" : "Plane" + numberOfPlanes;
        return temporaryPlaneNickName;
    }



    @Override
    public Iterator<Plane> iterator() {
        Iterator<Plane> planeIterator = new PlaneIterator(planes);        
        return planeIterator;
    }



    @Override
    public String toString() {
        return this.name;
    }
}
