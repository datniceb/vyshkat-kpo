package ru.hse.vyshkat.di;

import java.util.Scanner;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import ru.hse.vyshkat.service.Park;
import ru.hse.vyshkat.service.ServiceCenter;
import ru.hse.vyshkat.service.SimpleServiceCenter;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        // чтобы поменять правила техосмотра, достаточно поменять реализацию здесь
        bind(ServiceCenter.class).to(SimpleServiceCenter.class);
        // парк должен быть один на всё приложение
        bind(Park.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    Scanner provideScanner() {
        return new Scanner(System.in);
    }
}
