package ar.edu.um.programacion2.service.mapper;

import ar.edu.um.programacion2.domain.Opciones;
import ar.edu.um.programacion2.domain.Personalizaciones;
import ar.edu.um.programacion2.domain.Venta;
import ar.edu.um.programacion2.service.dto.OpcionesDTO;
import ar.edu.um.programacion2.service.dto.PersonalizacionesDTO;
import ar.edu.um.programacion2.service.dto.VentaDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Opciones} and its DTO {@link OpcionesDTO}.
 */
@Mapper(componentModel = "spring")
public interface OpcionesMapper extends EntityMapper<OpcionesDTO, Opciones> {
    @Mapping(target = "personalizacion", source = "personalizacion", qualifiedByName = "personalizacionesId")
    @Mapping(target = "personalizacionOpciones", source = "personalizacionOpciones", qualifiedByName = "personalizacionesId")
    @Mapping(target = "ventaOpciones", source = "ventaOpciones", qualifiedByName = "ventaIdSet")
    OpcionesDTO toDto(Opciones s);

    @Mapping(target = "ventaOpciones", ignore = true)
    @Mapping(target = "removeVentaOpciones", ignore = true)
    Opciones toEntity(OpcionesDTO opcionesDTO);

    @Named("personalizacionesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonalizacionesDTO toDtoPersonalizacionesId(Personalizaciones personalizaciones);

    @Named("ventaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VentaDTO toDtoVentaId(Venta venta);

    @Named("ventaIdSet")
    default Set<VentaDTO> toDtoVentaIdSet(Set<Venta> venta) {
        return venta.stream().map(this::toDtoVentaId).collect(Collectors.toSet());
    }
}
