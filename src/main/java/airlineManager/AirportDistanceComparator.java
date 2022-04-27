package airlineManager;

import java.util.Comparator;

public class AirportDistanceComparator implements Comparator<Airport> {

    private Airport baseAirport;

    public AirportDistanceComparator(Airport baseAirport) {
        this.baseAirport = baseAirport;
    }

    @Override
    public int compare(Airport a1, Airport a2) {
        return baseAirport.compareTo(a1) - baseAirport.compareTo(a2);
    }
    
}
