package airlineManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AircraftsLoader implements InterfaceGameFileLoader<Aircraft> {

    @Override
    public List<Aircraft> load(String fileName) {
        
        List<Aircraft> aircrafts = new ArrayList<>();

        String temporaryLine = "";    

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("gamefiles/" + fileName)))) {  
            bufferedReader.readLine(); // Skip first line in the CSV document to skip the header.

            System.out.println("\n\n********************\n");
            System.out.println("Starting loading aircrafts...");
            System.out.println("\n");

            while ((temporaryLine = bufferedReader.readLine()) != null) {
                String[] aircraftInfo = temporaryLine.split(",");

                aircrafts.add(
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
                System.out.println(aircrafts.get(aircrafts.size()-1));
            } 

            System.out.println("\n");
            System.out.println("Loading complete!");
            System.out.println("\n");


        }   
        catch (IOException e) {  
            e.printStackTrace();  
        } 

        return aircrafts;
    }
    
}
