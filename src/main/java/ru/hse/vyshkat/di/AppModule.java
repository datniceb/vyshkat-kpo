package ru.hse.vyshkat.di;

import java.util.Scanner;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import ru.hse.vyshkat.service.BeginnerPolicy;
import ru.hse.vyshkat.service.Park;
import ru.hse.vyshkat.service.ServiceCenter;
import ru.hse.vyshkat.service.SimpleServiceCenter;
import ru.hse.vyshkat.service.SimplicityBeginnerPolicy;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        // чтобы поменять правила техосмотра или для новичков, достаточно поменять реализацию здесь
        bind(ServiceCenter.class).to(SimpleServiceCenter.class);
        bind(BeginnerPolicy.class).to(SimplicityBeginnerPolicy.class);
        // парк должен быть один на всё приложение
        bind(Park.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    Scanner provideScanner() {
        return new Scanner(System.in);
    }
}
