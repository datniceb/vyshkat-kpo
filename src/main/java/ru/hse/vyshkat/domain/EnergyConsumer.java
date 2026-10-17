package ru.hse.vyshkat.domain;

// то, что тратит электроэнергию (велосипед и шлем это не реализуют)
public interface EnergyConsumer {

    double getDailyEnergy(); // кВт·ч в сутки
}
