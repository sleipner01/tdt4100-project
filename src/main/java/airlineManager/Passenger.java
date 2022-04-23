package airlineManager;

public class Passenger {

    private final double PAYMENT_RATIO = 0.4;

    private String firstName;
    private String surName;
    private int paying;
    private Airport destination;



    public Passenger(String name, Airport location, Airport destinaton) throws IllegalArgumentException {
        if(!this.isValidNameFormat(name))
            throw new IllegalArgumentException(name + " is not a valid name format...");
        String[] nameArray = name.split(" ");
        this.firstName = nameArray[0];
        this.surName = nameArray[1];
        this.destination = destinaton;
        this.paying = this.calculatePayment(location, destinaton);
    }

    private boolean isValidNameFormat(String name) {
        // TODO: Use Regex  
        return true;
    }


    
    private int calculatePayment(Airport location, Airport destination) {
        return (int)(CalculateFlightDistance.calculate(location, destination) * PAYMENT_RATIO);
    }



    public String getFullName() { return this.firstName + " " + this.surName; }

    public int getPaying() { return this.paying; }

    public Airport getDestination() { return this.destination; }



    @Override
    public String toString() {
        return this.getFullName();
    }
}
