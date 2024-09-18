package ar.edu.um.programacion2.domain;

import static ar.edu.um.programacion2.domain.CaracteristicasTestSamples.*;
import static ar.edu.um.programacion2.domain.DispositivosTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.programacion2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CaracteristicasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Caracteristicas.class);
        Caracteristicas caracteristicas1 = getCaracteristicasSample1();
        Caracteristicas caracteristicas2 = new Caracteristicas();
        assertThat(caracteristicas1).isNotEqualTo(caracteristicas2);

        caracteristicas2.setId(caracteristicas1.getId());
        assertThat(caracteristicas1).isEqualTo(caracteristicas2);

        caracteristicas2 = getCaracteristicasSample2();
        assertThat(caracteristicas1).isNotEqualTo(caracteristicas2);
    }

    @Test
    void dispositivoTest() {
        Caracteristicas caracteristicas = getCaracteristicasRandomSampleGenerator();
        Dispositivos dispositivosBack = getDispositivosRandomSampleGenerator();

        caracteristicas.setDispositivo(dispositivosBack);
        assertThat(caracteristicas.getDispositivo()).isEqualTo(dispositivosBack);

        caracteristicas.dispositivo(null);
        assertThat(caracteristicas.getDispositivo()).isNull();
    }

    @Test
    void dispositivoCaracteristicasTest() {
        Caracteristicas caracteristicas = getCaracteristicasRandomSampleGenerator();
        Dispositivos dispositivosBack = getDispositivosRandomSampleGenerator();

        caracteristicas.setDispositivoCaracteristicas(dispositivosBack);
        assertThat(caracteristicas.getDispositivoCaracteristicas()).isEqualTo(dispositivosBack);

        caracteristicas.dispositivoCaracteristicas(null);
        assertThat(caracteristicas.getDispositivoCaracteristicas()).isNull();
    }
}
