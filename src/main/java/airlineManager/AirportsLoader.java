package airlineManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AirportsLoader implements InterfaceGameFileLoader<Airport> {
    
    @Override
    public List<Airport> load(String fileName) {

        List<Airport> airports = new ArrayList<>();

        String temporaryLine = "";    

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("gamefiles/" + fileName)))) {  

            bufferedReader.readLine(); // Skip first line in the CSV document to skip the header.

            System.out.println("\n\n********************\n");
            System.out.println("Starting loading airports...");
            System.out.println("\n");

            while ((temporaryLine = bufferedReader.readLine()) != null) {
                String[] airportInfo = temporaryLine.split(",");

                airports.add(
                    new Airport(airportInfo[3], 
                                Integer.parseInt(airportInfo[4]), Integer.parseInt(airportInfo[5]),
                                airportInfo[0], Integer.parseInt(airportInfo[1]), Integer.parseInt(airportInfo[2])
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

    // private File getFile(String filename) {
    //     return new File(AircraftsLoader.class.getResource("resources/").getFile() + filename);
    // }
}
