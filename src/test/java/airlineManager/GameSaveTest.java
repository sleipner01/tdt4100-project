package airlineManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;

public class GameSaveTest {

    private AirlineManagerGame createGame() {
        AirlineManagerGame game = new AirlineManagerGame();
        game.airlineBuy(game.getAircrafts().get(0));
        game.boardPassenger(game.getAirline().getPlanes().get(0),
                            game.getAirline().getPlanes().get(0).getAirport().getTravellers().get(0));

        return game;
	}
	
	@Test
	public void testSaveGame() {
		AirlineManagerGame game = this.createGame();
		InterfaceGameSaveHandler gameSaveHandler = new GameSaveHandler();
		gameSaveHandler.save("testSave", game);

        Airline airline = game.getAirline();
        Plane plane = game.getAirline().getPlanes().get(0);
        Passenger passenger = plane.getPassengers().get(0); 

        String content = null;
		
        try {
            content = Files.readString(new File(getClass().getResource("gamefiles/").getFile() + "testSave.txt").toPath());
        }
        catch (IOException e) {
            fail("Error when reading file");
            e.printStackTrace();
        }

		String actual = content;
		String expected = "***VALID\n" +
                          "\n\n\n" +
                          "***AIRLINE\n" + 
                          "Airline," + 
                          airline.getName() + "," + 
                          airline.getCoinAmount() + "," + 
                          airline.getHomeAirport().getAirportID() + "\n" +
                          "\n\n\n" +
                          "***PLANES\n" +
                          "Plane," + 
                          plane.getNickName() + "," +
                          plane.getAircraft().getAircraftID() + "," +
                          plane.getAirport().getAirportID() + "," +
                          "null," +
                          plane.isInFlight() + "," +
                          plane.getRemainingFlightTimeInMinutes() + "\n" + 
                          "Passenger," + 
                          passenger.getFullName() + "," +
                          passenger.getDestination().getAirportID() + "\n" +
                          "\n\n";

		
		assertEquals(expected, actual, "Information written to the file is in the wrong format.");
	}
	
	@Test
	public void testLoadGame() {
		AirlineManagerGame game = this.createGame();
		InterfaceGameSaveHandler gameSaveHandler = new GameSaveHandler();
		gameSaveHandler.save("testLoad", game);

        Airline expectedAirline = game.getAirline();
        Plane expectedPlane = game.getAirline().getPlanes().get(0);
        Passenger expectedPassenger = expectedPlane.getPassengers().get(0); 

        Airline actualAirline = null;
        
        try {
            actualAirline = gameSaveHandler.load(new File(getClass().getResource("gamefiles/").getFile() + "testLoad.txt"), game);
        }
        catch (FileNotFoundException e) {
            fail("The test couldn't find the file");
        }
        catch (IllegalArgumentException e) {
            fail("The test couldn't find the file");
        }
        Plane actualPlane = actualAirline.getPlanes().get(0);
        Passenger actualPassenger = actualPlane.getPassengers().get(0); 

		assertEquals(expectedAirline.getCoinAmount(), actualAirline.getCoinAmount(), "The coinamounts are different.");
		assertEquals(expectedAirline.getHomeAirport().getAirportID(), actualAirline.getHomeAirport().getAirportID(), "The home airports are different.");
		assertEquals(expectedPlane.getAircraft().getAircraftID(), actualPlane.getAircraft().getAircraftID(), "The aircrafts are different.");
		assertEquals(expectedPlane.getAirport().getAirportID(), actualPlane.getAirport().getAirportID(), "The airports are different.");
		assertEquals(expectedPassenger.getFullName(), actualPassenger.getFullName(), "The passengers name are different.");
		assertEquals(expectedPassenger.getPaying(), actualPassenger.getPaying(), "The passengers are paying different amounts.");
		assertEquals(expectedPassenger.getDestination(), actualPassenger.getDestination(), "The passengers are going different places.");

	}
}
