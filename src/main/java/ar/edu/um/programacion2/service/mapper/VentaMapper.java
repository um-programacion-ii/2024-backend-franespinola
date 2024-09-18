package ar.edu.um.programacion2.service.mapper;

import ar.edu.um.programacion2.domain.Adicionales;
import ar.edu.um.programacion2.domain.Dispositivos;
import ar.edu.um.programacion2.domain.Opciones;
import ar.edu.um.programacion2.domain.Venta;
import ar.edu.um.programacion2.service.dto.AdicionalesDTO;
import ar.edu.um.programacion2.service.dto.DispositivosDTO;
import ar.edu.um.programacion2.service.dto.OpcionesDTO;
import ar.edu.um.programacion2.service.dto.VentaDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Venta} and its DTO {@link VentaDTO}.
 */
@Mapper(componentModel = "spring")
public interface VentaMapper extends EntityMapper<VentaDTO, Venta> {
    @Mapping(target = "dispositivoVenta", source = "dispositivoVenta", qualifiedByName = "dispositivosId")
    @Mapping(target = "personalizacionesVentas", source = "personalizacionesVentas", qualifiedByName = "opcionesIdSet")
    @Mapping(target = "adicionalesVentas", source = "adicionalesVentas", qualifiedByName = "adicionalesIdSet")
    VentaDTO toDto(Venta s);

    @Mapping(target = "removePersonalizacionesVenta", ignore = true)
    @Mapping(target = "removeAdicionalesVenta", ignore = true)
    Venta toEntity(VentaDTO ventaDTO);

    @Named("dispositivosId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DispositivosDTO toDtoDispositivosId(Dispositivos dispositivos);

    @Named("opcionesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OpcionesDTO toDtoOpcionesId(Opciones opciones);

    @Named("opcionesIdSet")
    default Set<OpcionesDTO> toDtoOpcionesIdSet(Set<Opciones> opciones) {
        return opciones.stream().map(this::toDtoOpcionesId).collect(Collectors.toSet());
    }

    @Named("adicionalesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdicionalesDTO toDtoAdicionalesId(Adicionales adicionales);

    @Named("adicionalesIdSet")
    default Set<AdicionalesDTO> toDtoAdicionalesIdSet(Set<Adicionales> adicionales) {
        return adicionales.stream().map(this::toDtoAdicionalesId).collect(Collectors.toSet());
    }
}
