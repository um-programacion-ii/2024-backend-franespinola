package ar.edu.um.programacion2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CaracteristicasTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Caracteristicas getCaracteristicasSample1() {
        return new Caracteristicas().id(1L).nombre("nombre1").descripcion("descripcion1");
    }

    public static Caracteristicas getCaracteristicasSample2() {
        return new Caracteristicas().id(2L).nombre("nombre2").descripcion("descripcion2");
    }

    public static Caracteristicas getCaracteristicasRandomSampleGenerator() {
        return new Caracteristicas()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString());
    }
}
