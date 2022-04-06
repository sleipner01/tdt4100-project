package airlineManager;

import java.io.FileNotFoundException;

public interface InterfaceFileHandler {

    public void save(String filename, AirlineManagerGame game) throws FileNotFoundException;

	public AirlineManagerGame load(String filename) throws FileNotFoundException;
    
}
