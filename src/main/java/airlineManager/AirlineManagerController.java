package airlineManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AirlineManagerController implements SecondClockListener {



    private AirlineManagerGame game;

    private final int GRIDPANE_PADDING = 6;

    private final int BUTTON_HORIZONTAL_GAP = 6;
    private final int BUTTON_VERTICAL_GAP = 6;

    private final int PLANE_BUTTON_MIN_HEIGHT = 70;
    private final int PLANE_BUTTON_MAX_HEIGHT = 70;
    private final int PLANE_BUTTON_MIN_WIDTH = 70;
    private final int PLANE_BUTTON_MAX_WIDTH = 100;

    private final int DESTINATION_BUTTON_MIN_HEIGHT = 50;
    private final int DESTINATION_BUTTON_MAX_HEIGHT = 90;
    private final int DESTINATION_BUTTON_MIN_WIDTH = 160;
    private final int DESTINATION_BUTTON_MAX_WIDTH = 160;

    private final int TRAVELLER_BUTTON_MIN_HEIGHT = 50;
    private final int TRAVELLER_BUTTON_MAX_HEIGHT = 90;
    private final int TRAVELLER_BUTTON_MIN_WIDTH = 160;
    private final int TRAVELLER_BUTTON_MAX_WIDTH = 160;

    private final int AIRCRAFT_BUTTON_MIN_HEIGHT = 50;
    private final int AIRCRAFT_BUTTON_MAX_HEIGHT = 90;
    private final int AIRCRAFT_BUTTON_MIN_WIDTH = 160;
    private final int AIRCRAFT_BUTTON_MAX_WIDTH = 160;

    @FXML
    public Button takeOffButton, buyPlaneButton;

    @FXML
    public Text airlineName, manufacturerAndModelLabel, passengerCountLabel,
                destinationAirportLabel, distanceLabel, profitLabel, airportNameLabel,
                travellersRefreshTimer, airlineCoins, selectedAircraftLabel, seatsLabel,
                priceLabel, rangeLabel, efficiencyLabel, speedLabel;

    @FXML
    public ScrollPane viewableTravellersList, viewableDestinationsList,
                      viewablePlanesList, viewableBuyableAircraftsList;



    // ************
    // Setup 
    // ************

    @FXML
    public void initialize() {
        game = new AirlineManagerGame();
        
        if(!game.hasLoadedFromGameSave()) nameAirlineDialog();
        
        addControllerToGameClock();

        resetPanel();
    }

    private void nameAirlineDialog() {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Welcome to Airline Manager");
        dialog.setHeaderText("What do you want to name your airline?");
        dialog.setContentText("Name:");
        try {
            game.getAirline().rename(dialog.showAndWait().get());
            setAirlineNameHeader();;
        } 
        catch (IllegalArgumentException e) {
            showAlert(e.getMessage());
            nameAirlineDialog();
        }
        catch (NoSuchElementException e) {
            System.err.println("Error when naming airline: " + e.getMessage());
            System.out.println("Creating airline with default name");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void resetPanel() {
        refreshInterface();
        refreshAircraftsTab();
    }



    // ***********
    // Time 
    // ***********

    private void addControllerToGameClock() {
        try {
            game.addToGameClock(this);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void tick() {
        // Only refreshing certain parts to keep the game running smoothly
        setTravellersRefreshTimer();
        setAirlineCoins();
        refreshPlanesInFlight();
    }



    // ************
    // Interface 
    // ************

    private void loadPlanesList() {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(GRIDPANE_PADDING));
        grid.setHgap(BUTTON_HORIZONTAL_GAP);
        grid.setVgap(BUTTON_VERTICAL_GAP);

        List<Plane> planesList = game.getAirline().getPlanes();
        if(planesList.size() <= 0) {
            viewablePlanesList.setContent(new Text("The airline does not have any planes."));
            return;
        }

        for (Plane plane : planesList) {
            grid.add(createPlaneButton(plane),planesList.indexOf(plane),1);
        }

        viewablePlanesList.setContent(grid);
        
    }

    // To keep the grid from having to update constantly, this only updates the flights in air.
    private void refreshPlanesInFlight() {

        Node viewablePlanesListContent = viewablePlanesList.getContent();
        if(!(viewablePlanesListContent instanceof GridPane)) return;

        GridPane viewablePlanesListGridPane = (GridPane)viewablePlanesListContent;
        ObservableList<Node> gridPanePlaneButtons = viewablePlanesListGridPane.getChildren();

        List<Plane> planesList = game.getAirline().getPlanes();
        // The two lists should be the same length, one with buttons, the other with planes..
        if(!(gridPanePlaneButtons.size() == planesList.size())) {
            loadPlanesList(); // In case a new aircraft have been bought
            if(!(gridPanePlaneButtons.size() == planesList.size())) 
                showAlert("For some reason, the amount of buttons and the number of planes stored in the game are not equal.");
        }

        for (Node button : gridPanePlaneButtons) {
            if(button.isDisable()){
                int index = gridPanePlaneButtons.indexOf(button);
                gridPanePlaneButtons.set(index, createPlaneButton(planesList.get(index)));
            }
            
        }
        
    }

    private Button createPlaneButton(Plane plane) {
        Button button;
        if(plane.isInFlight()) {
            button = new Button(plane.getNickName() + "\n" + 
                                "Time remaining: " + plane.getRemainingFlightTimeInMinutes() + " min");
            button.setDisable(true);
        }
        else { 
            button = new Button(plane.getNickName());
            button.setDisable(false);
        }
        button.wrapTextProperty().setValue(true);
        button.setStyle("-fx-text-alignment: center;");
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> handlePlaneSelect(plane));
        button.setMaxWidth(PLANE_BUTTON_MAX_WIDTH);
        button.setMinWidth(PLANE_BUTTON_MIN_WIDTH);
        button.setMaxHeight(PLANE_BUTTON_MAX_HEIGHT);
        button.setMinHeight(PLANE_BUTTON_MIN_HEIGHT);

        return button;
    }



    private void loadDestinationsList(Plane plane) {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(GRIDPANE_PADDING));
        grid.setHgap(BUTTON_HORIZONTAL_GAP);
        grid.setVgap(BUTTON_VERTICAL_GAP);

        List<Airport> airportsList = game.getAirports();
        if(airportsList.size() <= 0) {
            viewableDestinationsList.setContent(new Text("There are no airports."));
            return;
        }

        airportsList.remove(plane.getAirport());
        Collections.sort(airportsList, new AirportDistanceComparator(plane.getAirport()));
        // airportsList.sort((a, b) -> plane.getAirport().compareTo(a) - plane.getAirport().compareTo(b));

        for (Airport airport : airportsList) {
            if(!getSelectedPlane().isInRange(airport)) break;

            grid.add(createAirportButton(airport), 1, airportsList.indexOf(airport));
        }

        viewableDestinationsList.setContent(grid);
    }

    private Button createAirportButton(Airport airport) {

        Button button = new Button("City: " + airport.getCityName() + "\n" +
                                   "Airport: " + airport.getAirportName() + "\n" +
                                   "Distance: " + getSelectedPlane().getAirport().compareTo(airport) + "km");
        button.wrapTextProperty().setValue(true);
        button.setStyle("-fx-text-alignment: center;");
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> handleSetDestination(airport));
        button.setMaxWidth(DESTINATION_BUTTON_MAX_WIDTH);
        button.setMinWidth(DESTINATION_BUTTON_MIN_WIDTH);
        button.setMaxHeight(DESTINATION_BUTTON_MAX_HEIGHT);
        button.setMinHeight(DESTINATION_BUTTON_MIN_HEIGHT);

        if(!getSelectedPlane().isInRange(airport)) button.setDisable(true);
        else button.setDisable(false);

        return button;

    }

    private void emptyDestinationsList() {
        viewableDestinationsList.setContent(null);
    }



    private void loadTravellersList(Airport airport) {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(GRIDPANE_PADDING));
        grid.setHgap(BUTTON_HORIZONTAL_GAP);
        grid.setVgap(BUTTON_VERTICAL_GAP);


        List<Passenger> passengersList = getSelectedPlane().getPassengers();
        for (Passenger passenger : passengersList) {
            grid.add(createPassengerButton(passenger), 1, passengersList.indexOf(passenger));
        }


        List<Passenger> travellersList = airport.getTravellers();
        if(travellersList.size() <= 0) {
            viewableTravellersList.setContent(new Text("There are no travellers at this moment"));
            return;
        }

        travellersList.sort((a, b) -> airport.compareTo(a.getDestination()) - airport.compareTo(b.getDestination()));
        for (Passenger passenger : travellersList) {
            grid.add(createTravellerButton(passenger), 1, travellersList.indexOf(passenger) + passengersList.size());
        }

        viewableTravellersList.setContent(grid);
    }

    private Button createTravellerButton(Passenger passenger) {

        Button button = new Button(passenger.getFullName() + "\n" + 
                                   "Paying: " + passenger.getPaying() + ",-\n" +
                                   "Destination: " + passenger.getDestination().getCityName());
        button.wrapTextProperty().setValue(true);
        button.setStyle("-fx-text-alignment: center;");
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> handleBoardPassenger(passenger));
        button.setMaxWidth(TRAVELLER_BUTTON_MAX_WIDTH);
        button.setMinWidth(TRAVELLER_BUTTON_MIN_WIDTH);
        button.setMaxHeight(TRAVELLER_BUTTON_MAX_HEIGHT);
        button.setMinHeight(TRAVELLER_BUTTON_MIN_HEIGHT);

        if(!getSelectedPlane().hasMoreEmptySeats()) {
            button.setDisable(true);        
        }

        return button;

    }

    private Button createPassengerButton(Passenger passenger) {

        Button button = new Button(passenger.getFullName() + "\n" + 
                                   "Paying: " + passenger.getPaying() + ",-\n" +
                                   "Destination: " + passenger.getDestination().getCityName());
        button.wrapTextProperty().setValue(true);
        button.setStyle("-fx-text-alignment: center;");
        button.setStyle("-fx-background-color: #00FF00;");
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> handleUnBoardPassenger(passenger));
        button.setMaxWidth(TRAVELLER_BUTTON_MAX_WIDTH);
        button.setMinWidth(TRAVELLER_BUTTON_MIN_WIDTH);
        button.setMaxHeight(TRAVELLER_BUTTON_MAX_HEIGHT);
        button.setMinHeight(TRAVELLER_BUTTON_MIN_HEIGHT);

        return button;

    }

    private void emptyTravellersList() {
        viewableTravellersList.setContent(null);
    }



    private void refreshInterface() {
        setAirlineNameHeader();
        setAirlineCoins();
        emptyDestinationsList();
        emptyTravellersList();
        resetPlaneInfo();
        loadPlanesList();
    }

    private void showPlaneInfo(Plane plane) {
        setAirportNameLabel(plane);
        setManufacturerAndModelLabel(plane);
        setPassengerCountLabel(plane);
        setDestinationAirportLabel(plane);
        setDistanceLabel(plane);
        setProfitLabel(plane);
        loadDestinationsList(plane);
        loadTravellersList(plane.getAirport());
         
        refreshTakeOffButton();
    }

    private void updatePlaneInfo() {
        setPassengerCountLabel(getSelectedPlane());
        setDestinationAirportLabel(getSelectedPlane());
        setDistanceLabel(getSelectedPlane());
        setProfitLabel(getSelectedPlane());
    }

    private void resetPlaneInfo() {
        setAirportNameLabel("");
        setManufacturerAndModelLabel("");
        setPassengerCountLabel("");
        resetDistanceLabel();
        resetDestinationLabel();
        resetProfitLabel();
    }

    private void setAirportNameLabel(Plane plane) {
        setAirportNameLabel(plane.getAirport().getAirportName());
    }
    private void setAirportNameLabel(String text) {
        airportNameLabel.setText(text);
    }

    private void setManufacturerAndModelLabel(Plane plane) {
        setManufacturerAndModelLabel(plane.getAircraft().getManufacturer() + " " + plane.getAircraft().getModel());
    }
    private void setManufacturerAndModelLabel(String text) {
        manufacturerAndModelLabel.setText(text);
    }

    private void setPassengerCountLabel(Plane plane) {
        setPassengerCountLabel(plane.getPassengerCount() + "/" + plane.getAircraft().getSeats());
    }
    private void setPassengerCountLabel(String text) {
        passengerCountLabel.setText(text);
    }

    private void setDistanceLabel(Plane plane) {
        Airport destination = plane.getDestination();
        if(Objects.isNull(destination)) resetDistanceLabel();
        else setDistanceLabel(plane.getAirport().compareTo(plane.getDestination()) + "km");
    }
    private void setDistanceLabel(String text) {
        distanceLabel.setText(text);
    }
    private void resetDistanceLabel() {
        distanceLabel.setText("Destination is not set");
    }

    private void setProfitLabel(Plane plane) {
        Airport destination = plane.getDestination();
        if(Objects.isNull(destination)) resetProfitLabel();
        else setProfitLabel(plane.getProfit() + " coins");
    }
    private void setProfitLabel(String text) {
        profitLabel.setText(text);
    }
    private void resetProfitLabel() {
        profitLabel.setText("Destination is not set");
    }

    private void setDestinationAirportLabel(Plane plane) {
        Airport destination = plane.getDestination();
        if(Objects.isNull(destination)) resetDestinationLabel();
        else setDestinationLabel(destination.getAirportName());
    }
    private void setDestinationLabel(String text) {
        destinationAirportLabel.setText(text);
    }
    private void resetDestinationLabel() {
        destinationAirportLabel.setText("Destination is not set");
    }

    private void refreshTakeOffButton() {
        if(getSelectedPlane().isReadyForTakeOff()) enableTakeOffButton();
        else disableTakeOffButton();
    }

    private void disableTakeOffButton() {
        takeOffButton.setDisable(true);
    }

    private void enableTakeOffButton() {
        takeOffButton.setDisable(false);
    }



    private void setAirlineNameHeader() {
        airlineName.setText(game.getAirline().getName());
    }

    private void setTravellersRefreshTimer() {
        travellersRefreshTimer.setText("Refreshing travellers in: " + game.refreshingTravellersIn() + " min");
    }

    private void setAirlineCoins() {
        airlineCoins.setText("Coins: " + game.getAirline().getCoinAmount());
    }



    private void setSelectedPlane(Plane plane) {
        try {
            game.setSelectedPlane(plane);
        }
        catch (IllegalArgumentException e) {
            showAlert(e.getMessage());
        }
    }

    private Plane getSelectedPlane() {
        return game.getSelectedPlane();
    }



    private void boardPassenger(Passenger passenger) {
        try {
            game.boardPassenger(getSelectedPlane(), passenger);
        }
        catch (IllegalArgumentException e) {
            showAlert(e.getMessage());
        }
    }

    private void unBoardPassenger(Passenger passenger) {
        try {
            game.unBoardPassenger(getSelectedPlane(), passenger);
        }
        catch (IllegalArgumentException e) {
            showAlert(e.getMessage());
        }
    }



    @FXML
    public void handlePlaneSelect(Plane plane) {
        setSelectedPlane(plane);
        showPlaneInfo(plane);
        System.out.println(plane + " selected...");
    }

    @FXML
    public void handleBoardPassenger(Passenger passenger) {
        boardPassenger(passenger);
        updatePlaneInfo();
        loadTravellersList(getSelectedPlane().getAirport());
    }

    @FXML
    public void handleUnBoardPassenger(Passenger passenger) {
        unBoardPassenger(passenger);
        updatePlaneInfo();
        loadTravellersList(getSelectedPlane().getAirport());
    }

    @FXML
    public void handleSetDestination(Airport airport) {
        try {
            getSelectedPlane().setDestination(airport);
            updatePlaneInfo();
            refreshTakeOffButton();
        }
        catch (IllegalArgumentException e) {
            showAlert(e.getMessage());
        }
    }

    @FXML
    public void handleTakeOff() {
        try {
            getSelectedPlane().takeOff();
            refreshInterface();
        }
        catch (IllegalArgumentException e) {
            showAlert(e.getMessage());
        }
        catch (IllegalStateException e) {
            showAlert(e.getMessage());
        }
    }

    @FXML
    public void handleRenameAirline() {
        nameAirlineDialog();
    }


    // ***************
    // Aircrafts Tab
    // ***************

    private void refreshAircraftsTab() {
        loadBuyableAircraftsList();
    }

    private void loadBuyableAircraftsList() {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(GRIDPANE_PADDING));
        grid.setHgap(BUTTON_HORIZONTAL_GAP);
        grid.setVgap(BUTTON_VERTICAL_GAP);


        
        List<Aircraft> aircraftsList = game.getAircrafts();
        if(aircraftsList.size() <= 0) showAlert("Try reloading the game. There are no aircrafts.");

        for (Aircraft aircraft : aircraftsList) {
            grid.add(createAircraftButton(aircraft), 1, aircraftsList.indexOf(aircraft));
        }

        viewableBuyableAircraftsList.setContent(grid);
        
    }

    private Button createAircraftButton(Aircraft aircraft) {
        Button button;

        button = new Button(aircraft.getManufacturer() + " " + aircraft.getModel());
        button.setDisable(false);
        
        button.wrapTextProperty().setValue(true);
        button.setStyle("-fx-text-alignment: center;");
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> handleAircraftSelect(aircraft));
        button.setMaxWidth(AIRCRAFT_BUTTON_MAX_WIDTH);
        button.setMinWidth(AIRCRAFT_BUTTON_MIN_WIDTH);
        button.setMaxHeight(AIRCRAFT_BUTTON_MAX_HEIGHT);
        button.setMinHeight(AIRCRAFT_BUTTON_MIN_HEIGHT);

        return button;    
    }



    private void showAircraftInfo(Aircraft aircraft) {
        setSelectedAircraftLabel(aircraft);
        setSeatsLabel(aircraft);
        setPriceLabel(aircraft);
        setEfficiencyLabel(aircraft);
        setRangeLabel(aircraft);
        setSpeedLabel(aircraft);
    }

    private void setSelectedAircraftLabel(Aircraft aircraft) {
        selectedAircraftLabel.setText(aircraft.getManufacturer() + " " + aircraft.getModel());
    }

    private void setSeatsLabel(Aircraft aircraft) {
        seatsLabel.setText("Seats: " + aircraft.getSeats());
    }

    private void setPriceLabel(Aircraft aircraft) {
        priceLabel.setText("Price: " + aircraft.getPrice() + " coins");
    }

    private void setRangeLabel(Aircraft aircraft) {
        rangeLabel.setText("Range: " + aircraft.getRange() + "km");
    }

    private void setEfficiencyLabel(Aircraft aircraft) {
        efficiencyLabel.setText("Efficiency: " + aircraft.getEfficiency() + " coins per kilometer");
    }

    private void setSpeedLabel(Aircraft aircraft) {
        speedLabel.setText("Speed: " + aircraft.getSpeed() + "km/h");
    }



    private void refreshBuyButton(Aircraft aircraft) {
        try {
            if(game.airlineCanBuy(aircraft)) enableBuyButton();
            else disableBuyButton();
        }
        catch (IllegalArgumentException e) {
            showAlert(e.getMessage());
        }
    }

    private void disableBuyButton() {
        buyPlaneButton.setDisable(true);
    }

    private void enableBuyButton() {
        buyPlaneButton.setDisable(false);
    }



    private void setSelectedAircraft(Aircraft aircraft) {
        try {
            game.setSelectedAircraft(aircraft);
        } catch (IllegalArgumentException e) {
            showAlert(e.getMessage());
        }
    }

    private Aircraft getSelectedAircraft() {
        return game.getSelectedAircraft();
    }


    @FXML
    public void handleAircraftSelect(Aircraft aircraft) {
        setSelectedAircraft(aircraft);
        showAircraftInfo(aircraft);
        refreshBuyButton(aircraft);
        System.out.println(aircraft.getManufacturer() + " " + aircraft.getModel() + " selected...");
    }

    @FXML
    public void handleBuyAircraft() {
        try {
            game.airlineBuy(getSelectedAircraft());
            loadPlanesList();
        }
        catch (IllegalArgumentException e) {
            showAlert(e.getMessage());
        }
    }



    // *****************
    // Alert
    // *****************

    private void showAlert(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Alert");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }



    // *****************
    // Save and load
    // *****************

    @FXML
    public void handleSaveGame() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save game");
        dialog.setHeaderText("What do you want to name the save?");
        dialog.setContentText("Name:");

        try {
            String saveName = dialog.showAndWait().get();
            game.saveGame(saveName);
            // checkoutReceipt.writeReceipt(receiptName, selfCheckout);
        }
        catch (IllegalArgumentException e) {
            showAlert(e.getMessage());
            handleSaveGame();
        }
        catch (NoSuchElementException e) {
            System.err.println("Error naming save file: " + e.getMessage());
            System.out.println("Aborting...");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML void handleLoadGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(getClass().getResource("gamefiles/").getPath()));
        fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        try {
            File gameSave = fileChooser.showOpenDialog(new Stage());
            if(Objects.isNull(gameSave)) return;
            game.loadGameSave(gameSave);
            resetPanel();
        }
        catch (IllegalArgumentException e) {
            showAlert(e.getMessage());
            handleLoadGame();
        }
        catch (FileNotFoundException e) {
            System.err.println("Error when loading file " + e.getMessage());
            showAlert(e.getMessage());
            System.out.println("Select a new file...");
            handleLoadGame();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    



}
