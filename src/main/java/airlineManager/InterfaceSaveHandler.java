package airlineManager;

import java.io.FileNotFoundException;

public interface InterfaceSaveHandler {

    public void save(String filename, AirlineManagerGame game) throws FileNotFoundException;

	public AirlineManagerGame load(String filename) throws FileNotFoundException;
    
}
