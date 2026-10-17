package ru.hse.vyshkat.di;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import org.junit.jupiter.api.Test;
import ru.hse.vyshkat.domain.ElectricScooter;
import ru.hse.vyshkat.service.Park;
import ru.hse.vyshkat.service.ServiceCenter;

class AppModuleTest {

    @Test
    void parkIsSingleton() {
        Injector injector = Guice.createInjector(new AppModule());

        assertSame(injector.getInstance(Park.class), injector.getInstance(Park.class));
    }

    @Test
    void realServiceCenterRejectsHighMileage() {
        Park park = Guice.createInjector(new AppModule()).getInstance(Park.class);

        assertFalse(park.addVehicle(new ElectricScooter(1, "Xiaomi", 7, 99999, 0.7)));
    }

    @Test
    void serviceCenterCanBeReplacedInContainer() {
        // подменяем техосмотр на тот, который пропускает всё
        Injector injector = Guice.createInjector(Modules.override(new AppModule())
                .with(binder -> binder.bind(ServiceCenter.class).toInstance(vehicle -> true)));
        Park park = injector.getInstance(Park.class);

        assertTrue(park.addVehicle(new ElectricScooter(1, "Xiaomi", 7, 99999, 0.7)));
    }
}
