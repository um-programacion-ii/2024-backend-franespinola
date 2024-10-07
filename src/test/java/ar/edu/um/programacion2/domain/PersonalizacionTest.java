package ar.edu.um.programacion2.domain;

import static ar.edu.um.programacion2.domain.DispositivoTestSamples.*;
import static ar.edu.um.programacion2.domain.PersonalizacionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.programacion2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonalizacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personalizacion.class);
        Personalizacion personalizacion1 = getPersonalizacionSample1();
        Personalizacion personalizacion2 = new Personalizacion();
        assertThat(personalizacion1).isNotEqualTo(personalizacion2);

        personalizacion2.setId(personalizacion1.getId());
        assertThat(personalizacion1).isEqualTo(personalizacion2);

        personalizacion2 = getPersonalizacionSample2();
        assertThat(personalizacion1).isNotEqualTo(personalizacion2);
    }

    @Test
    void dispositivoTest() {
        Personalizacion personalizacion = getPersonalizacionRandomSampleGenerator();
        Dispositivo dispositivoBack = getDispositivoRandomSampleGenerator();

        personalizacion.setDispositivo(dispositivoBack);
        assertThat(personalizacion.getDispositivo()).isEqualTo(dispositivoBack);

        personalizacion.dispositivo(null);
        assertThat(personalizacion.getDispositivo()).isNull();
    }
}
