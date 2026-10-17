package ru.hse.vyshkat;

import com.google.inject.Guice;
import com.google.inject.Injector;
import ru.hse.vyshkat.console.ConsoleApp;
import ru.hse.vyshkat.di.AppModule;

public class Main {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        ConsoleApp app = injector.getInstance(ConsoleApp.class);
        app.run();
    }
}
