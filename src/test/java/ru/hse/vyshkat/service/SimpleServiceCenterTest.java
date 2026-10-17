package ru.hse.vyshkat.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.hse.vyshkat.domain.Bicycle;
import ru.hse.vyshkat.domain.ElectricBike;
import ru.hse.vyshkat.domain.ElectricScooter;

class SimpleServiceCenterTest {

    private final SimpleServiceCenter serviceCenter = new SimpleServiceCenter();

    @Test
    void goodScooterPasses() {
        assertTrue(serviceCenter.check(new ElectricScooter(1, "Ninebot", 8, 120, 0.6)));
    }

    @Test
    void mileageLimitIs5000() {
        assertTrue(serviceCenter.check(new Bicycle(1, "Stels", 9, 5000)));
        assertFalse(serviceCenter.check(new Bicycle(2, "Stels", 9, 5001)));
    }

    @Test
    void energyLimitIs1_5() {
        assertTrue(serviceCenter.check(new ElectricBike(1, "Eltreco", 6, 0, 1.5)));
        assertFalse(serviceCenter.check(new ElectricBike(2, "Noname", 6, 0, 1.51)));
    }
}
