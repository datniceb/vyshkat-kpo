package ru.hse.vyshkat.domain;

public class ElectricScooter extends Vehicle implements EnergyConsumer {

    private final double dailyEnergy;

    public ElectricScooter(int inventoryNumber, String name, int simplicity, int mileage, double dailyEnergy) {
        super(inventoryNumber, name, simplicity, mileage);
        this.dailyEnergy = dailyEnergy;
    }

    @Override
    public double getDailyEnergy() {
        return dailyEnergy;
    }

    @Override
    public String getType() {
        return "Электросамокат";
    }
}
