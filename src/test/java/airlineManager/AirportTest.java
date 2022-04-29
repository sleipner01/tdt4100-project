package airlineManager;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

public class AirportTest {

    private AirlineManagerGame createGame() {
        AirlineManagerGame game = new AirlineManagerGame();
        return game;
    }

    @Test
    public void testRefreshTravellers() {

        AirlineManagerGame game = createGame();
        Airport someAirport = game.getAirports().get(0);

        Passenger someTraveller = someAirport.getTravellers().get(0);

        someAirport.refreshTravellers(game.getAirports());

        List<Passenger> newTravellers = someAirport.getTravellers();

        assertFalse(newTravellers.contains(someTraveller), "The travellersLists should not contain any of the same objects");

    }
    
}
