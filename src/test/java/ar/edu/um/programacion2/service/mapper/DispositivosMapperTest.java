package ar.edu.um.programacion2.service.mapper;

import static ar.edu.um.programacion2.domain.DispositivosAsserts.*;
import static ar.edu.um.programacion2.domain.DispositivosTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DispositivosMapperTest {

    private DispositivosMapper dispositivosMapper;

    @BeforeEach
    void setUp() {
        dispositivosMapper = new DispositivosMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDispositivosSample1();
        var actual = dispositivosMapper.toEntity(dispositivosMapper.toDto(expected));
        assertDispositivosAllPropertiesEquals(expected, actual);
    }
}
