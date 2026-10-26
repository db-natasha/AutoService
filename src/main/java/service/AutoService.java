package service;

import application.Car;
import application.Customer;
import application.RepairOrder;
import repository.InMemoryRepairRepository;
import repository.RepairRepository;

public class AutoService {
    private final RepairRepository repository;

    public AutoService(RepairRepository repository) {
        this.repository = repository;
    }

    public void createOrder(RepairOrder order) {
        repository.save(order);
    }

    public void markRepairDone(int id) {
        RepairOrder order = repository.findById(id);
        if (order == null) {
            throw new IllegalArgumentException("Заказ не найден");
        }
        order.setRepairDone(true);
    }

    public void changeStatus(int id, String newStatus) {
        RepairOrder order = repository.findById(id);
        if (order == null) {
            throw new IllegalArgumentException("Заказ не найден");
        }
        if (order.getStatus().equals("COMPLETED")) {
            throw new IllegalStateException("Нельзя изменить завершённый заказ");
        }
        if (newStatus.equals("COMPLETED") && !order.isRepairDone()) {
            throw new IllegalStateException("Нельзя завершить заказ: ремонт не выполнен");
        }
        order.setStatus(newStatus);
    }

    public RepairOrder findOrder(int id) {
        return repository.findById(id);
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