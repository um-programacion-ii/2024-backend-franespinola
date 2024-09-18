package ar.edu.um.programacion2.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CaracteristicasAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCaracteristicasAllPropertiesEquals(Caracteristicas expected, Caracteristicas actual) {
        assertCaracteristicasAutoGeneratedPropertiesEquals(expected, actual);
        assertCaracteristicasAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCaracteristicasAllUpdatablePropertiesEquals(Caracteristicas expected, Caracteristicas actual) {
        assertCaracteristicasUpdatableFieldsEquals(expected, actual);
        assertCaracteristicasUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCaracteristicasAutoGeneratedPropertiesEquals(Caracteristicas expected, Caracteristicas actual) {
        assertThat(expected)
            .as("Verify Caracteristicas auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCaracteristicasUpdatableFieldsEquals(Caracteristicas expected, Caracteristicas actual) {
        assertThat(expected)
            .as("Verify Caracteristicas relevant properties")
            .satisfies(e -> assertThat(e.getNombre()).as("check nombre").isEqualTo(actual.getNombre()))
            .satisfies(e -> assertThat(e.getDescripcion()).as("check descripcion").isEqualTo(actual.getDescripcion()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCaracteristicasUpdatableRelationshipsEquals(Caracteristicas expected, Caracteristicas actual) {
        assertThat(expected)
            .as("Verify Caracteristicas relationships")
            .satisfies(e -> assertThat(e.getDispositivo()).as("check dispositivo").isEqualTo(actual.getDispositivo()))
            .satisfies(e ->
                assertThat(e.getDispositivoCaracteristicas())
                    .as("check dispositivoCaracteristicas")
                    .isEqualTo(actual.getDispositivoCaracteristicas())
            );
    }
}