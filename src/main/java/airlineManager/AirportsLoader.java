package airlineManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AirportsLoader implements InterfaceGameFileLoader<Airport> {

    private final String REGEX_PATTERN = "[a-zA-Z]*";
    private final String FILE_FORMAT = ".csv";
    private final String GAMEFILES_FOLDER = "gamefiles/";

    @Override
    public List<Airport> load(String fileName) {

        if(!isValidFileName(fileName))
            throw new IllegalArgumentException("The filename can only include the name in ACHII characters, no path and no format.");

        List<Airport> airports = new ArrayList<>();

        String temporaryLine = "";    

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(GAMEFILES_FOLDER + fileName + FILE_FORMAT)))) {  

            bufferedReader.readLine(); // Skip first line in the CSV document to skip the header.

            System.out.println("\n\n********************\n");
            System.out.println("Starting loading airports...");
            System.out.println("\n");

            while ((temporaryLine = bufferedReader.readLine()) != null) {
                String[] airportInfo = temporaryLine.split(",");

                airports.add(
                    new Airport(airportInfo[3], 
                                Integer.parseInt(airportInfo[4]),
                                Integer.parseInt(airportInfo[5]),
                                airportInfo[0],
                                Double.parseDouble(airportInfo[1]),
                                Double.parseDouble(airportInfo[2]),
                                airports.size()
                    )
                );

                System.out.println(airports.get(airports.size()-1));

            } 

            System.out.println("\n");
            System.out.println("Loading complete!");
            System.out.println("\n");


        }   
        catch (IOException e) {  
            e.printStackTrace();  
        }  

        return airports;
    }

    private boolean isValidFileName(String fileName) {
        if(Pattern.matches(REGEX_PATTERN, fileName)) return true;
        return false;
    }
}
