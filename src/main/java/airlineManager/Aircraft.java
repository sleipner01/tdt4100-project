package airlineManager;

public class Aircraft {
    


    private String manufacturer;
    private String model;
    private String type; // Passanger or Cargo
    private Livery livery;
    private int speed;
    private int range;
    private int efficiency;
    private int price;
    private int seats;
    private int aircraftID;



    public Aircraft(String manufacturer, String model, String type, int speed,
                    int range, int efficiency, int price, int seats, int aircraftID) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.type = type;
        this.livery = new Livery("defaultLivery.png");
        this.speed = speed;
        this.range = range;
        this.efficiency = efficiency;
        this.price = price;
        this.seats = seats;
        this.aircraftID = aircraftID;
    }



    public Aircraft(String manufacturer, String model, String type, int speed,
            int range, int efficiency, int price, int seats, int aircraftID, Livery livery) {
        this(manufacturer, model, type, speed, range, efficiency, price, seats, aircraftID);
        this.livery = livery;
    }



    public String getManufacturer() { return this.manufacturer; }

    public String getModel() { return this.model; }

    public String getType() { return this.type; }
    
    public Livery getLivery() { return this.livery; }
    
    public int getSpeed() { return speed; }
    
    public int getRange() { return this.range; }

    public int getEfficiency() { return efficiency; }

    public int getPrice() { return price; }

    public int getSeats() { return this.seats; }

    public int getAircraftID() { return this.aircraftID; }


    
    @Override
    public String toString() {
        return "Aircraft [manufacturer=" + manufacturer+ ", model=" + model + ", price=" + price
                + ", seats=" + seats + ", speed=" + speed + ", range="
                + range + ", efficiency=" + efficiency + ", livery=" + livery + ", type=" + type + ", aircraftID=" + aircraftID + "]";
    }

}
