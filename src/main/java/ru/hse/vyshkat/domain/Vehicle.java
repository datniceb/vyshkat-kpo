package ru.hse.vyshkat.domain;

public abstract class Vehicle implements InventoryItem {

    private final int inventoryNumber;
    private final String name;
    private final int simplicity; // насколько легко новичку, от 1 до 10
    private final int mileage;    // пробег в км

    public Vehicle(int inventoryNumber, String name, int simplicity, int mileage) {
        if (simplicity < 1 || simplicity > 10) {
            throw new IllegalArgumentException("Простота должна быть от 1 до 10");
        }
        if (mileage < 0) {
            throw new IllegalArgumentException("Пробег не может быть отрицательным");
        }
        this.inventoryNumber = inventoryNumber;
        this.name = name;
        this.simplicity = simplicity;
        this.mileage = mileage;
    }

    @Override
    public int getInventoryNumber() {
        return inventoryNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getSimplicity() {
        return simplicity;
    }

    public int getMileage() {
        return mileage;
    }

    @Override
    public String toString() {
        return getType() + " " + name + " (№" + inventoryNumber + ")";
    }
}
