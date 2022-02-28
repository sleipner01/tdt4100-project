package airlineManager;

import java.util.ArrayList;
import java.util.List;

public class Airline {
    private String name;
    // private long value;
    private int money;
    private List<Plane> planes;


    public Airline() {

        this.planes = new ArrayList<>();
    }



    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<Plane> getPlanes() {
        return new ArrayList<>(this.planes);
    }

    public void addPlane(Plane plane) {
        this.planes.add(plane);
    }

    
}
