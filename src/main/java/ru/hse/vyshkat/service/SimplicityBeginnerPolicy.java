package ru.hse.vyshkat.service;

import ru.hse.vyshkat.domain.Vehicle;

// базовое правило из ТЗ: простота не меньше 6
public class SimplicityBeginnerPolicy implements BeginnerPolicy {

    public static final int MIN_SIMPLICITY = 6;

    @Override
    public boolean isForBeginner(Vehicle vehicle) {
        return vehicle.getSimplicity() >= MIN_SIMPLICITY;
    }
}
