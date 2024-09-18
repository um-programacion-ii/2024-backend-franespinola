package ar.edu.um.programacion2.service.mapper;

import ar.edu.um.programacion2.domain.Personalizaciones;
import ar.edu.um.programacion2.service.dto.PersonalizacionesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Personalizaciones} and its DTO {@link PersonalizacionesDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonalizacionesMapper extends EntityMapper<PersonalizacionesDTO, Personalizaciones> {}
