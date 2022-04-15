package airlineManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Airline implements Iterable<Plane> {

    private String name;
    // private long value;
    private int coins;
    private List<Plane> planes;
    private Airport homeAirport;


    public Airline(String name, int coins, Airport homeAirport) {
        this.name = name;
        this.coins = coins;
        this.homeAirport = homeAirport;
        this.planes = new ArrayList<>();

        System.out.println("\n************");
        System.out.println("Created a new airline;");
        System.out.println("Name: " + name + ", Coins: " + coins + "\n");
    }

    public Airline (String name, int coins, List<Plane> planes) {
        this.name = name;
        this.coins = coins;
        this.planes = planes;

        System.out.println("\n************");
        System.out.println("Restored Airline");
        System.out.println("Name:" + name + ", Coins: " + coins + ", Planes:");
        for (Plane plane : planes) {
            System.out.println(plane);
        }
        System.out.println("");
    }

    public String getName() {
        return this.name;
    }

    public int getCoinAmount() {
        return this.coins;
    }

    public void addIncome(int coins) throws IllegalArgumentException {
        if(coins < 0) throw new IllegalArgumentException("Insert a positive integer.");
        this.coins += coins;
    }

    public int getNumberOfAirplanes() {
        return this.planes.size();
    }

    // public List<Plane> getPlanes() {
    //     return new ArrayList<>(this.planes);
    // }

    public void buy(Aircraft aircraft) throws IllegalArgumentException {
        if(this.getCoinAmount() < aircraft.getPrice())
            throw new IllegalArgumentException("You don't have enough money for this aircraft.");
        
        
        int numberOfPlanes = this.getNumberOfAirplanes();
        String temporaryPlaneNickName = (numberOfPlanes == 0) ? "Plane" : "Plane" + numberOfPlanes;
        this.planes.add(new Plane(aircraft, temporaryPlaneNickName, this, this.homeAirport));

        System.out.println("\n" + this + " successfully bought " + aircraft);
    }

    public List<Plane> getPlanes() {
        return new ArrayList<>(this.planes);
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
