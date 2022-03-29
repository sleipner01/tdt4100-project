package airlineManager;

public class Flight {

    private Plane plane;
    private Airport origin;
    private Airport destination;
    private int flightTime;

    public Flight(Plane plane, Airport origin) {
        this.plane = plane;
        this.origin = origin;
    }

    public Plane getPlane() {
        return this.plane;
    }

    public Airport getOrigin() {
        return this.origin;
    }

    public Airport getDestination() {
        return this.destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;

        // Calculate flightTime
        int time = 0;
        setFlightTime(time);
    }

    public int getFlightTime() {
        return this.flightTime;
    }

    private void setFlightTime(int time) {
        this.flightTime = time;
    }   

}
