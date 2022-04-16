package airlineManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


public class AirlineManagerGame {

    private final String CONFIG_FILE = "config.properties";
    private final String AIRCRAFTS_FILE_NAME = "aircrafts.csv";
    private final String AIRPORTS_FILE_NAME = "airports.csv";
    private Airport defaultAirport;

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
        this.defaultAirport = airports.stream().filter(airport -> airport.getAirportName().equals(this.properties.get("defaultAirport"))).findFirst().get();
        MinuteClock minuteClock = new MinuteClock();
        System.out.println("Made " + this.defaultAirport + " as default airport");
        this.load();
    }



    // If there exists a save, start from save - Initialize the airline
    public AirlineManagerGame(String gameSaveFileName) {
        // this();
        //this.load(gameSaveFileName);
    }



    private void load() {
        this.airline = new Airline(Integer.parseInt(properties.getProperty("defaultCoins")), this.getDefaultAirport());
        airports.forEach(airport -> airport.refreshTravellers(this.getAirports()));
    }



    private void load(String gameSaveFileName) {
        // this.load();

        // Do Save restoration stuff
    }


    // Remove when other functionality has been added
    private Airport getDefaultAirport() {
        return this.defaultAirport;
    }

    

    public Airline getAirline() {
        return this.airline;
    }



    public List<Airport> getAirports() {
        return new ArrayList<>(this.airports);
    }



    public List<Aircraft> getAircrafts() {
        return new ArrayList<>(this.aircrafts);
    }



    public void addToGameClock(MinuteClockListener listener) {

    }



    public static void main(String[] args) {
        AirlineManagerGame game = new AirlineManagerGame();

        Airline airline = game.getAirline();
        
        airline.buy(new Aircraft("Boeing", "737", "Passanger", 200, 200, 1, 500, 4));

        // Iterator<Plane> it = airline.iterator();
        // while(it.hasNext()) {
        //     System.out.println(it.next().getNickName());
        // }

        // for (Plane plane : airline) {
        //     System.out.println(plane.getNickName());
        // }

    }


}