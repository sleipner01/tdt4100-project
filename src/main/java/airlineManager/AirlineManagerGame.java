package airlineManager;

import java.io.BufferedReader;
import java.io.FileReader;  
import java.io.IOException;  
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class AirlineManagerGame {

    private final String CONFIG_FILE_PATH = "src/main/resources/airlineManager/config.properties";
    private String PATH;
    private String AIRCRAFTS_FILE_NAME;
    private String AIRPORTS_FILE_NAME;

    private Airline airline;
    public List<Airport> availableAirports = new ArrayList<>();
    private List<Aircraft> availableAircrafts = new ArrayList<>();
    private List<Flight> flights = new ArrayList<>();


    public AirlineManagerGame() {
        // Setting up initial values from config file
        try (FileReader reader = new FileReader(CONFIG_FILE_PATH)) {
            Properties properties = new Properties();
            properties.load(reader);

            this.PATH = properties.getProperty("resourcesPath");
            this.AIRCRAFTS_FILE_NAME = properties.getProperty("aircraftsFileName");
            this.AIRPORTS_FILE_NAME = properties.getProperty("airportsFileName");

        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void load() {
        loadAircraft(AIRCRAFTS_FILE_NAME);
        loadAirports(AIRPORTS_FILE_NAME);
        
        // If there exists a save, start from save - Initialize the airline
    }



    private void loadAircraft(String fileName) {
        String temporaryLine = "";    

        try {  
            // Parsing a CSV file into BufferedReader class constructor  
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.PATH + fileName));  
            bufferedReader.readLine(); // Skip first line in the CSV document to skip the header.

            while ((temporaryLine = bufferedReader.readLine()) != null) {
                String[] aircraftInfo = temporaryLine.split(",");

                availableAircrafts.add(
                    new Aircraft(aircraftInfo[0], 
                                 aircraftInfo[1], 
                                 aircraftInfo[2], 
                                 new Livery(aircraftInfo[3]), 
                                 Integer.parseInt(aircraftInfo[4]), 
                                 Integer.parseInt(aircraftInfo[5]), 
                                 Integer.parseInt(aircraftInfo[6]), 
                                 Integer.parseInt(aircraftInfo[7]), 
                                 Integer.parseInt(aircraftInfo[8])
                    )
                );
            } 
            bufferedReader.close();
        }   
        catch (IOException e) {  
            e.printStackTrace();  
        }  

        System.out.println(availableAircrafts);

    }



    private void loadAirports(String fileName) {
        String temporaryLine = "";    

        try {  
            // Parsing a CSV file into BufferedReader class constructor  
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.PATH + fileName));  
            bufferedReader.readLine(); // Skip first line in the CSV document to skip the header.

            while ((temporaryLine = bufferedReader.readLine()) != null) {
                String[] airportInfo = temporaryLine.split(",");

                availableAirports.add(
                    new Airport(airportInfo[3], 
                                Integer.parseInt(airportInfo[4]), 
                                airportInfo[0], Integer.parseInt(airportInfo[1]), Integer.parseInt(airportInfo[2])
                    )
                );
            } 
            bufferedReader.close();
        }   
        catch (IOException e) {  
            e.printStackTrace();  
        }  

        System.out.println(availableAirports);

    }



    public static void main(String[] args) {
        AirlineManagerGame game = new AirlineManagerGame();
        game.load();        
    }


}