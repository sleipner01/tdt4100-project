package airlineManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class AirlineManagerGame {

    private final String CONFIG_FILE = "config.properties";
    private final String AIRCRAFTS_FILE_NAME = "aircrafts.csv";
    private final String AIRPORTS_FILE_NAME = "airports.csv";

    private Properties properties;

    private Airline airline;
    private List<Aircraft> aircrafts;
    private List<Airport> airports;

    //private List<Flight> flights = new ArrayList<>();


    public AirlineManagerGame() {
        this.properties = new PropertiesLoader().load(CONFIG_FILE);
        this.aircrafts = new AircraftsLoader().load(AIRCRAFTS_FILE_NAME);
        this.airports= new AirportsLoader().load(AIRPORTS_FILE_NAME);
        // Loading game files with airplanes and airports
        this.load();
    }



    // If there exists a save, start from save - Initialize the airline
    public AirlineManagerGame(File save) {
        // this();
        this.load(save);
    }



    private void load() {
        this.airline = new Airline("ByrkjajerAirlines", Integer.parseInt(properties.getProperty("defaultCoins")));
    }



    private void load(File save) {
        // this.load();

        // Do Save restoration stuff
    }



    public Airline getAirline() {
        return this.airline;
    }



    public static void main(String[] args) {
        AirlineManagerGame game = new AirlineManagerGame();

    }


}