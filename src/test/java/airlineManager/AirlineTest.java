package airlineManager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class AirlineTest {

    private AirlineManagerGame createGame() {
        AirlineManagerGame game = new AirlineManagerGame();
        return game;
    }

    @Test
    public void testRenameAirline() {

        AirlineManagerGame game = createGame();
        Airline airline = game.getAirline();

        String defaultName = "Airline";
        String validName = "Java";
        String inValidName = "|,-*^¨";
        
        assertTrue(airline.getName().equals(defaultName), "At the moment, if the game haven't loaded from a gameSave file, the name should be Airline");
        assertThrows(IllegalArgumentException.class, () -> airline.rename(inValidName));
        assertDoesNotThrow(() -> airline.rename(validName));
        airline.rename(validName);
        assertTrue(airline.getName().equals(validName));

    }

    @Test
    public void testAddIncome() {

        AirlineManagerGame game = createGame();
        Airline airline = game.getAirline();

        int currentCoins = airline.getCoinAmount();
        int income = 500;
        
        assertThrows(IllegalArgumentException.class, () -> airline.addIncome(-income));
        airline.addIncome(income);
        assertTrue(airline.getCoinAmount() == (currentCoins + income));

    }

    @Test
    public void testAddExpense() {

        AirlineManagerGame game = createGame();
        Airline airline = game.getAirline();

        int currentCoins = airline.getCoinAmount();
        int expense = 500;
        
        assertThrows(IllegalArgumentException.class, () -> airline.addIncome(-expense));
        airline.addExpense(expense);
        assertTrue(airline.getCoinAmount() == (currentCoins - expense));

    }

    @Test
    public void testBuyAircraft() {

        AirlineManagerGame game = createGame();
        Airline airline = game.getAirline();

        List<Aircraft> buyableAircrafts = game.getAircrafts().stream().filter(aircraft -> aircraft.getPrice() < airline.getCoinAmount()).toList();
        Aircraft someBuyableAircraft = buyableAircrafts.get(0);

        assertTrue(airline.canBuy(someBuyableAircraft));
        airline.buy(someBuyableAircraft, new SecondClock());
        assertTrue(airline.getNumberOfAirplanes() > 0, "The airline should have planes");
        assertTrue(airline.getPlanes().get(0).getAircraft().equals(someBuyableAircraft));

        airline.addExpense(airline.getCoinAmount());

        assertFalse(airline.canBuy(someBuyableAircraft));
        assertThrows(IllegalArgumentException.class, () -> airline.buy(someBuyableAircraft, new SecondClock()));

    }

}

