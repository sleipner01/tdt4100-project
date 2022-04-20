package airlineManager;

// @FunctionalInterface
public class CalculateFlightDistance {

    public double calculate(Airport origin, Airport destination) {
        return Math.sqrt(
            Math.abs(Math.pow((origin.getLatitude() - destination.getLatitude()), 2))
            +
            Math.abs(Math.pow((origin.getLongitude() - destination.getLongitude()), 2))
            );
    }
}
