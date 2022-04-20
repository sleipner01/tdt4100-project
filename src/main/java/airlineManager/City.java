package airlineManager;

public abstract class City {



    private String name;
    private double latitude, longitude; // Coordinates



    protected City(String name, double latitude, double longitude)  {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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
