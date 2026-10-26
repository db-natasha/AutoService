package application;
public class RepairOrder {
    private final int id;
    private final Car car;
    private final Customer customer;
    private final String description;
    private String status;
    private boolean repairDone;
    public RepairOrder(int id, Car car, Customer customer, String description) {
        this.id = id;
        this.car = car;
        this.customer = customer;
        this.description = description;
        status = "NEW";
        repairDone = false;
    }
    public int getId() {
        return id;
    }
    public Car getCar() {
        return car;
    }
    public Customer getCustomer() {
        return customer;
    }
    public String getDescription() {
        return description;
    }
    public String getStatus() {
        return status;
    }
    public boolean isRepairDone() {
        return repairDone;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setRepairDone(boolean repairDone) {
        this.repairDone = repairDone;
    }

    @Override
    public String toString() {
        return "Заказ №" + id + ", машина: " + car + ", клиент: " + customer.getName() +
                ", статус: " + status + ", ремонт выполнен: " + repairDone;
    }
}