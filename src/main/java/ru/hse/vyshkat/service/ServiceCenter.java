package ru.hse.vyshkat.service;

import ru.hse.vyshkat.domain.Vehicle;

// техосмотр: можно ли пускать транспорт в парк
public interface ServiceCenter {

    boolean check(Vehicle vehicle);
}
