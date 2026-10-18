package ru.hse.vyshkat.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.inject.Inject;
import ru.hse.vyshkat.domain.EnergyConsumer;
import ru.hse.vyshkat.domain.Equipment;
import ru.hse.vyshkat.domain.InventoryItem;
import ru.hse.vyshkat.domain.Vehicle;

public class Park {

    private final ServiceCenter serviceCenter;
    private final BeginnerPolicy beginnerPolicy;
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final List<Equipment> equipment = new ArrayList<>();

    @Inject
    public Park(ServiceCenter serviceCenter, BeginnerPolicy beginnerPolicy) {
        this.serviceCenter = serviceCenter;
        this.beginnerPolicy = beginnerPolicy;
    }

    // транспорт добавляется только через техосмотр, другого способа нет
    public boolean addVehicle(Vehicle vehicle) {
        if (isNumberTaken(vehicle.getInventoryNumber())) {
            return false;
        }
        if (!serviceCenter.check(vehicle)) {
            return false;
        }
        vehicles.add(vehicle);
        return true;
    }

    // вещи ставим на баланс без техосмотра
    public boolean addEquipment(Equipment item) {
        if (isNumberTaken(item.getInventoryNumber())) {
            return false;
        }
        equipment.add(item);
        return true;
    }

    public boolean isNumberTaken(int number) {
        for (InventoryItem item : getInventory()) {
            if (item.getInventoryNumber() == number) {
                return true;
            }
        }
        return false;
    }

    public List<Vehicle> getVehicles() {
        return Collections.unmodifiableList(vehicles);
    }

    public List<Equipment> getEquipment() {
        return Collections.unmodifiableList(equipment);
    }

    // всё, что на балансе, отсортированное по инвентарному номеру
    public List<InventoryItem> getInventory() {
        List<InventoryItem> all = new ArrayList<>();
        all.addAll(vehicles);
        all.addAll(equipment);
        all.sort(Comparator.comparingInt(InventoryItem::getInventoryNumber));
        return all;
    }

    // суточное потребление всего парка (и транспорт, и док-станции)
    public double getTotalDailyEnergy() {
        double total = 0;
        for (InventoryItem item : getInventory()) {
            if (item instanceof EnergyConsumer consumer) {
                total += consumer.getDailyEnergy();
            }
        }
        return total;
    }

    public List<Vehicle> getBeginnerVehicles() {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (beginnerPolicy.isForBeginner(vehicle)) {
                result.add(vehicle);
            }
        }
        return result;
    }
}
