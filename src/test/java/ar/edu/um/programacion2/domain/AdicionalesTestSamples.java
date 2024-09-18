package ar.edu.um.programacion2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AdicionalesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Adicionales getAdicionalesSample1() {
        return new Adicionales().id(1L).nombre("nombre1").descripcion("descripcion1");
    }

    public static Adicionales getAdicionalesSample2() {
        return new Adicionales().id(2L).nombre("nombre2").descripcion("descripcion2");
    }

    public static Adicionales getAdicionalesRandomSampleGenerator() {
        return new Adicionales()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString());
    }
}
