package ar.edu.um.programacion2.service.mapper;

import static ar.edu.um.programacion2.domain.AdicionalesAsserts.*;
import static ar.edu.um.programacion2.domain.AdicionalesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdicionalesMapperTest {

    private AdicionalesMapper adicionalesMapper;

    @BeforeEach
    void setUp() {
        adicionalesMapper = new AdicionalesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAdicionalesSample1();
        var actual = adicionalesMapper.toEntity(adicionalesMapper.toDto(expected));
        assertAdicionalesAllPropertiesEquals(expected, actual);
    }
}
