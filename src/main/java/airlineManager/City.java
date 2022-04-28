package airlineManager;

public abstract class City {



    private String name;
    private double latitude, longitude; // Coordinates



    protected City(String name, double latitude, double longitude) throws IllegalArgumentException {

        if(!isValidDecimalCoordinates(latitude, longitude))
        throw new IllegalArgumentException("Latitude must be between -+90. It was: " + latitude +"\n" +
                                           "Longitude must be between -+180. It was: " + longitude);

        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private boolean isValidDecimalCoordinates(double latitude, double longitude) {
        if(latitude < -90) return false;
        if(latitude > 90) return false;
        if(longitude < -180) return false;
        if(longitude > 180) return false;
        return true;
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
