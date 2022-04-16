package airlineManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


public class AirlineManagerGame implements MinuteClockListener {



    private final String CONFIG_FILE = "config.properties";
    private final String AIRCRAFTS_FILE_NAME = "aircrafts.csv";
    private final String AIRPORTS_FILE_NAME = "airports.csv";
    private final int travellersRefreshInterval = 5;
    private Airport defaultAirport;
    private MinuteClock minuteClock;

    private Properties properties;

    private Airline airline;
    private List<Aircraft> aircrafts;
    private List<Airport> airports;
    private int minutes;

    //private List<Flight> flights = new ArrayList<>();



    public AirlineManagerGame() {
        this.properties = new PropertiesLoader().load(CONFIG_FILE);
        this.aircrafts = new AircraftsLoader().load(AIRCRAFTS_FILE_NAME);
        this.airports= new AirportsLoader().load(AIRPORTS_FILE_NAME);
        // Loading game files with airplanes and airports
        this.minuteClock = new MinuteClock();
        this.minuteClock.addListener(this);
        minuteClock.start();
        this.load();
    }



    // If there exists a save, start from save - Initialize the airline
    public AirlineManagerGame(String gameSaveFileName) {
        // this();
        //this.load(gameSaveFileName);
    }



    private void load() {
        this.defaultAirport = airports.stream().filter(airport -> airport.getAirportName().equals(this.properties.get("defaultAirport"))).findFirst().get();
        System.out.println("Made " + this.defaultAirport + " as default airport");

        this.airline = new Airline(Integer.parseInt(properties.getProperty("defaultCoins")), this.getDefaultAirport());
        airports.forEach(airport -> airport.refreshTravellers(this.getAirports()));
    }



    private void load(String gameSaveFileName) {
        // this.load();

        // Do Save restoration stuff
    }


    // Remove when other functionality has been added
    private Airport getDefaultAirport() { return this.defaultAirport; }



    public Airline getAirline() { return this.airline; }



    public List<Airport> getAirports() { return new ArrayList<>(this.airports); }



    public List<Aircraft> getAircrafts() { return new ArrayList<>(this.aircrafts); }



    // public MinuteClock getMinuteClock() {
    //     return this.minuteClock;
    // }



    public void addToGameClock(MinuteClockListener listener) {
        this.minuteClock.addListener(listener);
    }



    public int refreshingTravellersIn() {
        return this.travellersRefreshInterval - this.minutes;
    }



    @Override
    public void minuteProcedure() {
        this.minutes++;
        if(this.minutes == this.travellersRefreshInterval) {
            this.airports.forEach(airport -> airport.refreshTravellers(this.getAirports()));
            this.minutes = 0;
        }

        // Not perfect, must be fixed
        this.getAirline().getPlanes().forEach(plane -> plane.minuteProcedure()); 
    }

}