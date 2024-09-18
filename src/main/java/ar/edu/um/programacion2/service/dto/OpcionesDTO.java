package ar.edu.um.programacion2.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link ar.edu.um.programacion2.domain.Opciones} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OpcionesDTO implements Serializable {

    private Long id;

    @NotNull
    private String codigo;

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal precioAdicional;

    @NotNull
    private PersonalizacionesDTO personalizacion;

    private PersonalizacionesDTO personalizacionOpciones;

    private Set<VentaDTO> ventaOpciones = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public BigDecimal getPrecioAdicional() {
        return precioAdicional;
    }

    public void setPrecioAdicional(BigDecimal precioAdicional) {
        this.precioAdicional = precioAdicional;
    }

    public PersonalizacionesDTO getPersonalizacion() {
        return personalizacion;
    }

    public void setPersonalizacion(PersonalizacionesDTO personalizacion) {
        this.personalizacion = personalizacion;
    }

    public PersonalizacionesDTO getPersonalizacionOpciones() {
        return personalizacionOpciones;
    }

    public void setPersonalizacionOpciones(PersonalizacionesDTO personalizacionOpciones) {
        this.personalizacionOpciones = personalizacionOpciones;
    }

    public Set<VentaDTO> getVentaOpciones() {
        return ventaOpciones;
    }

    public void setVentaOpciones(Set<VentaDTO> ventaOpciones) {
        this.ventaOpciones = ventaOpciones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OpcionesDTO)) {
            return false;
        }

        OpcionesDTO opcionesDTO = (OpcionesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, opcionesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OpcionesDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", precioAdicional=" + getPrecioAdicional() +
            ", personalizacion=" + getPersonalizacion() +
            ", personalizacionOpciones=" + getPersonalizacionOpciones() +
            ", ventaOpciones=" + getVentaOpciones() +
            "}";
    }
}
