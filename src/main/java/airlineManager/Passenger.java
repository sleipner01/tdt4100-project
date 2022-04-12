package airlineManager;

public class Passenger {

    private String name;
    private int paying;
    private Airport destination;

    public Passenger(String name, int paying, Airport destinaton) {
        this.name = name;
        this.paying = paying;
        this.destination = destinaton;
    }

    public String getName() {
        return this.name;
    }

    public int getPaying() {
        return this.paying;
    }

    public City getDestination() {
        return this.destination;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
