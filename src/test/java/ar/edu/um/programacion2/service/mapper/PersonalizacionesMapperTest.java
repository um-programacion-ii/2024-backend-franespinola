package ar.edu.um.programacion2.service.mapper;

import static ar.edu.um.programacion2.domain.PersonalizacionesAsserts.*;
import static ar.edu.um.programacion2.domain.PersonalizacionesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonalizacionesMapperTest {

    private PersonalizacionesMapper personalizacionesMapper;

    @BeforeEach
    void setUp() {
        personalizacionesMapper = new PersonalizacionesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPersonalizacionesSample1();
        var actual = personalizacionesMapper.toEntity(personalizacionesMapper.toDto(expected));
        assertPersonalizacionesAllPropertiesEquals(expected, actual);
    }
}
