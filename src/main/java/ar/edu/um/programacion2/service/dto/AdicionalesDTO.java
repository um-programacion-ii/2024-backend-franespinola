package ar.edu.um.programacion2.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link ar.edu.um.programacion2.domain.Adicionales} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdicionalesDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal precio;

    @NotNull
    @DecimalMin(value = "-1")
    private BigDecimal precioGratis;

    private Set<VentaDTO> ventaAdicionales = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getPrecioGratis() {
        return precioGratis;
    }

    public void setPrecioGratis(BigDecimal precioGratis) {
        this.precioGratis = precioGratis;
    }

    public Set<VentaDTO> getVentaAdicionales() {
        return ventaAdicionales;
    }

    public void setVentaAdicionales(Set<VentaDTO> ventaAdicionales) {
        this.ventaAdicionales = ventaAdicionales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdicionalesDTO)) {
            return false;
        }

        AdicionalesDTO adicionalesDTO = (AdicionalesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adicionalesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdicionalesDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", precio=" + getPrecio() +
            ", precioGratis=" + getPrecioGratis() +
            ", ventaAdicionales=" + getVentaAdicionales() +
            "}";
    }
}
