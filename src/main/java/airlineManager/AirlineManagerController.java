package airlineManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class AirlineManagerController {

    // For development, remove later
    private Aircraft testAircraft = new Aircraft("Boeing", "737", "Passenger", new Livery("aids.png"), 210, 200, 1, 500, 4);

    private AirlineManagerGame game;
    private InterfaceFileHandler saveHandler = new GameSaveHandler();

    private final int PLANE_BUTTON_PADDING = 4;

    @FXML
    public Button takeOff;

    @FXML
    public ScrollPane viewableTravellersList, viewableDestinationsList, viewablePlanesList;


    @FXML
    public void initialize() {
        // TODO: Check for savefile
        this.game = new AirlineManagerGame();
        this.game.getAirline().buy(testAircraft);

        this.initializePlanesList();

    }

    private void initializePlanesList() {
        // List<Button> planes = new ArrayList<>();
        // this.viewablePlanesList.getItems().setAll(planes);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(PLANE_BUTTON_PADDING));
        grid.setHgap(PLANE_BUTTON_PADDING);
        grid.setVgap(PLANE_BUTTON_PADDING);

        List<Plane> planesList = this.game.getAirline().getPlanes();
        for (Plane plane : planesList) {
            grid.add(this.createPlaneButton(plane),planesList.indexOf(plane),1);
        }
        
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
    public void handlePlaneSelect(Plane plane ) {

    }
    
}
