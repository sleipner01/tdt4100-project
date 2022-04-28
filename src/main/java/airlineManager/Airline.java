package airlineManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class Airline implements Iterable<Plane> {



    private final String DEFAULT_NAME = "Airline";
    private final String REGEX_PATTERN = "[a-zA-Z]*";




    private String name;
    // private long value;
    private int coins;
    private List<Plane> planes;
    private Airport homeAirport;



    public Airline(int coins, Airport homeAirport) throws IllegalArgumentException {
        this.name = DEFAULT_NAME;
        if(!isValidCoinInput(coins)) throw new IllegalArgumentException("The coin input must be positive");
        this.coins = coins;
        this.homeAirport = homeAirport;
        this.planes = new ArrayList<>();

        System.out.println("\n************");
        System.out.println("Created a new airline;");
        System.out.println("Name: " + name + ", Coins: " + coins + "\n");
    }

    // Simply to allow the gameSaveHandler to create the airline in an alternate way, with it's signature for security
    public Airline(InterfaceGameSaveHandler gameSaveHandler) {}

    public Airline(String name, int coins, Airport homeAirport, InterfaceGameSaveHandler gameSaveHandler) throws IllegalArgumentException {
        this.name = name;
        if(!isValidCoinInput(coins)) throw new IllegalArgumentException("The coin input must be positive");
        this.coins = coins;
        this.homeAirport = homeAirport;

        System.out.println("\n************");
        System.out.println("Restored Airline");
        System.out.println("Name:" + name + ", Coins: " + coins);

    }

    private boolean isValidCoinInput(int coins) {
        if(coins > 0) return true;
        return false;
    }

    // Must be added by an authorized class ie. a gameSave loader
    public void addExistingPlanes(List<Plane> planes, InterfaceGameSaveHandler gameSaveHandler) {
        this.planes = planes;

        System.out.println("\n************");
        System.out.println("Restored Planes");
        for (Plane plane : planes) {
            System.out.println(plane);
        }
    }





    public String getName() { return this.name; }

    public int getCoinAmount() { return this.coins; }

    public List<Plane> getPlanes() { return new ArrayList<>(this.planes); }

    public int getNumberOfAirplanes() { return this.planes.size(); }

    public Airport getHomeAirport() {
        return this.homeAirport;
    }



    public void rename(String name) throws IllegalArgumentException {
        if(!isValidAirlineName(name)) throw new IllegalArgumentException("This is not a valid airline name");
        this.name = name;
        System.out.println("Renamed airline to: " + name);
    }
    
    private boolean isValidAirlineName(String name) {
        if(Pattern.matches(REGEX_PATTERN, name)) return true;
        return false;
    }



    public void addIncome(int coins) throws IllegalArgumentException {
        if(!isValidCoinInput(coins)) throw new IllegalArgumentException("Insert a positive integer.");
        this.coins += coins;
    }



    public void addExpense(int coins) {
        if(!isValidCoinInput(coins)) throw new IllegalArgumentException("Insert a positive integer.");
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
