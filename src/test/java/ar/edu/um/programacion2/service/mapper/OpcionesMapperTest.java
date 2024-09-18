package ar.edu.um.programacion2.service.mapper;

import static ar.edu.um.programacion2.domain.OpcionesAsserts.*;
import static ar.edu.um.programacion2.domain.OpcionesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpcionesMapperTest {

    private OpcionesMapper opcionesMapper;

    @BeforeEach
    void setUp() {
        opcionesMapper = new OpcionesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOpcionesSample1();
        var actual = opcionesMapper.toEntity(opcionesMapper.toDto(expected));
        assertOpcionesAllPropertiesEquals(expected, actual);
    }
}
