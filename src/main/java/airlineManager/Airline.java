package airlineManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Airline implements Iterable<Plane> {

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

    @Override
    public Iterator<Plane> iterator() {
        Iterator<Plane> planeIterator = new PlaneIterator(planes);
        while(planeIterator.hasNext())
            System.out.println(planeIterator.next().getNickName());
        
        // return planeIterator;
        return null;
    }

}
