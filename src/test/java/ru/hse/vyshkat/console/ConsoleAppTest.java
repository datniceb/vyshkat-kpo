package ru.hse.vyshkat.console;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hse.vyshkat.service.Park;
import ru.hse.vyshkat.service.SimpleServiceCenter;
import ru.hse.vyshkat.service.SimplicityBeginnerPolicy;

class ConsoleAppTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final Park park = new Park(new SimpleServiceCenter(), new SimplicityBeginnerPolicy());

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private String run(String input) {
        new ConsoleApp(park, new Scanner(input)).run();
        return output.toString(StandardCharsets.UTF_8);
    }

    @Test
    void demoDataAndReports() {
        String out = run("6\n3\n4\n5\n0\n");

        assertTrue(out.contains("Принят в парк: Электросамокат Ninebot Max G30 (№1001)"));
        assertTrue(out.contains("Не прошёл техосмотр: Электросамокат Xiaomi Pro 2 (№1005)"));
        assertTrue(out.contains("Можно выдавать новичкам:"));
        assertTrue(out.contains("Пока!"));
        assertEquals(4, park.getVehicles().size());
        assertEquals(3, park.getEquipment().size());
    }

    @Test
    void addScooterFromKeyboard() {
        // вид 1, номер 5, модель, простота 7, пробег 10, расход 0,5 (с запятой)
        run("1\n1\n5\nNinebot\n7\n10\n0,5\n0\n");

        assertEquals(1, park.getVehicles().size());
        assertEquals(0.5, park.getTotalDailyEnergy(), 1e-9);
    }

    @Test
    void wrongInputDoesNotCrash() {
        // буквы вместо числа, пункта 9 нет, потом шлем с пустым названием
        String out = run("abc\n9\n2\n1\n7\n\nШлем M\n0\n");

        assertTrue(out.contains("Нужно ввести целое число"));
        assertTrue(out.contains("Число должно быть от 0 до 6"));
        assertTrue(out.contains("Строка не может быть пустой"));
        assertEquals(1, park.getEquipment().size());
    }

    @Test
    void endOfInputDoesNotCrash() {
        // ввод обрывается посреди добавления транспорта, как будто нажали Ctrl+D
        String out = run("1\n2\n");

        assertTrue(out.contains("Ввод закончился, выход"));
        assertTrue(park.getVehicles().isEmpty());
    }

    @Test
    void takenNumberIsAskedAgain() {
        run("6\n2\n1\n1001\n2005\nШлем\n0\n");

        assertTrue(output.toString(StandardCharsets.UTF_8).contains("Этот номер уже занят"));
        assertTrue(park.isNumberTaken(2005));
    }
}
