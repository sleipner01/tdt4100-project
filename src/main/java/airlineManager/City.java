package airlineManager;

public class City {

    private String name;
    private int longitude, latitude; // Coordinates in an imaginary 2D map

    public City(String name, int longitude, int latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCityName() {
        return this.name;
    }

    public int getLongitude() {
        return this.longitude;
    }

    public int getLatitude() {
        return this.latitude;
    }

    @Override
    public String toString() {
        return "City - City name=" + this.name + ", Longitude=" + longitude + ", Latitude=" + latitude;
    }

}
