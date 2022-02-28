package airlineManager;

public class Flight {

    private Plane plane;
    private Airport origin;
    private Airport destination;


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
    }

}
