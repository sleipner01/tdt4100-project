package airlineManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

public class GameSaveHandler implements InterfaceGameSaveHandler {

    @Override
    public void save(String fileName, AirlineManagerGame game) throws FileNotFoundException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Airline load(File file, AirlineManagerGame game) throws FileNotFoundException {
        // TODO Auto-generated method stub
        if(Objects.isNull(this.getClass().getResource("gameSave.txt"))) return null;
        return null;
    }

    @Override
    public File checkForExistingValidGameSave(String defaultGameSaveName) {
        try {
            File gameSave = new File(this.getClass().getResource("gamefiles/" + defaultGameSaveName + ".txt").getPath());
            if(gameSave.isFile()) {
                if(this.isValidGameSave(gameSave)) return gameSave;
                else return null;
            }
        }
        catch (NullPointerException e) {
            System.out.println(e);
            return null;
        }
        return null;
    }

    @Override
    public boolean isValidGameSave(File file) {
        return true;
    }
    
}
