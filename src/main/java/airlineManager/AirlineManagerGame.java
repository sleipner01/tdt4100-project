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
    
    private Airport defaultAirport;
    private String defaultGameSaveName;
    private SecondClock secondClock;

    private Properties properties;

    private Airline airline;
    private List<Aircraft> aircrafts;
    private List<Airport> airports;
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
        this.setDefaultAirport();
        this.setDefaultGameSaveName();

        File existingGame = this.checkForExistingValidGameSave(this.defaultGameSaveName);
        if(Objects.isNull(existingGame)) this.loadNewGame();
        else {
            try {
                this.loadGameSave(existingGame);
            }
            catch (Exception e) {
                System.out.println(e);
                this.loadNewGame();
            }
        }

        refreshTravellers();
        this.secondClock = new SecondClock();
        this.secondClock.addListener(this);
        secondClock.start();
    }

    private void loadNewGame() {

        this.airline = new Airline(Integer.parseInt(properties.getProperty("defaultCoins")), this.getDefaultAirport());
        this.hasLoadedFromGameSave = false;

    }

    private void loadExistingGame(Airline airline) {
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



    // ***************
    // Game Functions
    // ***************

    // Remove when other functionality has been added
    private Airport getDefaultAirport() { return this.defaultAirport; }

    public Airline getAirline() { return this.airline; }

    public List<Airport> getAirports() { return new ArrayList<>(this.airports); }

    public List<Aircraft> getAircrafts() { return new ArrayList<>(this.aircrafts); }

    public int refreshingTravellersIn() {
        return this.travellersRefreshInterval - this.seconds/60;
    }

    private void refreshTravellers() {
        this.airports.forEach(airport -> airport.refreshTravellers(this.getAirports()));
    }

    public void boardPassenger(Plane plane, Passenger passenger) {
        plane.getAirport().boardPassenger(plane, passenger);
    }

    public void unBoardPassenger(Plane plane, Passenger passenger) {
        plane.getAirport().unBoardPassenger(plane, passenger);

    }

    public boolean airlineCanBuy(Aircraft aircraft) {
        if(this.airline.canBuy(aircraft)) return true;
        return false;
    }

    public void airlineBuy(Aircraft aircraft) {
        this.airline.buy(aircraft);
    }

    public void setSelectedPlane(Plane plane) {
        this.selectedPlane = plane;
    }

    public Plane getSelectedPlane() {
        return this.selectedPlane;
    }

    public void setSelectedAircraft(Aircraft aircraft) {
        this.selectedAircraft = aircraft;
    }

    public Aircraft getSelectedAircraft() {
        return this.selectedAircraft;
    }



    // ***************
    // Save and load
    // ***************

    public void saveGame(String saveName) {
        System.out.println("Saving game... " + saveName);
        InterfaceGameSaveHandler gameSaveHandler = new GameSaveHandler();
        gameSaveHandler.save(saveName, this);
        System.out.println("Game saved");
        

    }

    public void loadGameSave(File file) throws FileNotFoundException {
        System.out.println("Loading game...");
        InterfaceGameSaveHandler gameSaveHandler = new GameSaveHandler();
        this.loadExistingGame(gameSaveHandler.load(file, this));
    }

    private File checkForExistingValidGameSave(String defaultGameSave) {
        InterfaceGameSaveHandler saveHandler = new GameSaveHandler();
        return saveHandler.checkForExistingValidGameSave(defaultGameSave);
    }

    public boolean hasLoadedFromGameSave() {
        return this.hasLoadedFromGameSave;
    }



    // ***************
    // Time
    // ***************

    @Override
    public void tick() {
        this.seconds++;
        // Not perfect, must be fixed
        this.getAirline().getPlanes().forEach(plane -> plane.tick()); 
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

    public void addToGameClock(SecondClockListener listener) {
        this.secondClock.addListener(listener);
    }

}