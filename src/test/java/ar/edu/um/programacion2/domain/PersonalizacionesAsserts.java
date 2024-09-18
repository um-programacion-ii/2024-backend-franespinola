package ar.edu.um.programacion2.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonalizacionesAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPersonalizacionesAllPropertiesEquals(Personalizaciones expected, Personalizaciones actual) {
        assertPersonalizacionesAutoGeneratedPropertiesEquals(expected, actual);
        assertPersonalizacionesAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPersonalizacionesAllUpdatablePropertiesEquals(Personalizaciones expected, Personalizaciones actual) {
        assertPersonalizacionesUpdatableFieldsEquals(expected, actual);
        assertPersonalizacionesUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPersonalizacionesAutoGeneratedPropertiesEquals(Personalizaciones expected, Personalizaciones actual) {
        assertThat(expected)
            .as("Verify Personalizaciones auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPersonalizacionesUpdatableFieldsEquals(Personalizaciones expected, Personalizaciones actual) {
        assertThat(expected)
            .as("Verify Personalizaciones relevant properties")
            .satisfies(e -> assertThat(e.getNombre()).as("check nombre").isEqualTo(actual.getNombre()))
            .satisfies(e -> assertThat(e.getDescripcion()).as("check descripcion").isEqualTo(actual.getDescripcion()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPersonalizacionesUpdatableRelationshipsEquals(Personalizaciones expected, Personalizaciones actual) {
        // empty method
    }
}