package airlineManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class GameSaveHandler implements InterfaceGameSaveHandler {

    private final String FOLDER = "gamefiles/";
    private final String FORMAT = ".txt";
    private final String SEPERATOR_VALUE = ",";
    private final String VALID_STAMP = "***VALID";
    private final String AIRLINE_STAMP = "***AIRLINE";
    private final String PLANES_STAMP = "***PLANES";

    @Override
    public void save(String fileName, AirlineManagerGame game) {
        try (PrintWriter writer = new PrintWriter(new File(this.getPathToGameFiles() + fileName + this.FORMAT))) {
            
            Airline airline = game.getAirline();

            writer.println(VALID_STAMP);
            writer.println();
            writer.println();
            writer.println();

            writer.println(AIRLINE_STAMP);
            writer.println("Airline," +
                            airline.getName() + SEPERATOR_VALUE +
                            airline.getCoinAmount() + SEPERATOR_VALUE +
                            airline.getHomeAirport().getAirportID());

            writer.println();
            writer.println();
            writer.println();

            writer.println(PLANES_STAMP);
            List<Plane> planes = airline.getPlanes();
            for (Plane plane : planes) {
                    //Plane info
                    String destinationID = (Objects.isNull(plane.getDestination())) ? "null" : Integer.toString(plane.getDestination().getAirportID());
                    writer.println("Plane," +
                                    plane.getNickName() + SEPERATOR_VALUE +
                                    plane.getAircraft().getAircraftID() + SEPERATOR_VALUE +
                                    plane.getAirport().getAirportID() + SEPERATOR_VALUE +
                                    destinationID + SEPERATOR_VALUE +
                                    plane.isInFlight() + SEPERATOR_VALUE +
                                    plane.getRemainingFlightTimeInMinutes()
                                    );

                    // Passengers
                    List<Passenger> passengers = plane.getPassengers();
                    for (Passenger passenger : passengers) {
                        writer.println("Passenger," + 
                                        passenger.getFullName() + SEPERATOR_VALUE +
                                        passenger.getDestination().getAirportID());
                    }

                writer.println();
            }
            
        } 
        catch (FileNotFoundException e) {
            System.err.println(e);
        }
    }

    @Override
    public Airline load(File file, AirlineManagerGame game) {
        if(!this.isValidGameSave(file)) throw new IllegalArgumentException("This is not a valid game file...");

        List<Aircraft> gameAircrafts = game.getAircrafts();
        List<Airport> gameAirports = game.getAirports();

        
        Airline airline = new Airline();
        List<Plane> planes = new ArrayList<>();


        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println("Firstline: " + line);

                switch (line) {
                    case VALID_STAMP:
                        break;
                    


                    case AIRLINE_STAMP:
                        String[] airlineData = scanner.nextLine().split(SEPERATOR_VALUE);

                        String airlineName = airlineData[1];
                        int airlineCoins = Integer.parseInt(airlineData[2]);
                        Airport homeAirport = gameAirports.stream().filter(airportObject -> airportObject.getAirportID() == Integer.parseInt(airlineData[3])).findFirst().get();
                        airline = new Airline(airlineName, airlineCoins, homeAirport);

                        break;
                


                    case PLANES_STAMP:
                        
                        while(scanner.hasNextLine()) {
                            String planeDataLine = scanner.nextLine();
                            System.out.println("PlaneDataLine: " + planeDataLine);
                            if(planeDataLine.startsWith("Plane")) {
                                String[] planeData = planeDataLine.split(SEPERATOR_VALUE);

                                String planeName = planeData[1];
                                Aircraft aircraft = gameAircrafts.stream().filter(aircraftObject -> aircraftObject.getAircraftID() == Integer.parseInt(planeData[2])).findFirst().get();
                                Airport airport = gameAirports.stream().filter(airportObject -> airportObject.getAirportID() == Integer.parseInt(planeData[3])).findFirst().get();
                                Airport destinaton = (planeData[4].equals("null")) ? null : gameAirports.stream().filter(airportObject -> airportObject.getAirportID() == Integer.parseInt(planeData[4])).findFirst().get();
                                Boolean inFlight = Boolean.parseBoolean(planeData[5]);
                                int minutesLeftInFlight = (inFlight) ? Integer.parseInt(planeData[6]) : 0;

                                List<Passenger> planePassengers = new ArrayList<>();

                                while(scanner.hasNextLine()) {
                                    String passengerDataLine = scanner.nextLine();
                                    System.out.println("PassengerDataLine: " + passengerDataLine);
                                    if(passengerDataLine.startsWith("Passenger")) {
                                        String[] passengerData = passengerDataLine.split(SEPERATOR_VALUE);
                                        String passengerName = passengerData[1];
                                        Airport location = airport;
                                        Airport destination = gameAirports.stream().filter(airportObject -> airportObject.getAirportID() == Integer.parseInt(passengerData[2])).findFirst().get();
                                        planePassengers.add(new Passenger(passengerName, location, destination));
                                    }
                                    else break;
                                }

                                planes.add(new Plane(aircraft, planeName, airline, airport, destinaton, inFlight, minutesLeftInFlight, planePassengers));
                                System.out.println("Planes size: " + planes.size());

                            }
                            else break;

                        }
                        break;

                    default:
                        break;
                }

            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        airline.addExistingPlanes(planes);

        return airline;
    }

    @Override
    public File checkForExistingValidGameSave(String defaultGameSaveName) {
        File gameSave = this.getFile(defaultGameSaveName);
        if(isValidGameSave(gameSave)) return gameSave;
        else return null;
    }

    @Override
    public boolean isValidGameSave(File file) {
        try (Scanner scanner = new Scanner(file)) {
            if(scanner.hasNextLine() && scanner.nextLine().equals(VALID_STAMP)) return true;
            else return false;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            System.err.println("File does not exist");
            return false;
        }

        return true;
        
    }

    private File getFile(String fileName) {
        File file;
        try {
            file = new File(this.getPathToGameFiles() + fileName + this.FORMAT);
            if(file.isFile()) return file;
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    private String getPathToGameFiles() {
        return this.getClass().getResource(this.FOLDER).getFile();
    }
    
}
