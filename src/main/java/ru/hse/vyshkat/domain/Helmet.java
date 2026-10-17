package ru.hse.vyshkat.domain;

public class Helmet extends Equipment {

    public Helmet(int inventoryNumber, String name) {
        super(inventoryNumber, name);
    }

    @Override
    public String getType() {
        return "Шлем";
    }
}
