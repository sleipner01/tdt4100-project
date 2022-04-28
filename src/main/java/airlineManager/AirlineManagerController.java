package airlineManager;

import java.io.File;
import java.util.Collections;
import java.util.List;
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

    private final int PLANE_BUTTON_PADDING = 10;
    private final int BUTTON_HORIZONTAL_GAP = 0;
    private final int BUTTON_VERTICAL_GAP = 0;

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

        String airlineName;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Welcome to Airline Manager");
        dialog.setHeaderText("What do you want to name your airline?");
        dialog.setContentText("Name:");
        try {
            airlineName = dialog.showAndWait().get();
            game.getAirline().rename(airlineName);
        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }

    }

    public void resetPanel() {
        setAirlineNameHeader();
        setAirlineCoins();
        refreshInterface();
        refreshAircraftsTab();
    }



    // ***********
    // Time 
    // ***********

    private void addControllerToGameClock() {
        game.addToGameClock(this);
    }

    @Override
    public void tick() {
        // Only refreshing certain parts to keep the game running smoothly
        setTravellersRefreshTimer(game.refreshingTravellersIn());
        setAirlineCoins();
        refreshPlanesInFlight();
    }



    // ************
    // Interface 
    // ************

    private void loadPlanesList() {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(PLANE_BUTTON_PADDING));
        grid.setHgap(BUTTON_HORIZONTAL_GAP);
        grid.setVgap(BUTTON_VERTICAL_GAP);


        
        List<Plane> planesList = game.getAirline().getPlanes();
        for (Plane plane : planesList) {
            grid.add(createPlaneButton(plane),planesList.indexOf(plane),1);
        }

        viewablePlanesList.setContent(grid);
        
    }

    // To keep the grid from having to update constantly, this only updates the flights in air.
    private void refreshPlanesInFlight() throws IllegalStateException {

        Node viewablePlanesListContent = viewablePlanesList.getContent();
        if(!(viewablePlanesListContent instanceof GridPane))
            throw new IllegalStateException("No can do. This is not a gridpane");

        GridPane viewablePlanesListGridPane = (GridPane)viewablePlanesListContent;
        ObservableList<Node> gridPanePlaneButtons = viewablePlanesListGridPane.getChildren();

        List<Plane> planesList = game.getAirline().getPlanes();
        // The two lists should be the same length, one with buttons, the other with planes..
        if(!(gridPanePlaneButtons.size() == planesList.size())) {
            loadPlanesList(); // In case a new aircraft have been bought
            if(!(gridPanePlaneButtons.size() == planesList.size()))
                throw new IllegalStateException("The two list are not of the same size. Something is wrong.");
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
                                   plane.getRemainingFlightTimeInMinutes() + " min");
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
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);



        return button;
    }



    private void loadDestinationsList(Plane plane) {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(PLANE_BUTTON_PADDING));
        grid.setHgap(BUTTON_HORIZONTAL_GAP);
        grid.setVgap(BUTTON_VERTICAL_GAP);

        List<Airport> airportsList = game.getAirports();
        airportsList.remove(plane.getAirport());
        Collections.sort(airportsList, new AirportDistanceComparator(plane.getAirport()));
        // airportsList.sort((a, b) -> plane.getAirport().compareTo(a) - plane.getAirport().compareTo(b));

        for (Airport airport : airportsList) {
            grid.add(createAirportButton(airport), 1, airportsList.indexOf(airport));
        }

        viewableDestinationsList.setContent(grid);
    }

    private Button createAirportButton(Airport airport) {

        Button button = new Button(airport.getAirportName() + "\n"
                                   + getSelectedPlane().getAirport().compareTo(airport) + "km");
        button.wrapTextProperty().setValue(true);
        button.setStyle("-fx-text-alignment: center;");
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> handleSetDestination(airport));
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);

        if(!getSelectedPlane().isInRange(airport)) button.setDisable(true);
        else button.setDisable(false);

        return button;

    }

    private void emptyDestinationsList() {
        viewableDestinationsList.setContent(null);
    }



    private void loadTravellersList(Airport airport) {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(PLANE_BUTTON_PADDING));
        grid.setHgap(BUTTON_HORIZONTAL_GAP);
        grid.setVgap(BUTTON_VERTICAL_GAP);


        List<Passenger> passengersList = getSelectedPlane().getPassengers();
        for (Passenger passenger : passengersList) {
            grid.add(createPassengerButton(passenger), 1, passengersList.indexOf(passenger));
        }


        List<Passenger> travellersList = airport.getTravellers();
        // AirportDistanceComparator comparator = new AirportDistanceComparator(airport);
        // travellersList.sort(comparator.compare(a.getDestination(), b.getDestination()));
        travellersList.sort((a, b) -> airport.compareTo(a.getDestination()) - airport.compareTo(b.getDestination()));
        for (Passenger passenger : travellersList) {
            grid.add(createTravellerButton(passenger), 1, travellersList.indexOf(passenger) + passengersList.size());
        }

        viewableTravellersList.setContent(grid);
    }

    private Button createTravellerButton(Passenger passenger) {

        Button button = new Button(passenger.getFullName() + "\n" + 
                                   passenger.getPaying() + "\n" +
                                   passenger.getDestination().getAirportName());
                                   button.wrapTextProperty().setValue(true);
        button.setStyle("-fx-text-alignment: center;");
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> handleBoardPassenger(passenger));
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);
        if(!getSelectedPlane().hasMoreEmptySeats()) {
            button.setDisable(true);        
        }

        return button;

    }

    private Button createPassengerButton(Passenger passenger) {

        Button button = new Button(passenger.getFullName() + "\n" + 
                                   passenger.getPaying() + "\n" +
                                   passenger.getDestination().getAirportName());
        button.wrapTextProperty().setValue(true);
        button.setStyle("-fx-text-alignment: center;");
        button.setStyle("-fx-background-color: #00FF00;");
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> handleUnBoardPassenger(passenger));
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);

        return button;

    }

    private void emptyTravellersList() {
        viewableTravellersList.setContent(null);
    }



    private void refreshInterface() {
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
        airportNameLabel.setText("");
        manufacturerAndModelLabel.setText("");
        passengerCountLabel.setText("");
        destinationAirportLabel.setText("");
        profitLabel.setText("");
    }

    private void setAirportNameLabel(Plane plane) {
        airportNameLabel.setText(plane.getAirport().getAirportName());
    }

    private void setManufacturerAndModelLabel(Plane plane) {
        manufacturerAndModelLabel.setText(plane.getAircraft().getManufacturer() + " " + plane.getAircraft().getModel());
    }

    private void setPassengerCountLabel(Plane plane) {
        passengerCountLabel.setText(plane.getPassengerCount() + "/" + plane.getAircraft().getSeats());
    }

    private void setDistanceLabel(Plane plane) {
        Airport destination = plane.getDestination();
        if(Objects.isNull(destination)) distanceLabel.setText("Destination is not set");
        else distanceLabel.setText(plane.getAirport().compareTo(plane.getDestination()) + "km");
        
    }

    private void setProfitLabel(Plane plane) {
        Airport destination = plane.getDestination();
        if(Objects.isNull(destination)) profitLabel.setText("Destination is not set");
        else profitLabel.setText(plane.getProfit() + " coins");
        
    }

    private void setDestinationAirportLabel(Plane plane) {
        Airport destination = plane.getDestination();
        if(Objects.isNull(destination)) destinationAirportLabel.setText("Not set");
        else destinationAirportLabel.setText(destination.getAirportName());
        

        
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

    private void setTravellersRefreshTimer(int time) {
        travellersRefreshTimer.setText("Refreshing travellers in: " + time + " min");
    }

    private void setAirlineCoins() {
        airlineCoins.setText("Coins: " + game.getAirline().getCoinAmount());
    }



    private void setSelectedPlane(Plane plane) {
        game.setSelectedPlane(plane);
    }

    private Plane getSelectedPlane() {
        return game.getSelectedPlane();
    }



    private void boardPassenger(Passenger passenger) {
        game.boardPassenger(getSelectedPlane(), passenger);
    }

    private void unBoardPassenger(Passenger passenger) {
        game.unBoardPassenger(getSelectedPlane(), passenger);
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
        getSelectedPlane().setDestination(airport);
        updatePlaneInfo();
        refreshTakeOffButton();
    }

    @FXML
    public void handleTakeOff() {
        getSelectedPlane().takeOff();
        refreshInterface();
    }



    // ***************
    // Aircrafts Tab
    // ***************

    private void refreshAircraftsTab() {
        loadBuyableAircraftsList();
    }

    private void loadBuyableAircraftsList() {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(PLANE_BUTTON_PADDING));
        grid.setHgap(BUTTON_HORIZONTAL_GAP);
        grid.setVgap(BUTTON_VERTICAL_GAP);


        
        List<Aircraft> aircraftsList = game.getAircrafts();
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
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);

        return button;    
    }



    private void showAircraftInfo(Aircraft aircraft) {
        setSelectedAircraftLabel(aircraft);
        setSeatsLabel(aircraft);
        setPriceLabel(aircraft);
        setEfficiencyLabel(aircraft);
        setRangeLabel(aircraft);
        setSpeedLabel(aircraft);
         
        // if(!Objects.isNull(plane.getDestination())) enableTakeOffButton();
        // else disableTakeOffButton();
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
        if(game.airlineCanBuy(aircraft)) enableBuyButton();
        else disableBuyButton();
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
        game.airlineBuy(getSelectedAircraft());
        loadPlanesList();
        // refreshAircraftTab();
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
        catch (Exception e) {
            System.out.println(e);
        } 
        // catch (IOException e) {
        //     System.out.println(e);
        //     showErrorMessage("Kvitteringen kunne ikke skrives til fil!");
        // }
    }

    @FXML void handleLoadGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(getClass().getResource("gamefiles/").getPath()));
        fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
                    );

        try {
            File gameSave = fileChooser.showOpenDialog(new Stage());
            if(Objects.isNull(gameSave)) return;
            game.stop();
            game.loadGameSave(gameSave);
            resetPanel();
            game.start();
        }
        catch (Exception e) {
            System.out.println(e);
        } 
        // catch (IOException e) {
        //     System.out.println(e);
        //     showErrorMessage("");
        // }
    }
    



}
