package airlineManager;

public class Passenger {



    private String firstName;
    private String surName;
    private int paying;
    private Airport destination;



    public Passenger(String name, int paying, Airport destinaton) {
        String[] nameArray = name.split(" ");
        this.firstName = nameArray[0];
        this.surName = nameArray[1];
        this.paying = paying;
        this.destination = destinaton;
    }



    public String getFullName() { return this.firstName + " " + this.surName; }

    public int getPaying() { return this.paying; }

    public Airport getDestination() { return this.destination; }



    @Override
    public String toString() {
        return this.getFullName();
    }
}
