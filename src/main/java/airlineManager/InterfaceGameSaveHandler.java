package airlineManager;

import java.io.File;
import java.io.FileNotFoundException;

public interface InterfaceGameSaveHandler {

    public void save(String filename, AirlineManagerGame game) throws IllegalArgumentException;

	public Airline load(File file, AirlineManagerGame game) throws FileNotFoundException, IllegalArgumentException;

    public File checkForExistingValidGameSave(String defaultGameSaveName);

    public boolean isValidGameSave(File file);
    
}
