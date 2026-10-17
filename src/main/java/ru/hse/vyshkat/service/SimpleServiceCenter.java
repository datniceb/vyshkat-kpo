package ru.hse.vyshkat.service;

import ru.hse.vyshkat.domain.EnergyConsumer;
import ru.hse.vyshkat.domain.Vehicle;

/*
 * Правила техосмотра:
 * - пробег не больше 5000 км
 * - если транспорт электрический, расход не больше 1.5 кВт·ч в сутки (иначе что-то не так с батареей)
 */
public class SimpleServiceCenter implements ServiceCenter {

    public static final int MAX_MILEAGE = 5000;
    public static final double MAX_DAILY_ENERGY = 1.5;

    @Override
    public boolean check(Vehicle vehicle) {
        if (vehicle.getMileage() > MAX_MILEAGE) {
            return false;
        }
        if (vehicle instanceof EnergyConsumer consumer && consumer.getDailyEnergy() > MAX_DAILY_ENERGY) {
            return false;
        }
        return true;
    }
}
