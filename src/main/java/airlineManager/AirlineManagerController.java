package airlineManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class AirlineManagerController {

    // For development, remove later
    private Aircraft testAircraft = new Aircraft("Boeing", "737", "Passenger", 210, 200, 1, 500, 4);



    private AirlineManagerGame game;
    private InterfaceFileHandler saveHandler = new GameSaveHandler();
    private Plane selectedPlane;

    private final int PLANE_BUTTON_PADDING = 20;
    private final int BUTTON_HORIZONTAL_GAP = 0;
    private final int BUTTON_VERTICAL_GAP = 0;

    @FXML
    public Button takeOff;

    @FXML
    public Text airlineName, manufacturerAndModelLabel, passengerCountLabel, destinationAirportLabel, profitLabel, airportNameLabel;

    @FXML
    public ScrollPane viewableTravellersList, viewableDestinationsList, viewablePlanesList;


    @FXML
    public void initialize() {
        // TODO: Check for savefile
        boolean hasSaveFile = false;
        String saveFileName = "aids.txt";

        if(hasSaveFile) this.game = new AirlineManagerGame(saveFileName);
        else this.game = new AirlineManagerGame();


        String airlineName;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Welcome to Airline Manager");
        dialog.setHeaderText("What do you want to name your airline?");
        dialog.setContentText("Name:");
        try {
            airlineName = dialog.showAndWait().get();
            this.game.getAirline().rename(airlineName);
        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }


        // TODO: Remove, only for development
        this.game.getAirline().buy(testAircraft);


        this.setAirlineNameHeader();


        this.loadPlanesList();

    }



    private void setAirlineNameHeader() {
        this.airlineName.setText(this.game.getAirline().getName());
    }



    private void loadPlanesList() {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(PLANE_BUTTON_PADDING));
        grid.setHgap(BUTTON_HORIZONTAL_GAP);
        grid.setVgap(BUTTON_VERTICAL_GAP);

        List<Plane> planesList = this.game.getAirline().getPlanes();
        for (Plane plane : planesList) {
            grid.add(this.createPlaneButton(plane),planesList.indexOf(plane),1);
        }

        viewablePlanesList.setContent(grid);
        
    }



    private Button createPlaneButton(Plane plane) {
        Button button = new Button(plane.toString());
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
        this.selectedPlane = plane;
        showPlaneInfo(plane);
        System.out.println(plane + " selected...");
    }

    private void showPlaneInfo(Plane plane) {
        setAirportNameLabel(plane);
        setManufacturerAndModelLabel(plane);
        setPassengerCountLabel(plane);
        setDestinationAirportLabel(plane);
        loadAirportsList(plane);
        loadTravellersList(plane.getAirport());
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

    private void setDestinationAirportLabel(Plane plane) {
        Airport destination = plane.getDestination();
        if(Objects.isNull(destination)) destinationAirportLabel.setText("Not set");
        else destinationAirportLabel.setText(destination.getAirportName());
    }



    private Button createAirportButton(Airport airport) {

        Button button = new Button(airport.getAirportName());
        button.wrapTextProperty().setValue(true);
        button.setStyle("-fx-text-alignment: center;");
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> handleSetDestination(airport));
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);

        return button;

    }



    @FXML
    public void handleSetDestination(Airport airport) {
        this.selectedPlane.setDestination(airport);
        setDestinationAirportLabel(this.selectedPlane);
        System.out.println(airport);
    }



    private void loadAirportsList(Plane plane) {
        // TODO: Load except current airport, sort based on distance

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(PLANE_BUTTON_PADDING));
        grid.setHgap(BUTTON_HORIZONTAL_GAP);
        grid.setVgap(BUTTON_VERTICAL_GAP);

        List<Airport> airportsList = this.game.getAirports();
        for (Airport airport : airportsList) {
            grid.add(this.createAirportButton(airport), 1, airportsList.indexOf(airport));
        }

        viewableDestinationsList.setContent(grid);
        
    }


    private void loadTravellersList(Airport airport) {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(PLANE_BUTTON_PADDING));
        grid.setHgap(BUTTON_HORIZONTAL_GAP);
        grid.setVgap(BUTTON_VERTICAL_GAP);

        List<Passenger> travellersList = airport.getTravellers();
        for (Passenger passenger : travellersList) {
            grid.add(createTravellerButton(passenger), 1, travellersList.indexOf(passenger));
        }

        viewableTravellersList.setContent(grid);
    }



    private Button createTravellerButton(Passenger passenger) {

        Button button = new Button(passenger.getName());
        button.wrapTextProperty().setValue(true);
        button.setStyle("-fx-text-alignment: center;");
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> handlePassenger(passenger));
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);

        return button;

    }



    @FXML
    public void handlePassenger(Passenger passenger) {
        //TODO: If boarded, kick off, of not, board
        System.out.println(passenger);
    }
    
}
