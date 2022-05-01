package airlineManager;

import java.util.regex.Pattern;

public class Passenger {



    private final double PAYMENT_RATIO = 1.2;
    private final String REGEX_PATTERN = "^([ \\u00c0-\\u01ffa-zA-Z'\\-])+$";

    private String fullName;
    private int paying;
    private Airport destination;



    public Passenger(String name, Airport location, Airport destination) throws IllegalArgumentException {
        if(!this.isValidNameFormat(name))
            throw new IllegalArgumentException(name + " is not a valid name format...");

        this.fullName = name;
        this.destination = destination;
        this.paying = this.calculatePayment(location, destination);
    }

    private boolean isValidNameFormat(String name) {
        if(Pattern.matches(REGEX_PATTERN, name)) return true;
        return false;
    }


    
    private int calculatePayment(Airport location, Airport destination) {
        return (int)(CalculateFlightDistance.calculate(location, destination) * PAYMENT_RATIO);
    }



    public String getFullName() { return this.fullName; }

    public int getPaying() { return this.paying; }

    public Airport getDestination() { return this.destination; }



    @Override
    public String toString() {
        return this.getFullName();
    }
}
