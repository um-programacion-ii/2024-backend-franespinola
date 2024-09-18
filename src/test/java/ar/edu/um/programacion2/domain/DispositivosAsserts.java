package ar.edu.um.programacion2.domain;

import static ar.edu.um.programacion2.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class DispositivosAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDispositivosAllPropertiesEquals(Dispositivos expected, Dispositivos actual) {
        assertDispositivosAutoGeneratedPropertiesEquals(expected, actual);
        assertDispositivosAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDispositivosAllUpdatablePropertiesEquals(Dispositivos expected, Dispositivos actual) {
        assertDispositivosUpdatableFieldsEquals(expected, actual);
        assertDispositivosUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDispositivosAutoGeneratedPropertiesEquals(Dispositivos expected, Dispositivos actual) {
        assertThat(expected)
            .as("Verify Dispositivos auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDispositivosUpdatableFieldsEquals(Dispositivos expected, Dispositivos actual) {
        assertThat(expected)
            .as("Verify Dispositivos relevant properties")
            .satisfies(e -> assertThat(e.getCodigo()).as("check codigo").isEqualTo(actual.getCodigo()))
            .satisfies(e -> assertThat(e.getNombre()).as("check nombre").isEqualTo(actual.getNombre()))
            .satisfies(e -> assertThat(e.getDescripcion()).as("check descripcion").isEqualTo(actual.getDescripcion()))
            .satisfies(e ->
                assertThat(e.getPrecioBase()).as("check precioBase").usingComparator(bigDecimalCompareTo).isEqualTo(actual.getPrecioBase())
            )
            .satisfies(e -> assertThat(e.getMoneda()).as("check moneda").isEqualTo(actual.getMoneda()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDispositivosUpdatableRelationshipsEquals(Dispositivos expected, Dispositivos actual) {
        // empty method
    }
}
