package airlineManager;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public Properties load(String fileName) {
        // Setting up initial values from config file
        Properties properties = new Properties();

        System.out.println("\n\n********************\n");
        System.out.println("Trying to load properties...");

        try (InputStream inputStream = this.getClass().getResourceAsStream("gamefiles/" + fileName)) {

            System.out.println("Loading properties...");
            System.out.println("\n");


            properties.load(inputStream);

            // this.PATH = properties.getProperty("resourcesPath");
            // this.AIRCRAFTS_FILE_NAME = properties.getProperty("aircraftsFileName");
            // this.AIRPORTS_FILE_NAME = properties.getProperty("airportsFileName");

            System.out.println("Loading complete!");
            System.out.println();
            System.out.println("Loaded these properties:");
            System.out.println();
    
            properties.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));
    
            System.out.println("\n\n");

        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        return properties;
    }
    
}
