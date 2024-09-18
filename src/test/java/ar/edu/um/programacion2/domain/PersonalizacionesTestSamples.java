package ar.edu.um.programacion2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PersonalizacionesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Personalizaciones getPersonalizacionesSample1() {
        return new Personalizaciones().id(1L).nombre("nombre1").descripcion("descripcion1");
    }

    public static Personalizaciones getPersonalizacionesSample2() {
        return new Personalizaciones().id(2L).nombre("nombre2").descripcion("descripcion2");
    }

    public static Personalizaciones getPersonalizacionesRandomSampleGenerator() {
        return new Personalizaciones()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString());
    }
}
