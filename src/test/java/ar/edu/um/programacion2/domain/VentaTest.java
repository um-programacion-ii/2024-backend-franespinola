package ar.edu.um.programacion2.domain;

import static ar.edu.um.programacion2.domain.AdicionalesTestSamples.*;
import static ar.edu.um.programacion2.domain.DispositivosTestSamples.*;
import static ar.edu.um.programacion2.domain.OpcionesTestSamples.*;
import static ar.edu.um.programacion2.domain.VentaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.programacion2.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class VentaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Venta.class);
        Venta venta1 = getVentaSample1();
        Venta venta2 = new Venta();
        assertThat(venta1).isNotEqualTo(venta2);

        venta2.setId(venta1.getId());
        assertThat(venta1).isEqualTo(venta2);

        venta2 = getVentaSample2();
        assertThat(venta1).isNotEqualTo(venta2);
    }

    @Test
    void dispositivoVentaTest() {
        Venta venta = getVentaRandomSampleGenerator();
        Dispositivos dispositivosBack = getDispositivosRandomSampleGenerator();

        venta.setDispositivoVenta(dispositivosBack);
        assertThat(venta.getDispositivoVenta()).isEqualTo(dispositivosBack);

        venta.dispositivoVenta(null);
        assertThat(venta.getDispositivoVenta()).isNull();
    }

    @Test
    void personalizacionesVentaTest() {
        Venta venta = getVentaRandomSampleGenerator();
        Opciones opcionesBack = getOpcionesRandomSampleGenerator();

        venta.addPersonalizacionesVenta(opcionesBack);
        assertThat(venta.getPersonalizacionesVentas()).containsOnly(opcionesBack);

        venta.removePersonalizacionesVenta(opcionesBack);
        assertThat(venta.getPersonalizacionesVentas()).doesNotContain(opcionesBack);

        venta.personalizacionesVentas(new HashSet<>(Set.of(opcionesBack)));
        assertThat(venta.getPersonalizacionesVentas()).containsOnly(opcionesBack);

        venta.setPersonalizacionesVentas(new HashSet<>());
        assertThat(venta.getPersonalizacionesVentas()).doesNotContain(opcionesBack);
    }

    @Test
    void adicionalesVentaTest() {
        Venta venta = getVentaRandomSampleGenerator();
        Adicionales adicionalesBack = getAdicionalesRandomSampleGenerator();

        venta.addAdicionalesVenta(adicionalesBack);
        assertThat(venta.getAdicionalesVentas()).containsOnly(adicionalesBack);

        venta.removeAdicionalesVenta(adicionalesBack);
        assertThat(venta.getAdicionalesVentas()).doesNotContain(adicionalesBack);

        venta.adicionalesVentas(new HashSet<>(Set.of(adicionalesBack)));
        assertThat(venta.getAdicionalesVentas()).containsOnly(adicionalesBack);

        venta.setAdicionalesVentas(new HashSet<>());
        assertThat(venta.getAdicionalesVentas()).doesNotContain(adicionalesBack);
    }
}
