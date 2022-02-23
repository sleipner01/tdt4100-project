package airlineManager;

public class Aircraft {
    
    private String manufacturer;
    private String model;
    private String type; // Passanger or Cargo
    private String tailNumber;
    private int speed;
    private int efficiency;
    private int price;


    public String getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getEfficiency() {
        return efficiency;
    }
    
    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public int getPrice() {
        return price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    

}
