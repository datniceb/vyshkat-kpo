package ru.hse.vyshkat.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class VehicleTest {

    @Test
    void scooterKeepsData() {
        ElectricScooter scooter = new ElectricScooter(1, "Ninebot", 8, 100, 0.6);

        assertEquals(1, scooter.getInventoryNumber());
        assertEquals("Ninebot", scooter.getName());
        assertEquals(8, scooter.getSimplicity());
        assertEquals(100, scooter.getMileage());
        assertEquals(0.6, scooter.getDailyEnergy());
        assertEquals("Электросамокат Ninebot (№1)", scooter.toString());
    }

    @Test
    void simplicityMustBeFrom1To10() {
        assertThrows(IllegalArgumentException.class, () -> new Bicycle(1, "Stels", 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Bicycle(1, "Stels", 11, 0));
    }

    @Test
    void mileageCannotBeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Bicycle(1, "Stels", 5, -1));
    }

    @Test
    void onlyElectricThingsConsumeEnergy() {
        assertFalse(new Bicycle(1, "Stels", 5, 0) instanceof EnergyConsumer);
        assertFalse(new Helmet(2, "M") instanceof EnergyConsumer);
        assertTrue(new ElectricBike(3, "Eltreco", 6, 0, 0.8) instanceof EnergyConsumer);
        assertTrue(new DockStation(4, "Покровка", 2.4) instanceof EnergyConsumer);
    }
}
