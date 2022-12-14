// Haversine Formula
// https://en.wikipedia.org/wiki/Haversine_formula

// Haversine Formula in code
// https://www.movable-type.co.uk/scripts/latlong.html

// This Java version of the Haversine Formula is inspired from @author ananth
// https://gist.github.com/vananth22/888ed9a22105670e7a4092bdcf0d72e4


package airlineManager;
// @FunctionalInterface
public class CalculateFlightDistance {

    public static double calculate(Airport origin, Airport destination) {
        // https://en.wikipedia.org/wiki/Earth_radius
        final double earthRadius = 6371.0088; // Kilometers

        double latitude1 = origin.getLatitude();
        double longitude1 = origin.getLongitude();
        double latitude2 = destination.getLatitude();
        double longitude2 = destination.getLongitude();
        double Δlatitude = toRadians(latitude2-latitude1);
        double Δlongitude = toRadians(longitude2-longitude1);

        double halfCordLengthSquared = Math.sin(Δlatitude / 2) * Math.sin(Δlatitude / 2) 
                + Math.cos(toRadians(latitude1)) * Math.cos(toRadians(latitude2)) 
                * Math.sin(Δlongitude / 2) * Math.sin(Δlongitude / 2);

        double angularDistanceRadians = 2 * Math.atan2(Math.sqrt(halfCordLengthSquared), Math.sqrt(1-halfCordLengthSquared));
        double distance = earthRadius * angularDistanceRadians;
        
        return distance; // Kilometers
    }
    
    private static double toRadians(double value) {
        return value * Math.PI / 180;
    }        
}