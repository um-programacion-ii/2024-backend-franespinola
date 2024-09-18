package ar.edu.um.programacion2.domain;

import static ar.edu.um.programacion2.domain.AdicionalesTestSamples.*;
import static ar.edu.um.programacion2.domain.VentaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.programacion2.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AdicionalesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adicionales.class);
        Adicionales adicionales1 = getAdicionalesSample1();
        Adicionales adicionales2 = new Adicionales();
        assertThat(adicionales1).isNotEqualTo(adicionales2);

        adicionales2.setId(adicionales1.getId());
        assertThat(adicionales1).isEqualTo(adicionales2);

        adicionales2 = getAdicionalesSample2();
        assertThat(adicionales1).isNotEqualTo(adicionales2);
    }

    @Test
    void ventaAdicionalesTest() {
        Adicionales adicionales = getAdicionalesRandomSampleGenerator();
        Venta ventaBack = getVentaRandomSampleGenerator();

        adicionales.addVentaAdicionales(ventaBack);
        assertThat(adicionales.getVentaAdicionales()).containsOnly(ventaBack);
        assertThat(ventaBack.getAdicionalesVentas()).containsOnly(adicionales);

        adicionales.removeVentaAdicionales(ventaBack);
        assertThat(adicionales.getVentaAdicionales()).doesNotContain(ventaBack);
        assertThat(ventaBack.getAdicionalesVentas()).doesNotContain(adicionales);

        adicionales.ventaAdicionales(new HashSet<>(Set.of(ventaBack)));
        assertThat(adicionales.getVentaAdicionales()).containsOnly(ventaBack);
        assertThat(ventaBack.getAdicionalesVentas()).containsOnly(adicionales);

        adicionales.setVentaAdicionales(new HashSet<>());
        assertThat(adicionales.getVentaAdicionales()).doesNotContain(ventaBack);
        assertThat(ventaBack.getAdicionalesVentas()).doesNotContain(adicionales);
    }
}
