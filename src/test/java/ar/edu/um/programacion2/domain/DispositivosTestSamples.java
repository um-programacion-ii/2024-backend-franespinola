package ar.edu.um.programacion2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DispositivosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Dispositivos getDispositivosSample1() {
        return new Dispositivos().id(1L).codigo("codigo1").nombre("nombre1").descripcion("descripcion1").moneda("moneda1");
    }

    public static Dispositivos getDispositivosSample2() {
        return new Dispositivos().id(2L).codigo("codigo2").nombre("nombre2").descripcion("descripcion2").moneda("moneda2");
    }

    public static Dispositivos getDispositivosRandomSampleGenerator() {
        return new Dispositivos()
            .id(longCount.incrementAndGet())
            .codigo(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString())
            .moneda(UUID.randomUUID().toString());
    }
}
