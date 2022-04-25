package airlineManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class AirlineManagerController implements SecondClockListener {




    private AirlineManagerGame game;
    private Plane selectedPlane;
    private Aircraft selectedAircraft;

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



    @FXML
    public void initialize() {
        // TODO: Check for savefile
        boolean hasSaveFile = false;
        String saveFileName = "aids.txt";

        if(hasSaveFile) game = new AirlineManagerGame(saveFileName);
        else game = new AirlineManagerGame();

        addControllerToGameClock();
        
        // nameAirline();
        setAirlineNameHeader(game.getAirline().getName());
        setAirlineCoins(game.getAirline().getCoinAmount());
        game.refreshingTravellersIn();


        // Interface tab
        loadPlanesList();

        // Aircrafts tab
        loadBuyableAircraftsList();

    }



    private void nameAirline() {

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



    private void addControllerToGameClock() {
        game.addToGameClock(this);
    }



    private void setAirlineNameHeader(String name) {
        airlineName.setText(name);
    }



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

    // To keep the grid from having to update constantly, only the flights in air.
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



    @FXML
    public void handlePlaneSelect(Plane plane) {
        selectedPlane = plane;
        showPlaneInfo(plane);
        System.out.println(plane + " selected...");
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
        else distanceLabel.setText((int)CalculateFlightDistance.calculate(plane.getAirport(), plane.getDestination()) + "km");
        
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



    private Button createAirportButton(Airport airport) {

        Button button = new Button(airport.getAirportName() + "\n"
                                   + (int)CalculateFlightDistance.calculate(selectedPlane.getAirport(), airport) + "km");
        button.wrapTextProperty().setValue(true);
        button.setStyle("-fx-text-alignment: center;");
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> handleSetDestination(airport));
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);

        if(!selectedPlane.isInRange(airport)) button.setDisable(true);
        else button.setDisable(false);

        return button;

    }



    private void disableTakeOffButton() {
        takeOffButton.setDisable(true);
    }



    private void enableTakeOffButton() {
        takeOffButton.setDisable(false);
    }



    @FXML
    public void handleSetDestination(Airport airport) {
        selectedPlane.setDestination(airport);
        updatePlaneInfo();
        refreshTakeOffButton();
    }



    private void refreshTakeOffButton() {
        if(selectedPlane.isReadyForTakeOff()) enableTakeOffButton();
        else disableTakeOffButton();
    }



    private void updatePlaneInfo() {
        setPassengerCountLabel(selectedPlane);
        setDestinationAirportLabel(selectedPlane);
        setDistanceLabel(selectedPlane);
        setProfitLabel(selectedPlane);
    }



    private void emptyDestinationsList() {
        viewableDestinationsList.setContent(null);
    }



    private void loadDestinationsList(Plane plane) {
        // TODO: sort based on distance

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(PLANE_BUTTON_PADDING));
        grid.setHgap(BUTTON_HORIZONTAL_GAP);
        grid.setVgap(BUTTON_VERTICAL_GAP);

        List<Airport> airportsList = game.getAirports();
        airportsList.remove(plane.getAirport());
        airportsList.sort((a, b) -> (int)CalculateFlightDistance.calculate(plane.getAirport(), a) - (int)CalculateFlightDistance.calculate(plane.getAirport(), b));

        for (Airport airport : airportsList) {
            grid.add(createAirportButton(airport), 1, airportsList.indexOf(airport));
        }

        viewableDestinationsList.setContent(grid);
    }



    private void emptyTravellersList() {
        viewableTravellersList.setContent(null);
    }



    private void loadTravellersList(Airport airport) {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(PLANE_BUTTON_PADDING));
        grid.setHgap(BUTTON_HORIZONTAL_GAP);
        grid.setVgap(BUTTON_VERTICAL_GAP);


        List<Passenger> passengersList = selectedPlane.getPassengers();
        for (Passenger passenger : passengersList) {
            grid.add(createPassengerButton(passenger), 1, passengersList.indexOf(passenger));
        }

        List<Passenger> travellersList = airport.getTravellers();
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
        if(!selectedPlane.hasMoreEmptySeats()) {
            button.setDisable(true);        
        }

        return button;

    }



    @FXML
    public void handleBoardPassenger(Passenger passenger) {
        game.boardPassenger(selectedPlane, passenger);
        updatePlaneInfo();
        loadTravellersList(selectedPlane.getAirport());
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



    private void setTravellersRefreshTimer(int time) {
        travellersRefreshTimer.setText("Refreshing travellers in: " + time + " min");
    }



    @FXML
    public void handleUnBoardPassenger(Passenger passenger) {
        game.unBoardPassenger(selectedPlane, passenger);
        updatePlaneInfo();
        loadTravellersList(selectedPlane.getAirport());
    }



    @FXML
    public void handleTakeOff() {
        selectedPlane.takeOff();
        refreshDisplay();
    }



    private void refreshDisplay() {
        emptyDestinationsList();
        emptyTravellersList();
        resetPlaneInfo();
        loadPlanesList();
    }



    private void setAirlineCoins(int coins) {
        airlineCoins.setText("Coins: " + coins);
    }



    @Override
    public void tick() {
        // Sleep a little to make sure everything is loaded;
        setTravellersRefreshTimer(game.refreshingTravellersIn());
        setAirlineCoins(game.getAirline().getCoinAmount());
        refreshPlanesInFlight();
    }



    private void resetPlaneInfo() {
        airportNameLabel.setText("");
        manufacturerAndModelLabel.setText("");
        passengerCountLabel.setText("");
        destinationAirportLabel.setText("");
        profitLabel.setText("");
    }




    // ***************
    // Planes Tab
    // ***************



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


    @FXML
    public void handleAircraftSelect(Aircraft aircraft) {
        selectedAircraft = aircraft;
        showAircraftInfo(aircraft);
        refreshBuyButton(aircraft);
        System.out.println(aircraft.getManufacturer() + " " + aircraft.getModel() + " selected...");
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



    @FXML
    public void handleBuyAircraft() {
        game.airlineBuy(selectedAircraft);
        loadPlanesList();
        refreshAircraftTab();
    }



    private void refreshAircraftTab() {

    }
    
}
