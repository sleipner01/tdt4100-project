package airlineManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;


public class AirlineManagerGame implements SecondClockListener {



    private final String CONFIG_FILE_NAME = "config";
    private final String AIRCRAFTS_FILE_NAME = "aircrafts";
    private final String AIRPORTS_FILE_NAME = "airports";
    private final int travellersRefreshInterval = 5;

    private final InterfaceGameSaveHandler gameSaveHandler;
    private final Properties properties;
    private final SecondClock secondClock;
    private final List<Aircraft> aircrafts;
    private final List<Airport> airports;
    


    private Airport defaultAirport;
    private String defaultGameSaveName;

    private Airline airline;
    private int seconds;
    private boolean hasLoadedFromGameSave;

    private Plane selectedPlane;
    private Aircraft selectedAircraft;



    // ************
    // Setup
    // ************

    public AirlineManagerGame() {
        this.properties = new PropertiesLoader().load(CONFIG_FILE_NAME);
        this.aircrafts = new AircraftsLoader().load(AIRCRAFTS_FILE_NAME);
        this.airports= new AirportsLoader().load(AIRPORTS_FILE_NAME);
        this.gameSaveHandler = new GameSaveHandler();
        this.secondClock = new SecondClock();
        this.setDefaultAirport();
        this.setDefaultGameSaveName();

        this.loadGame();

        this.refreshTravellers();

        this.startGameClock();
    }

    private void loadGame() {
        File existingGame = this.checkForExistingValidGameSave(this.defaultGameSaveName);
        if(Objects.isNull(existingGame)) this.loadNewGame();
        else {
            try {
                this.loadGameSave(existingGame);
            }
            catch (Exception e) {
                e.printStackTrace();
                this.loadNewGame();
            }
        }
    }

    private void loadNewGame() {
        this.airline = new Airline(Integer.parseInt(properties.getProperty("defaultCoins")), this.getDefaultAirport());
        this.hasLoadedFromGameSave = false;
    }

    private void initializeExistingGame(Airline airline) {
        this.airline = airline;
        this.hasLoadedFromGameSave = true;
    }

    private void setDefaultAirport() {
        this.defaultAirport = airports.stream().filter(airport -> airport.getAirportName().equals(this.properties.get("defaultAirport"))).findFirst().get();
        System.out.println("Made " + this.defaultAirport + " as default airport");
    }

    private void setDefaultGameSaveName() {
        this.defaultGameSaveName = properties.getProperty("defaultGameSaveName", "gameSave");
    }

    private Airport getDefaultAirport() { return this.defaultAirport; }



    // ***************
    // Game Functions
    // ***************

    public Airline getAirline() { return this.airline; }

    public List<Airport> getAirports() { return new ArrayList<>(this.airports); }

    public List<Aircraft> getAircrafts() { return new ArrayList<>(this.aircrafts); }

    public int refreshingTravellersIn() {
        return this.travellersRefreshInterval - this.seconds/60;
    }

    private void refreshTravellers() {
        this.airports.forEach(airport -> airport.refreshTravellers(this.getAirports()));
    }

    public void boardPassenger(Plane plane, Passenger passenger) throws IllegalArgumentException {
        plane.getAirport().boardPassenger(plane, passenger);
    }

    public void unBoardPassenger(Plane plane, Passenger passenger) throws IllegalArgumentException {
        plane.getAirport().unBoardPassenger(plane, passenger);

    }

    public boolean airlineCanBuy(Aircraft aircraft) throws IllegalArgumentException {
        if(!this.isValidAircraft(aircraft)) throw new IllegalArgumentException(aircraft + " does not exist in the gamefiles.");
        if(this.airline.canBuy(aircraft)) return true;
        return false;
    }

    public void airlineBuy(Aircraft aircraft) throws IllegalArgumentException {
        this.airline.buy(aircraft, this.secondClock);
    }

    public void setSelectedPlane(Plane plane) throws IllegalArgumentException {
        if(!this.isValidPlane(plane)) throw new IllegalArgumentException(plane + " does not exist in the gamefiles.");
        this.selectedPlane = plane;
    }

    private boolean isValidPlane(Plane plane) {
        for(Plane airlinePlane : this.airline) if(airlinePlane.equals(plane)) return true;
        return false;
    }

    public Plane getSelectedPlane() {
        return this.selectedPlane;
    }

    public void setSelectedAircraft(Aircraft aircraft) throws IllegalArgumentException {
        if(!this.isValidAircraft(aircraft)) throw new IllegalArgumentException(aircraft + " does not exist in the gamefiles.");
        this.selectedAircraft = aircraft;
    }

    private boolean isValidAircraft(Aircraft aircraft) {
        if(aircrafts.contains(aircraft)) return true;
        return false;
    }

    public Aircraft getSelectedAircraft() {
        return this.selectedAircraft;
    }



    // ***************
    // Save and load
    // ***************

    public void saveGame(String saveName) throws IllegalArgumentException {
        System.out.println("Saving game... " + saveName);
        gameSaveHandler.save(saveName, this);
        System.out.println("Game saved");
    }

    public void loadGameSave(File file) throws FileNotFoundException, IllegalArgumentException {
        this.stop();
        System.out.println("Loading game...");
        this.initializeExistingGame(gameSaveHandler.load(file, this));
        this.start();
    }

    private File checkForExistingValidGameSave(String defaultGameSave) {
        return gameSaveHandler.checkForExistingValidGameSave(defaultGameSave);
    }

    public boolean hasLoadedFromGameSave() {
        return this.hasLoadedFromGameSave;
    }



    // ***************
    // Time
    // ***************

    private void startGameClock() {
        this.secondClock.addListener(this);
        for (Plane plane : this.getAirline()) {
            this.secondClock.addListener(plane);
        }
        this.secondClock.start();
    }

    @Override
    public void tick() {
        this.seconds++;
        this.minuteProcedure();
    }

    public void start() {
        this.secondClock.start();
    }

    public void stop() {
        this.secondClock.stop();
    }

    private void minuteProcedure() {
        if(this.seconds/60 == this.travellersRefreshInterval) {
            this.refreshTravellers();
            this.saveGame(defaultGameSaveName);
            this.seconds = 0;
        }
    }

    public void addToGameClock(SecondClockListener listener) throws IllegalArgumentException {
        this.secondClock.addListener(listener);
    }

}