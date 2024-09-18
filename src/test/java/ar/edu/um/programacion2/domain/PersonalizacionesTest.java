package ar.edu.um.programacion2.domain;

import static ar.edu.um.programacion2.domain.OpcionesTestSamples.*;
import static ar.edu.um.programacion2.domain.PersonalizacionesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.programacion2.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PersonalizacionesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personalizaciones.class);
        Personalizaciones personalizaciones1 = getPersonalizacionesSample1();
        Personalizaciones personalizaciones2 = new Personalizaciones();
        assertThat(personalizaciones1).isNotEqualTo(personalizaciones2);

        personalizaciones2.setId(personalizaciones1.getId());
        assertThat(personalizaciones1).isEqualTo(personalizaciones2);

        personalizaciones2 = getPersonalizacionesSample2();
        assertThat(personalizaciones1).isNotEqualTo(personalizaciones2);
    }

    @Test
    void opcionesTest() {
        Personalizaciones personalizaciones = getPersonalizacionesRandomSampleGenerator();
        Opciones opcionesBack = getOpcionesRandomSampleGenerator();

        personalizaciones.addOpciones(opcionesBack);
        assertThat(personalizaciones.getOpciones()).containsOnly(opcionesBack);
        assertThat(opcionesBack.getPersonalizacionOpciones()).isEqualTo(personalizaciones);

        personalizaciones.removeOpciones(opcionesBack);
        assertThat(personalizaciones.getOpciones()).doesNotContain(opcionesBack);
        assertThat(opcionesBack.getPersonalizacionOpciones()).isNull();

        personalizaciones.opciones(new HashSet<>(Set.of(opcionesBack)));
        assertThat(personalizaciones.getOpciones()).containsOnly(opcionesBack);
        assertThat(opcionesBack.getPersonalizacionOpciones()).isEqualTo(personalizaciones);

        personalizaciones.setOpciones(new HashSet<>());
        assertThat(personalizaciones.getOpciones()).doesNotContain(opcionesBack);
        assertThat(opcionesBack.getPersonalizacionOpciones()).isNull();
    }
}
