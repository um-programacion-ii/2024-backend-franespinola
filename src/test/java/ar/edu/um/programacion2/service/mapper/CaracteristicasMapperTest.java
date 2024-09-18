package ar.edu.um.programacion2.service.mapper;

import static ar.edu.um.programacion2.domain.CaracteristicasAsserts.*;
import static ar.edu.um.programacion2.domain.CaracteristicasTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CaracteristicasMapperTest {

    private CaracteristicasMapper caracteristicasMapper;

    @BeforeEach
    void setUp() {
        caracteristicasMapper = new CaracteristicasMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCaracteristicasSample1();
        var actual = caracteristicasMapper.toEntity(caracteristicasMapper.toDto(expected));
        assertCaracteristicasAllPropertiesEquals(expected, actual);
    }
}
