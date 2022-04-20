package airlineManager;

// @FunctionalInterface
public class CalculateFlightDistance {
    private final double R = 6371000; // metres

    public double calculate(Airport origin, Airport destination) {
        // φ, λ in radians
        double φ1 = origin.getLatitude() * Math.PI/180; 
        double φ2 = destination.getLatitude() * Math.PI/180;
        double Δφ = (φ2-φ1) * Math.PI/180;
        double Δλ = (destination.getLongitude()-origin.getLongitude()) * Math.PI/180;

        double a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
                Math.cos(φ1) * Math.cos(φ2) *
                Math.sin(Δλ/2) * Math.sin(Δλ/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R * c; // in metres

    }
}
