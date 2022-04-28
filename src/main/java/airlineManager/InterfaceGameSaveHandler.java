package airlineManager;

import java.io.File;

public interface InterfaceGameSaveHandler {

    public void save(String filename, AirlineManagerGame game) throws IllegalArgumentException;

	public Airline load(File file, AirlineManagerGame game) throws IllegalArgumentException;

    public File checkForExistingValidGameSave(String defaultGameSaveName);

    public boolean isValidGameSave(File file);
    
}
