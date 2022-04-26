package airlineManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class GameSaveHandler implements InterfaceGameSaveHandler {

    private final String folder = "gamefiles/";
    private final String format = ".txt";

    @Override
    public void save(String fileName, AirlineManagerGame game) {
        try (PrintWriter writer = new PrintWriter(new File(this.getPathToGameFiles() + fileName + format))) {
            
            Airline airline = game.getAirline();

            writer.println("***VALID");
            writer.println();

            writer.println("***AIRLINE");
            writer.println("Airline," +
                            airline.getName() + "," +
                            airline.getCoinAmount());

            writer.println();

            writer.println("***PLANES");
            List<Plane> planes = airline.getPlanes();
            for (Plane plane : planes) {
                    //Plane info
                    String destinationID = (Objects.isNull(plane.getDestination())) ? "null" : Integer.toString(plane.getDestination().getAirportID());
                    writer.println("Plane," +
                                    plane.getNickName() + "," +
                                    plane.getAircraft().getAircraftID() + "," +
                                    plane.getAirport().getAirportID() + "," +
                                    destinationID + "," +
                                    plane.isInFlight() + "," +
                                    plane.getRemainingFlightTimeInMinutes()
                                    );

                    // Passengers
                    List<Passenger> passengers = plane.getPassengers();
                    for (Passenger passenger : passengers) {
                        writer.println("Passenger," + 
                                        passenger.getFullName() + "," +
                                        passenger.getDestination().getAirportID());
                    }

                writer.println();
            }
            
        } 
        catch (FileNotFoundException e) {
            System.err.println(e);
        }
    }

    @Override
    public Airline load(File file, AirlineManagerGame game) {
        if(!this.isValidGameSave(file)) throw new IllegalArgumentException("This is not a valid game file...");

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] data= scanner.nextLine().split(",");
            }
        }
        return selfCheckout;
        return null;
    }

    @Override
    public File checkForExistingValidGameSave(String defaultGameSaveName) {
        File gameSave = this.getFile(defaultGameSaveName);
        if(isValidGameSave(gameSave)) return gameSave;
        else return null;
    }

    @Override
    public boolean isValidGameSave(File file) {
        // Check if the file is stamped valid
        return true;
    }

    private File getFile(String fileName) {
        File file;
        try {
            file = new File(this.getClass().getResource(this.folder + fileName + this.format).getPath());
            if(file.isFile()) return file;
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    private String getPathToGameFiles() {
        return this.getClass().getResource(folder).getFile();
    }
    
}
