package ru.hse.vyshkat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import ru.hse.vyshkat.domain.Bicycle;
import ru.hse.vyshkat.domain.DockStation;
import ru.hse.vyshkat.domain.ElectricScooter;
import ru.hse.vyshkat.domain.Helmet;
import ru.hse.vyshkat.domain.InventoryItem;
import ru.hse.vyshkat.domain.Vehicle;

class ParkTest {

    // вместо настоящего техосмотра подставляем лямбду, ServiceCenter — интерфейс с одним методом
    private final Park park = new Park(vehicle -> true);

    @Test
    void vehicleIsAddedIfInspectionPassed() {
        assertTrue(park.addVehicle(new Bicycle(1, "Stels", 9, 0)));
        assertEquals(1, park.getVehicles().size());
    }

    @Test
    void vehicleIsNotAddedIfInspectionFailed() {
        Park strictPark = new Park(vehicle -> false);

        assertFalse(strictPark.addVehicle(new Bicycle(1, "Stels", 9, 0)));
        assertTrue(strictPark.getVehicles().isEmpty());
        assertFalse(strictPark.isNumberTaken(1));
    }

    @Test
    void inventoryNumberCannotRepeat() {
        park.addVehicle(new Bicycle(1, "Stels", 9, 0));

        assertFalse(park.addVehicle(new Bicycle(1, "Другой", 9, 0)));
        assertFalse(park.addEquipment(new Helmet(1, "M")));
        assertTrue(park.addEquipment(new Helmet(2, "M")));
    }

    @Test
    void totalEnergyCountsVehiclesAndEquipment() {
        park.addVehicle(new ElectricScooter(1, "Ninebot", 8, 0, 0.5));
        park.addVehicle(new Bicycle(2, "Stels", 9, 0));
        park.addEquipment(new DockStation(3, "Покровка", 2.0));
        park.addEquipment(new Helmet(4, "M"));

        assertEquals(2.5, park.getTotalDailyEnergy(), 1e-9);
    }

    @Test
    void beginnerVehiclesHaveSimplicityAtLeast6() {
        park.addVehicle(new Bicycle(1, "Легкий", 6, 0));
        park.addVehicle(new Bicycle(2, "Сложный", 5, 0));

        List<Vehicle> result = park.getBeginnerVehicles();

        assertEquals(1, result.size());
        assertEquals("Легкий", result.get(0).getName());
    }

    @Test
    void inventoryIsSortedByNumber() {
        park.addEquipment(new Helmet(30, "L"));
        park.addVehicle(new Bicycle(10, "Stels", 9, 0));
        park.addEquipment(new Helmet(20, "M"));

        List<InventoryItem> items = park.getInventory();

        assertEquals(10, items.get(0).getInventoryNumber());
        assertEquals(20, items.get(1).getInventoryNumber());
        assertEquals(30, items.get(2).getInventoryNumber());
    }
}
