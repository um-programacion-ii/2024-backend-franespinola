package ar.edu.um.programacion2.domain;

import static ar.edu.um.programacion2.domain.CaracteristicasTestSamples.*;
import static ar.edu.um.programacion2.domain.DispositivosTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.programacion2.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DispositivosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dispositivos.class);
        Dispositivos dispositivos1 = getDispositivosSample1();
        Dispositivos dispositivos2 = new Dispositivos();
        assertThat(dispositivos1).isNotEqualTo(dispositivos2);

        dispositivos2.setId(dispositivos1.getId());
        assertThat(dispositivos1).isEqualTo(dispositivos2);

        dispositivos2 = getDispositivosSample2();
        assertThat(dispositivos1).isNotEqualTo(dispositivos2);
    }

    @Test
    void caracteristicasTest() {
        Dispositivos dispositivos = getDispositivosRandomSampleGenerator();
        Caracteristicas caracteristicasBack = getCaracteristicasRandomSampleGenerator();

        dispositivos.addCaracteristicas(caracteristicasBack);
        assertThat(dispositivos.getCaracteristicas()).containsOnly(caracteristicasBack);
        assertThat(caracteristicasBack.getDispositivoCaracteristicas()).isEqualTo(dispositivos);

        dispositivos.removeCaracteristicas(caracteristicasBack);
        assertThat(dispositivos.getCaracteristicas()).doesNotContain(caracteristicasBack);
        assertThat(caracteristicasBack.getDispositivoCaracteristicas()).isNull();

        dispositivos.caracteristicas(new HashSet<>(Set.of(caracteristicasBack)));
        assertThat(dispositivos.getCaracteristicas()).containsOnly(caracteristicasBack);
        assertThat(caracteristicasBack.getDispositivoCaracteristicas()).isEqualTo(dispositivos);

        dispositivos.setCaracteristicas(new HashSet<>());
        assertThat(dispositivos.getCaracteristicas()).doesNotContain(caracteristicasBack);
        assertThat(caracteristicasBack.getDispositivoCaracteristicas()).isNull();
    }
}
