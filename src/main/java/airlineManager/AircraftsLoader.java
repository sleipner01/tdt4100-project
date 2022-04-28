package airlineManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AircraftsLoader implements InterfaceGameFileLoader<Aircraft> {

    private final String REGEX_PATTERN = "[a-zA-Z]*";
    private final String FILE_FORMAT = ".csv";
    private final String CHARSET = "UTF-8";
    private final String GAMEFILES_FOLDER = "gamefiles/";


    @Override
    public List<Aircraft> load(String fileName) {

        if(!isValidFileName(fileName))
            throw new IllegalArgumentException("The filename can only include the name in ACHII characters, no path and no format.");

        
        List<Aircraft> aircrafts = new ArrayList<>();

        String temporaryLine = "";    

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(GAMEFILES_FOLDER + fileName + FILE_FORMAT), this.CHARSET))) {  
            bufferedReader.readLine(); // Skip first line in the CSV document to skip the header.

            System.out.println("\n\n********************\n");
            System.out.println("Starting to load aircrafts...");
            System.out.println("\n");

            while ((temporaryLine = bufferedReader.readLine()) != null) {
                String[] aircraftInfo = temporaryLine.split(",");

                try {
                    aircrafts.add(
                        new Aircraft(aircraftInfo[0],
                                     aircraftInfo[1],
                                     aircraftInfo[2],
                                     Integer.parseInt(aircraftInfo[4]),
                                     Integer.parseInt(aircraftInfo[5]),
                                     Integer.parseInt(aircraftInfo[6]),
                                     Integer.parseInt(aircraftInfo[7]),
                                     Integer.parseInt(aircraftInfo[8]),
                                     aircrafts.size(),
                                     new Livery(aircraftInfo[3])
                        )
                    );
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Something is wrong with the format of the line:\n" + temporaryLine + "\nin the file: " + fileName + FILE_FORMAT);
                }
                
                System.out.println(aircrafts.get(aircrafts.size()-1));
            } 

            System.out.println("\n");
            System.out.println("Loading complete!");
            System.out.println("\n");


        }   
        catch (IOException e) {  
            e.printStackTrace();  
        } 
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        return aircrafts;
    }



    private boolean isValidFileName(String fileName) {
        if(Pattern.matches(REGEX_PATTERN, fileName)) return true;
        return false;
    }
    
}
