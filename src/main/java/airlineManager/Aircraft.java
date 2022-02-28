package airlineManager;

public class Aircraft {
    
    private String manufacturer;
    private String model;
    private String type; // Passanger or Cargo
    private String tailNumber;
    private Livery livery;
    private int speed;
    private int efficiency;
    private int price;
    private int seats;

    public Aircraft(String manufacturer, String model, String type, String tailNumber, Livery livery, int speed,
            int efficiency, int price, int seats) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.type = type;
        this.tailNumber = tailNumber;
        this.livery = livery;
        this.speed = speed;
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

    public String getTailNumber() {
        return this.tailNumber;
    }

    public Livery getLivery() {
        return this.livery;
    }

    public int getSpeed() {
        return speed;
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

}
