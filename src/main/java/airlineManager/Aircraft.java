package airlineManager;

public class Aircraft implements AircraftInterface {
    
    private String manufacturer;
    private String model;
    private String type; // Passanger or Cargo
    private Livery livery;
    private int speed;
    private int range;
    private int efficiency;
    private int price;
    private int seats;

    public Aircraft(String manufacturer, String model, String type, Livery livery, int speed,
            int range, int efficiency, int price, int seats) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.type = type;
        this.livery = livery;
        this.speed = speed;
        this.range = range;
        this.efficiency = efficiency;
        this.price = price;
        this.seats = seats;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public String getModel() {
        return this.model;
    }

    public String getType() {
        return this.type;
    }
    
    public Livery getLivery() {
        return this.livery;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public int getRange() {
        return this.range;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public int getPrice() {
        return price;
    }

    public int getSeats() {
        return this.seats;
    }


    @Override
    public String toString() {
        return "Aircraft [manufacturer=" + manufacturer+ ", model=" + model + ", price=" + price
                + ", seats=" + seats + ", speed=" + speed + ", range="
                + range + "efficiency=" + efficiency + ", livery=" + livery + ", type=" + type + "]";
    }

}
