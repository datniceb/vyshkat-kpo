package ru.hse.vyshkat.domain;

public class DockStation extends Equipment implements EnergyConsumer {

    private final double dailyEnergy;

    public DockStation(int inventoryNumber, String name, double dailyEnergy) {
        super(inventoryNumber, name);
        this.dailyEnergy = dailyEnergy;
    }

    @Override
    public double getDailyEnergy() {
        return dailyEnergy;
    }

    @Override
    public String getType() {
        return "Док-станция";
    }
}
