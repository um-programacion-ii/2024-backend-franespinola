package ar.edu.um.programacion2.service.mapper;

import ar.edu.um.programacion2.domain.Adicionales;
import ar.edu.um.programacion2.domain.Venta;
import ar.edu.um.programacion2.service.dto.AdicionalesDTO;
import ar.edu.um.programacion2.service.dto.VentaDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Adicionales} and its DTO {@link AdicionalesDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdicionalesMapper extends EntityMapper<AdicionalesDTO, Adicionales> {
    @Mapping(target = "ventaAdicionales", source = "ventaAdicionales", qualifiedByName = "ventaIdSet")
    AdicionalesDTO toDto(Adicionales s);

    @Mapping(target = "ventaAdicionales", ignore = true)
    @Mapping(target = "removeVentaAdicionales", ignore = true)
    Adicionales toEntity(AdicionalesDTO adicionalesDTO);

    @Named("ventaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VentaDTO toDtoVentaId(Venta venta);

    @Named("ventaIdSet")
    default Set<VentaDTO> toDtoVentaIdSet(Set<Venta> venta) {
        return venta.stream().map(this::toDtoVentaId).collect(Collectors.toSet());
    }
}
