package airlineManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.Test;

public class GameFileReaderTest {

	@Test
	public void testReadAircraftsFile() throws UnsupportedEncodingException {

		AircraftsLoader aircraftsLoader = new AircraftsLoader();

		String manufacturer = "TestBus";
		String model = "404";
		String type = "Passenger";
		String livery = "plane.png";
		int speed = 200;
		int range = 199;
		int efficiency = 188;
		int price = 177;
		int seats = 166;
		int aircraftID = 0;

		Aircraft expectedAircraft= new Aircraft(manufacturer, model, type, speed, range, efficiency, price, seats, aircraftID, new Livery(livery));
		Aircraft actualAircraft = null;

        try (PrintWriter writer = new PrintWriter(new File(getClass().getResource("gamefiles/").getFile() + "aircraftsTestFile.csv"), "UTF-8")) {
            writer.println("TestingFile\n" + manufacturer + "," + model + "," + type + "," + livery + "," + speed + "," + range + "," + efficiency + "," + price + "," + seats);
		}
		catch (FileNotFoundException e) {
			fail("File couldn't be created");
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			fail("File couldn't be created");
			e.printStackTrace();
		}

		List<Aircraft> actualAircrafts;

		try {
			actualAircrafts = aircraftsLoader.load("aircraftsTestFile");
			actualAircraft = actualAircrafts.get(0);
		}
		catch (Exception e) {
			fail("Something went wrong when finding or reading the file");
		}

		assertEquals(expectedAircraft.getManufacturer(), actualAircraft.getManufacturer(), "Manufacturers does not match.");
		assertEquals(expectedAircraft.getModel(), actualAircraft.getModel(), "Models does not match.");
		assertEquals(expectedAircraft.getLivery().toString(), actualAircraft.getLivery().toString(), "Liveries does not match.");
		assertEquals(expectedAircraft.getSpeed(), actualAircraft.getSpeed(), "Speeds does not match.");
		assertEquals(expectedAircraft.getRange(), actualAircraft.getRange(), "Ranges does not match.");
		assertEquals(expectedAircraft.getPrice(), actualAircraft.getPrice(), "Prices does not match.");
		assertEquals(expectedAircraft.getSeats(), actualAircraft.getSeats(), "Efficiencies does not match.");
	}

	@Test
	public void testReadAirportsFile() throws UnsupportedEncodingException {

		AirportsLoader airportsLoader = new AirportsLoader();

		Airport expectedAirport = new Airport("TestAirport", 20, 20, "TestCity", 90, 0, 0);
		Airport actualAirport = null;

        try (PrintWriter writer = new PrintWriter(new File(getClass().getResource("gamefiles/").getFile() + "airportsTestFile.csv"), "UTF-8")) {
            writer.println("TestingFile\nTestCity,90,0,TestAirport,20,20");
		}
		catch (FileNotFoundException e) {
			fail("File couldn't be created");
		}

		List<Airport> actualAirports;

		try {
			actualAirports = airportsLoader.load("airportsTestFile");
			actualAirport = actualAirports.get(0);
		}
		catch (Exception e) {
			fail("Something went wrong when finding or reading the file");
		}

		assertEquals(expectedAirport.getCityName(), actualAirport.getCityName(), "Cities does not match.");
		assertEquals(expectedAirport.getAirportName(), actualAirport.getAirportName(), "Airportnames does not match.");
		assertEquals(expectedAirport.getLatitude(), actualAirport.getLatitude(), "Latitudes does not match.");
		assertEquals(expectedAirport.getLongitude(), actualAirport.getLongitude(), "Longitudes does not match.");
		assertEquals(expectedAirport.getCapacity(), actualAirport.getCapacity(), "Capacities does not match.");
		assertEquals(expectedAirport.getNumberOfGates(), actualAirport.getNumberOfGates(), "Number of gates does not match.");

	}
	
	@Test
	public void testReadProperties() throws UnsupportedEncodingException {
		PropertiesLoader propertiesLoader = new PropertiesLoader();
		Properties actualProperties = propertiesLoader.load("config");

		String validCode = "valid";
		
		String actualCodeRead = actualProperties.get("validTestCode").toString();
		
		assertEquals(validCode, actualCodeRead, "The codes does not match. Either the code has been changed, or the file isn't read properly");
	}
}
