package airlineManager;

public class Plane {

    private Aircraft aircraft;
    private String nickName;
    private Airline airline;

    public Plane(Aircraft aircraft, String nickName, Airline airline) {
        this.aircraft = aircraft;
        this.nickName = nickName;
        this.airline = airline;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Airline getAirline() {
        return airline;
    }
    
}
