package airlineManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;  
import java.io.IOException;  
import java.util.ArrayList;
import java.util.List;


public class AirlineManagerGame {

    private final String path = "src/main/resources/airlineManager/";
    private final String aircraftsFileName ="aircrafts.csv";
    private final String csvSplitBy = ",";



    private Airline airline;
    private List<City> availableCities = new ArrayList<>();
    private List<Aircraft> availableAircrafts = new ArrayList<>();
    private List<Flight> flights = new ArrayList<>();




    private void load() {
        loadAircraft(aircraftsFileName);
        // Load all aircraft and cities from files
        // Initialize the airline
    }

    private void loadAircraft(String fileName) {
        String temporaryLine = "";    

        try {  
            //parsing a CSV file into BufferedReader class constructor  
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.path + fileName));  
            while ((temporaryLine = bufferedReader.readLine()) != null) {
                String[] aircraftInfo = temporaryLine.split(csvSplitBy);    // use comma as separator  
                System.out.println("Aircraft [First Name=" + aircraftInfo[0] + ", Last Name=" + aircraftInfo[1] + ", Designation=" + aircraftInfo[2]);  
            } 
            bufferedReader.close();
        }   
        catch (IOException e) {  
            e.printStackTrace();  
        }  

    }



    public static void main(String[] args) {
        AirlineManagerGame game = new AirlineManagerGame();
        game.load();
    }


}

