package airlineManager;

import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

public class PropertiesLoader {

    private final String REGEX_PATTERN = "[a-zA-Z]*";
    private final String FILE_FORMAT = ".properties";
    private final String GAMEFILES_FOLDER = "gamefiles/";

    public Properties load(String fileName) throws IllegalArgumentException {

        if(!isValidFileName(fileName))
            throw new IllegalArgumentException("The filename can only include the name in ACHII characters, no path and no format.");

        // Setting up initial values from config file
        Properties properties = new Properties();

        System.out.println("\n\n********************\n");
        System.out.println("Trying to load properties...");

        try (InputStream inputStream = this.getClass().getResourceAsStream(GAMEFILES_FOLDER + fileName + FILE_FORMAT)) {

            System.out.println("Loading properties...");
            System.out.println("\n");

            properties.load(inputStream);

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

    private boolean isValidFileName(String fileName) {
        if(Pattern.matches(REGEX_PATTERN, fileName)) return true;
        return false;
    }
    
}
