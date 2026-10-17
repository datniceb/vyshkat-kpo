package ru.hse.vyshkat.console;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.google.inject.Inject;
import ru.hse.vyshkat.domain.Bicycle;
import ru.hse.vyshkat.domain.DockStation;
import ru.hse.vyshkat.domain.ElectricBike;
import ru.hse.vyshkat.domain.ElectricScooter;
import ru.hse.vyshkat.domain.Equipment;
import ru.hse.vyshkat.domain.Helmet;
import ru.hse.vyshkat.domain.InventoryItem;
import ru.hse.vyshkat.domain.Vehicle;
import ru.hse.vyshkat.service.Park;

public class ConsoleApp {

    private final Park park;
    private final Scanner scanner;

    @Inject
    public ConsoleApp(Park park, Scanner scanner) {
        this.park = park;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("ВышКат — учёт парка");
        try {
            while (true) {
                printMenu();
                int choice = readInt("Выберите пункт: ", 0, 6);
                switch (choice) {
                    case 1 -> addVehicle();
                    case 2 -> addEquipment();
                    case 3 -> showSummary();
                    case 4 -> showBeginnerVehicles();
                    case 5 -> showInventory();
                    case 6 -> loadDemoData();
                    case 0 -> {
                        System.out.println("Пока!");
                        return;
                    }
                }
            }
        } catch (NoSuchElementException e) {
            // ввод закончился (Ctrl+D), scanner.nextLine() больше нечего читать
            System.out.println();
            System.out.println("Ввод закончился, выход");
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Добавить транспорт (через техосмотр)");
        System.out.println("2. Поставить вещь на баланс");
        System.out.println("3. Количество и энергопотребление");
        System.out.println("4. Транспорт для новичков");
        System.out.println("5. Инвентарная ведомость");
        System.out.println("6. Загрузить демо-данные");
        System.out.println("0. Выход");
    }

    private void addVehicle() {
        System.out.println("1 - электросамокат, 2 - велосипед, 3 - электровелосипед");
        int type = readInt("Вид: ", 1, 3);
        int number = readNumber();
        String name = readString("Модель: ");
        int simplicity = readInt("Простота для новичка (1-10): ", 1, 10);
        int mileage = readInt("Пробег, км: ", 0, Integer.MAX_VALUE);

        Vehicle vehicle;
        if (type == 1) {
            vehicle = new ElectricScooter(number, name, simplicity, mileage, readDouble("Расход в сутки, кВт·ч: "));
        } else if (type == 2) {
            vehicle = new Bicycle(number, name, simplicity, mileage);
        } else {
            vehicle = new ElectricBike(number, name, simplicity, mileage, readDouble("Расход в сутки, кВт·ч: "));
        }
        tryAddVehicle(vehicle);
    }

    private void addEquipment() {
        System.out.println("1 - шлем, 2 - док-станция");
        int type = readInt("Вид: ", 1, 2);
        int number = readNumber();
        String name = readString("Название: ");

        Equipment item;
        if (type == 1) {
            item = new Helmet(number, name);
        } else {
            item = new DockStation(number, name, readDouble("Расход в сутки, кВт·ч: "));
        }
        tryAddEquipment(item);
    }

    private void showSummary() {
        System.out.println("Транспорта в парке: " + park.getVehicles().size());
        System.out.println("Вещей на балансе: " + park.getEquipment().size());
        System.out.printf("Суточное потребление энергии: %.2f кВт·ч%n", park.getTotalDailyEnergy());
    }

    private void showBeginnerVehicles() {
        List<Vehicle> list = park.getBeginnerVehicles();
        if (list.isEmpty()) {
            System.out.println("Подходящего транспорта нет");
            return;
        }
        System.out.println("Можно выдавать новичкам:");
        for (Vehicle vehicle : list) {
            System.out.println("  " + vehicle + ", простота " + vehicle.getSimplicity());
        }
    }

    private void showInventory() {
        List<InventoryItem> items = park.getInventory();
        if (items.isEmpty()) {
            System.out.println("На балансе ничего нет");
            return;
        }
        for (InventoryItem item : items) {
            System.out.println("  №" + item.getInventoryNumber() + "  " + item.getType() + "  " + item.getName());
        }
    }

    private void loadDemoData() {
        tryAddVehicle(new ElectricScooter(1001, "Ninebot Max G30", 8, 120, 0.6));
        tryAddVehicle(new ElectricScooter(1002, "Kugoo Kirin M4", 5, 40, 0.9));
        tryAddVehicle(new Bicycle(1003, "Stels Navigator 300", 9, 300));
        tryAddVehicle(new ElectricBike(1004, "Eltreco XT 600", 6, 800, 0.8));
        tryAddVehicle(new ElectricScooter(1005, "Xiaomi Pro 2", 7, 7400, 0.7)); // не пройдёт, большой пробег
        tryAddVehicle(new ElectricBike(1006, "Noname", 4, 0, 2.3));             // не пройдёт, большой расход

        tryAddEquipment(new Helmet(2001, "ВышКат, размер M"));
        tryAddEquipment(new Helmet(2002, "ВышКат, размер L"));
        tryAddEquipment(new DockStation(3001, "Покровский б-р, 11", 2.4));
    }

    private void tryAddVehicle(Vehicle vehicle) {
        if (park.isNumberTaken(vehicle.getInventoryNumber())) {
            System.out.println("Номер " + vehicle.getInventoryNumber() + " уже занят");
        } else if (park.addVehicle(vehicle)) {
            System.out.println("Принят в парк: " + vehicle);
        } else {
            System.out.println("Не прошёл техосмотр: " + vehicle);
        }
    }

    private void tryAddEquipment(Equipment item) {
        if (park.addEquipment(item)) {
            System.out.println("Поставлено на баланс: " + item);
        } else {
            System.out.println("Номер " + item.getInventoryNumber() + " уже занят");
        }
    }

    // инвентарный номер, который ещё никем не занят
    private int readNumber() {
        while (true) {
            int number = readInt("Инвентарный номер: ", 1, Integer.MAX_VALUE);
            if (!park.isNumberTaken(number)) {
                return number;
            }
            System.out.println("Этот номер уже занят");
        }
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
                if (max == Integer.MAX_VALUE) {
                    System.out.println("Число должно быть не меньше " + min);
                } else {
                    System.out.println("Число должно быть от " + min + " до " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Нужно ввести целое число");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim().replace(',', '.');
            try {
                double value = Double.parseDouble(line);
                if (value > 0) {
                    return value;
                }
                System.out.println("Число должно быть больше нуля");
            } catch (NumberFormatException e) {
                System.out.println("Нужно ввести число, например 0.5");
            }
        }
    }

    private String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("Строка не может быть пустой");
        }
    }
}
