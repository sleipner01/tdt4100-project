package airlineManager;

public abstract class City {



    private String name;
    private double longitude, latitude; // Coordinates in an imaginary 2D map



    protected City(String name, int longitude, int latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }



    public String getCityName() { return this.name; }

    public double getLongitude() { return this.longitude; }

    public double getLatitude() { return this.latitude; }

    public abstract String getAirportName(); 


    
    @Override
    public String toString() {
        return "City - City name=" + this.name + ", Longitude=" + longitude + ", Latitude=" + latitude;
    }

}
