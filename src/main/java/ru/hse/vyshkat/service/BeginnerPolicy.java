package ru.hse.vyshkat.service;

import ru.hse.vyshkat.domain.Vehicle;

// правило "можно ли выдавать новичку", в ТЗ сказано что его будут усложнять, поэтому отдельно
public interface BeginnerPolicy {

    boolean isForBeginner(Vehicle vehicle);
}
