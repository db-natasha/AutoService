package application;

public class Car {
    private final String brand;
    private final String model;
    private final String plate;
    public Car(String brand, String model, String plate) {
        this.brand = brand;
        this.model = model;
        this.plate = plate;
    }
    public String getBrand() {
        return brand;
    }
    public String getModel() {
        return model;
    }
    public String getPlate() {
        return plate;
    }

    @Override
    public String toString() {
        return brand + " " + model + " [" + plate + "]";
    }
}