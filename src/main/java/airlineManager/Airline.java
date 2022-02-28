package airlineManager;

import java.util.ArrayList;
import java.util.List;

public class Airline {

    private String name;
    // private long value;
    private int money;
    private List<Plane> planes;


    public Airline(String name, int money) {
        this.name = name;
        this.money = money;
        this.planes = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public int getMoney() {
        return this.money;
    }

    public void addIncome(int money) {
        this.money += money;
    }

    public List<Plane> getPlanes() {
        return new ArrayList<>(this.planes);
    }

    public void addPlane(Plane plane) {
        this.planes.add(plane);
    }

}
