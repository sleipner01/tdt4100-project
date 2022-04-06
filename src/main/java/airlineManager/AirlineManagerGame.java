package airlineManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class AirlineManagerGame {

    private final String CONFIG_FILE = "config.properties";
    private final String AIRCRAFTS_FILE_NAME = "aircrafts.csv";
    private final String AIRPORTS_FILE_NAME = "airports.csv";

    private Airline airline;
    private List<Aircraft> aircrafts = new ArrayList<>();
    private List<Airport> airports = new ArrayList<>();

    //private List<Flight> flights = new ArrayList<>();


    public AirlineManagerGame() {
        // FUNCTIONALITY REMOVED. Instead hardcoded in this file.
        // Setting up initial values from config file
        // try (FileReader reader = new FileReader(CONFIG_FILE)) {
        //     Properties properties = new Properties();
        //     properties.load(reader);

        //     this.PATH = properties.getProperty("resourcesPath");
        //     this.AIRCRAFTS_FILE_NAME = properties.getProperty("aircraftsFileName");
        //     this.AIRPORTS_FILE_NAME = properties.getProperty("airportsFileName");

        // } 
        // catch (Exception e) {
        //     e.printStackTrace();
        // }

        // Loading game files with airplanes and airports
        this.load();
    }

    // If there exists a save, start from save - Initialize the airline
    public AirlineManagerGame(File save) {
        // this();
        this.load(save);
    }

    private void load() {
        this.loadAircraft(AIRCRAFTS_FILE_NAME);
        this.loadAirports(AIRPORTS_FILE_NAME);
    }

    private void load(File save) {
        this.load();

        // Do Save restoration stuff
    }



    private void loadAircraft(String fileName) {

        InterfaceGameFileLoader<Aircraft> aircraftsLoader = new AircraftsLoader();
        this.aircrafts = aircraftsLoader.load(fileName);

    }

    private void loadAirports(String fileName) {

        InterfaceGameFileLoader<Airport> airportsLoader = new AirportsLoader();
        this.airports = airportsLoader.load(fileName);

    }

    public Airline getAirline() {
        return this.airline;
    }



    public static void main(String[] args) {
        AirlineManagerGame game = new AirlineManagerGame();
    }


}