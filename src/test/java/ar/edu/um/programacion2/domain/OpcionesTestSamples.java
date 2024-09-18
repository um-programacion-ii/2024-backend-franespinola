package ar.edu.um.programacion2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OpcionesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Opciones getOpcionesSample1() {
        return new Opciones().id(1L).codigo("codigo1").nombre("nombre1").descripcion("descripcion1");
    }

    public static Opciones getOpcionesSample2() {
        return new Opciones().id(2L).codigo("codigo2").nombre("nombre2").descripcion("descripcion2");
    }

    public static Opciones getOpcionesRandomSampleGenerator() {
        return new Opciones()
            .id(longCount.incrementAndGet())
            .codigo(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString());
    }
}
