package airlineManager;

import java.io.File;

public interface InterfaceGameSaveHandler {

    public void save(String filename, AirlineManagerGame game);

	public Airline load(File file, AirlineManagerGame game);

    public File checkForExistingValidGameSave(String defaultGameSaveName);

    public boolean isValidGameSave(File file);
    
}
