package ru.hse.vyshkat.domain;

// вещи на балансе, которые не транспорт
public abstract class Equipment implements InventoryItem {

    private final int inventoryNumber;
    private final String name;

    public Equipment(int inventoryNumber, String name) {
        this.inventoryNumber = inventoryNumber;
        this.name = name;
    }

    @Override
    public int getInventoryNumber() {
        return inventoryNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getType() + " " + name + " (№" + inventoryNumber + ")";
    }
}
