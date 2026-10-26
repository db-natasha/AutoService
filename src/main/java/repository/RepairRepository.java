package repository;

import java.util.List;

import application.RepairOrder;

public interface RepairRepository {
    void save(RepairOrder order);

    RepairOrder findById(int id);

    List<RepairOrder> findAll();
}