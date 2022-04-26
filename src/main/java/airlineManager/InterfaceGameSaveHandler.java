package airlineManager;

import java.io.File;
import java.io.FileNotFoundException;

public interface InterfaceGameSaveHandler {

    public void save(String filename, AirlineManagerGame game) throws FileNotFoundException;

	public Airline load(File file, AirlineManagerGame game) throws FileNotFoundException;

    public File checkForExistingValidGameSave(String defaultGameSaveName);

    public boolean isValidGameSave(File file);
    
}
