package airlineManager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class PlaneTest {

    private AirlineManagerGame createGame() {
        AirlineManagerGame game = new AirlineManagerGame();
        game.airlineBuy(game.getAircrafts().get(0));
        return game;
    }
    

    @Test
    public void testBoardAndUnBoardPassenger() {

        AirlineManagerGame game = createGame();

        Airport airport = game.getAirline().getPlanes().get(0).getAirport();
        Plane plane = game.getAirline().getPlanes().get(0);
        Passenger passenger  = airport.getTravellers().get(0);

        assertTrue(airport.getTravellers().contains(passenger), "The traveller should be at the airport.");
        assertFalse(plane.getPassengers().contains(passenger), "The passenger should not be on the plane");

        game.boardPassenger(plane, passenger);

        assertFalse(airport.getTravellers().contains(passenger), "The traveller should not be at the airport.");
        assertTrue(plane.getPassengers().contains(passenger), "The passenger should be on the plane");

        game.unBoardPassenger(plane, passenger);

        assertTrue(airport.getTravellers().contains(passenger), "The traveller should be at the airport.");
        assertFalse(plane.getPassengers().contains(passenger), "The passenger should not be on the plane");
    }

    @Test
    public void testSetDestination() {

        AirlineManagerGame game = createGame();
        Plane plane = game.getAirline().getPlanes().get(0);
        Airport planeAirport = plane.getAirport();
        Airport outOfRangeAirport = game.getAirports().stream().filter(airport -> airport.compareTo(planeAirport) > plane.getAircraft().getRange()).findFirst().get();
        List<Airport> otherAirports = game.getAirports();
        otherAirports.remove(planeAirport);
        Airport inRangeAirport = otherAirports.stream().filter(airport -> airport.compareTo(planeAirport) < plane.getAircraft().getRange()).findFirst().get();

        assertThrows(IllegalArgumentException.class, () -> plane.setDestination(planeAirport));
        assertThrows(IllegalArgumentException.class, () -> plane.setDestination(outOfRangeAirport));

        plane.setDestination(inRangeAirport);

        assertTrue(plane.getDestination().equals(inRangeAirport), "The destination is not correct");

    }

    @Test
    public void testTakeOff() {

        AirlineManagerGame game = createGame();
        Plane plane = game.getAirline().getPlanes().get(0);
        Airport planeAirport = plane.getAirport();
        List<Airport> otherAirports = game.getAirports();
        otherAirports.remove(planeAirport);
        Airport inRangeAirport = otherAirports.stream().filter(airport -> airport.compareTo(planeAirport) < plane.getAircraft().getRange()).findFirst().get();

        assertThrows(IllegalStateException.class, () -> plane.takeOff());

        plane.setDestination(inRangeAirport);

        assertTrue(plane.isReadyForTakeOff(), "The plane should be ready for takeoff");

        plane.takeOff();

        assertThrows(IllegalArgumentException.class, () -> plane.takeOff());

        assertFalse(planeAirport.getPlanes().contains(plane), "The plane should be gone from the airport");
    }

    @Test
    public void testLanding() {

        AirlineManagerGame game = createGame();
        Plane plane = game.getAirline().getPlanes().get(0);
        Airport planeAirport = plane.getAirport();
        List<Airport> otherAirports = game.getAirports();
        otherAirports.remove(planeAirport);
        Airport inRangeAirport = otherAirports.stream().filter(airport -> airport.compareTo(planeAirport) < plane.getAircraft().getRange()).findFirst().get();

        plane.setDestination(inRangeAirport);
        plane.takeOff();

        assertTrue(plane.getDestination().equals(inRangeAirport), "The destination should have been set correctly");
        assertFalse(inRangeAirport.getPlanes().contains(plane), "The destination should not contain the aircraft yet");

        plane.land();

        assertTrue(inRangeAirport.getPlanes().contains(plane), "The destination should contain the aircraft");
        assertThrows(IllegalArgumentException.class, () -> plane.land());

    }

}
