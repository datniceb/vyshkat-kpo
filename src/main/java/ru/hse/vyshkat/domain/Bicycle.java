package ru.hse.vyshkat.domain;

public class Bicycle extends Vehicle {

    public Bicycle(int inventoryNumber, String name, int simplicity, int mileage) {
        super(inventoryNumber, name, simplicity, mileage);
    }

    @Override
    public String getType() {
        return "Велосипед";
    }
}
