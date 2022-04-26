package airlineManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;

public class GameSaveHandler implements InterfaceGameSaveHandler {

    private final String folder = "gamefiles/";
    private final String format = ".txt";

    @Override
    public void save(String fileName, AirlineManagerGame game) {
        try (PrintWriter writer = new PrintWriter(new File(this.getPathToGameFiles() + fileName + format))) {
            
            Airline airline = game.getAirline();

            writer.println("***VALID");
            writer.println("");

            writer.println("***AIRLINE");
            writer.println(airline.getName() + "," +
                           airline.getCoinAmount());

            writer.println("");

            writer.println("***PLANES");
            List<Plane> planes = airline.getPlanes();
            planes.forEach(plane -> writer.println(plane.getNickName() + "," +
                                                   plane.getAircraft().getAircraftID()));
            
        } 
        catch (FileNotFoundException e) {
            System.err.println(e);
        }
    }

    @Override
    public Airline load(File file, AirlineManagerGame game) {
        // TODO Auto-generated method stub
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
