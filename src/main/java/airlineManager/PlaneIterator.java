package airlineManager;

import java.util.Iterator;
import java.util.List;

public class PlaneIterator implements Iterator<Plane> {



    private int index;
    private int listLength;
    private List<Plane> planes;



    public PlaneIterator(List<Plane> planes) {
        this.planes = planes;
        this.listLength = planes.size();
    }



    @Override
    public boolean hasNext() {
        if(listLength - index > 0) return true;
        return false;
    }


    
    @Override
    public Plane next() {
        if(index == listLength || index > listLength)
            throw new IllegalArgumentException("There are no more planes in the airline.");
        return planes.get(index++);
    }
    
}
