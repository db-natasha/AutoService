package service;

import application.Car;
import application.Customer;
import application.RepairOrder;
import repository.InMemoryRepairRepository;
import repository.RepairRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoService.class);
    private static final String myWrongConstantName = "test";
    private final RepairRepository repository;

    public AutoService(RepairRepository repository) {
        this.repository = repository;
    }

    public void createOrder(RepairOrder order) {
        LOGGER.info("Создание нового заказа на обслуживание: автомобиль {} [{}], клиент: {}, описание: {}",
                order.getCar().getBrand(),
                order.getCar().getPlate(),
                order.getCustomer().getName(),
                order.getDescription());

        repository.save(order);

        LOGGER.info("Заказ №{} успешно создан", order.getId());
    }

    public void markRepairDone(int id) {
        LOGGER.info("Отметка о выполнении ремонта для заказа №{}", id);
        RepairOrder order = repository.findById(id);
        if (order == null) {
            LOGGER.error("Попытка отметить несуществующий заказ №{} как выполненный", id);
            throw new IllegalArgumentException("Заказ не найден");
        }
        order.setRepairDone(true);
        LOGGER.info("Заказ №{} отмечен как выполненный", id);
    }

    public void changeStatus(int id, String newStatus) {
        RepairOrder order = repository.findById(id);
        if (order == null) {
            LOGGER.error("Попытка изменить статус несуществующего заказа №{}", id);
            throw new IllegalArgumentException("Заказ не найден");
        }

        LOGGER.info("Изменение статуса заказа №{} с {} на {}", id, order.getStatus(), newStatus);

        if (order.getStatus().equals("COMPLETED")) {
            LOGGER.warn("Попытка изменить завершенный заказ №{}", id);
            throw new IllegalStateException("Нельзя изменить завершённый заказ");
        }

        if (newStatus.equals("COMPLETED") && !order.isRepairDone()) {
            LOGGER.warn("Попытка завершить заказ №{} без выполнения ремонта", id);
            throw new IllegalStateException("Нельзя завершить заказ: ремонт не выполнен");
        }
        order.setStatus(newStatus);
        LOGGER.info("Статус заказа №{} успешно изменен на {}", id, newStatus);
    }

    public RepairOrder findOrder(int id) {
        RepairOrder order = repository.findById(id);
        if (order == null) {
            LOGGER.warn("Заказ №{} не найден в репозитории", id);
        } else {
            LOGGER.debug("Заказ №{} успешно найден", id);
        }
        return order;
    }

    public static void main(String[] args) {
        RepairRepository repository = new InMemoryRepairRepository();
        AutoService service = new AutoService(repository);
        Car car = new Car("Toyota", "Camry", "А123БВ");
        Customer customer = new Customer(1, "Иван");
        RepairOrder order = new RepairOrder(1, car, customer, "Замена масла");
        service.createOrder(order);
        System.out.println("Создан заказ: " + service.findOrder(1));
        service.changeStatus(1, "IN_PROGRESS");
        System.out.println("Статус изменен: " + service.findOrder(1));
        service.markRepairDone(1);
        service.changeStatus(1, "COMPLETED");
        System.out.println("Заказ успешно завершен: " + service.findOrder(1));
    }
}