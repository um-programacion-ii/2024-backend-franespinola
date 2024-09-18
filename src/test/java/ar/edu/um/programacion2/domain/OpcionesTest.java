package ar.edu.um.programacion2.domain;

import static ar.edu.um.programacion2.domain.OpcionesTestSamples.*;
import static ar.edu.um.programacion2.domain.PersonalizacionesTestSamples.*;
import static ar.edu.um.programacion2.domain.VentaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.programacion2.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OpcionesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Opciones.class);
        Opciones opciones1 = getOpcionesSample1();
        Opciones opciones2 = new Opciones();
        assertThat(opciones1).isNotEqualTo(opciones2);

        opciones2.setId(opciones1.getId());
        assertThat(opciones1).isEqualTo(opciones2);

        opciones2 = getOpcionesSample2();
        assertThat(opciones1).isNotEqualTo(opciones2);
    }

    @Test
    void personalizacionTest() {
        Opciones opciones = getOpcionesRandomSampleGenerator();
        Personalizaciones personalizacionesBack = getPersonalizacionesRandomSampleGenerator();

        opciones.setPersonalizacion(personalizacionesBack);
        assertThat(opciones.getPersonalizacion()).isEqualTo(personalizacionesBack);

        opciones.personalizacion(null);
        assertThat(opciones.getPersonalizacion()).isNull();
    }

    @Test
    void personalizacionOpcionesTest() {
        Opciones opciones = getOpcionesRandomSampleGenerator();
        Personalizaciones personalizacionesBack = getPersonalizacionesRandomSampleGenerator();

        opciones.setPersonalizacionOpciones(personalizacionesBack);
        assertThat(opciones.getPersonalizacionOpciones()).isEqualTo(personalizacionesBack);

        opciones.personalizacionOpciones(null);
        assertThat(opciones.getPersonalizacionOpciones()).isNull();
    }

    @Test
    void ventaOpcionesTest() {
        Opciones opciones = getOpcionesRandomSampleGenerator();
        Venta ventaBack = getVentaRandomSampleGenerator();

        opciones.addVentaOpciones(ventaBack);
        assertThat(opciones.getVentaOpciones()).containsOnly(ventaBack);
        assertThat(ventaBack.getPersonalizacionesVentas()).containsOnly(opciones);

        opciones.removeVentaOpciones(ventaBack);
        assertThat(opciones.getVentaOpciones()).doesNotContain(ventaBack);
        assertThat(ventaBack.getPersonalizacionesVentas()).doesNotContain(opciones);

        opciones.ventaOpciones(new HashSet<>(Set.of(ventaBack)));
        assertThat(opciones.getVentaOpciones()).containsOnly(ventaBack);
        assertThat(ventaBack.getPersonalizacionesVentas()).containsOnly(opciones);

        opciones.setVentaOpciones(new HashSet<>());
        assertThat(opciones.getVentaOpciones()).doesNotContain(ventaBack);
        assertThat(ventaBack.getPersonalizacionesVentas()).doesNotContain(opciones);
    }
}
