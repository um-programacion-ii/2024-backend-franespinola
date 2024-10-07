package ar.edu.um.programacion2.domain;

import static ar.edu.um.programacion2.domain.AdicionalTestSamples.*;
import static ar.edu.um.programacion2.domain.DispositivoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.programacion2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdicionalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adicional.class);
        Adicional adicional1 = getAdicionalSample1();
        Adicional adicional2 = new Adicional();
        assertThat(adicional1).isNotEqualTo(adicional2);

        adicional2.setId(adicional1.getId());
        assertThat(adicional1).isEqualTo(adicional2);

        adicional2 = getAdicionalSample2();
        assertThat(adicional1).isNotEqualTo(adicional2);
    }

    @Test
    void dispositivoTest() {
        Adicional adicional = getAdicionalRandomSampleGenerator();
        Dispositivo dispositivoBack = getDispositivoRandomSampleGenerator();

        adicional.setDispositivo(dispositivoBack);
        assertThat(adicional.getDispositivo()).isEqualTo(dispositivoBack);

        adicional.dispositivo(null);
        assertThat(adicional.getDispositivo()).isNull();
    }
}
