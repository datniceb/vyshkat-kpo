package ru.hse.vyshkat.domain;

// всё, что стоит на балансе парка и имеет инвентарный номер
public interface InventoryItem {

    int getInventoryNumber();

    String getName();

    String getType();
}
