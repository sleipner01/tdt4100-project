package airlineManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


public class AirlineManagerGame implements SecondClockListener {



    private final String CONFIG_FILE = "config.properties";
    private final String AIRCRAFTS_FILE_NAME = "aircrafts.csv";
    private final String AIRPORTS_FILE_NAME = "airports.csv";
    private final int travellersRefreshInterval = 5;
    private Airport defaultAirport;
    private SecondClock secondClock;

    private Properties properties;

    private Airline airline;
    private List<Aircraft> aircrafts;
    private List<Airport> airports;
    private int seconds;

    //private List<Flight> flights = new ArrayList<>();



    public AirlineManagerGame() {
        this.properties = new PropertiesLoader().load(CONFIG_FILE);
        this.aircrafts = new AircraftsLoader().load(AIRCRAFTS_FILE_NAME);
        this.airports= new AirportsLoader().load(AIRPORTS_FILE_NAME);
        // Loading game files with airplanes and airports
        this.secondClock = new SecondClock();
        this.secondClock.addListener(this);
        secondClock.start();
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


        // TODO: Remove, only for development
        // this.getAirline().buy(aircrafts.get(1));
        // this.getAirline().buy(aircrafts.get(1));
        // this.getAirline().getPlanes().get(1).setDestination(this.getAirports().get(1));
        // this.getAirline().getPlanes().get(1).takeOff();;
        // this.getAirline().getPlanes().get(1).land();
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



    public void addToGameClock(SecondClockListener listener) {
        this.secondClock.addListener(listener);
    }



    public int refreshingTravellersIn() {
        return this.travellersRefreshInterval - this.seconds/60;
    }



    private void minuteProcedure() {
            if(this.seconds/60 == this.travellersRefreshInterval) {
                this.airports.forEach(airport -> airport.refreshTravellers(this.getAirports()));
                this.seconds = 0;
            }
    }



    public void boardPassenger(Plane plane, Passenger passenger) {
        plane.getAirport().boardPassenger(plane, passenger);
    }



    public void unBoardPassenger(Plane plane, Passenger passenger) {
        plane.getAirport().unBoardPassenger(plane, passenger);

    }



    @Override
    public void tick() {
        this.seconds++;
        // Not perfect, must be fixed
        this.getAirline().getPlanes().forEach(plane -> plane.tick()); 
        this.minuteProcedure();
    }



    public boolean airlineCanBuy(Aircraft aircraft) {
        if(this.airline.canBuy(aircraft)) return true;
        return false;
    }


    public void airlineBuy(Aircraft aircraft) {
        this.airline.buy(aircraft);
    }



}