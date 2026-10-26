package service;
import repository.InMemoryRepairRepository;
import repository.RepairRepository;
import application.Car;
import application.Customer;
import application.RepairOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AutoServiceTests {

    private AutoService service;
    private RepairRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new InMemoryRepairRepository();
        service = new AutoService(repository);
    }

    @Test // 1
    public void testCreateAndCompleteOrder() {
        Car car = new Car("Toyota", "Camry", "А123БВ");
        Customer customer = new Customer(1, "Иван");
        RepairOrder order = new RepairOrder(1, car, customer, "Замена масла");
        service.createOrder(order);
        service.changeStatus(1, "IN_PROGRESS");
        service.markRepairDone(1);
        service.changeStatus(1, "COMPLETED");
        assertEquals("COMPLETED", service.findOrder(1).getStatus());
        assertTrue(service.findOrder(1).isRepairDone());
    }

    @Test // 2
    public void testCannotChangeCompletedOrder() {
        Car car = new Car("BMW", "X5", "Б222ВВ");
        Customer customer = new Customer(2, "Пётр");
        RepairOrder order = new RepairOrder(2, car, customer, "Ремонт двигателя");
        service.createOrder(order);
        service.changeStatus(2, "IN_PROGRESS");
        service.markRepairDone(2);
        service.changeStatus(2, "COMPLETED");
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            service.changeStatus(2, "IN_PROGRESS");
        });
        assertEquals("Нельзя изменить завершённый заказ", exception.getMessage());
    }

    @Test // 3
    public void testCannotCompleteWithoutRepairDone() {
        Car car = new Car("Audi", "A6", "В333СС");
        Customer customer = new Customer(3, "Анна");
        RepairOrder order = new RepairOrder(3, car, customer, "Замена тормозов");
        service.createOrder(order);
        service.changeStatus(3, "IN_PROGRESS");
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            service.changeStatus(3, "COMPLETED");
        });
        assertEquals("Нельзя завершить заказ: ремонт не выполнен", exception.getMessage());
    }
}