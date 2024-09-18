package ar.edu.um.programacion2.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link ar.edu.um.programacion2.domain.Venta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VentaDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal precioFinal;

    @NotNull
    private Instant fechaVenta;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal precioPersonalizaciones;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal precioAdicionales;

    @NotNull
    private DispositivosDTO dispositivoVenta;

    private Set<OpcionesDTO> personalizacionesVentas = new HashSet<>();

    private Set<AdicionalesDTO> adicionalesVentas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(BigDecimal precioFinal) {
        this.precioFinal = precioFinal;
    }

    public Instant getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Instant fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getPrecioPersonalizaciones() {
        return precioPersonalizaciones;
    }

    public void setPrecioPersonalizaciones(BigDecimal precioPersonalizaciones) {
        this.precioPersonalizaciones = precioPersonalizaciones;
    }

    public BigDecimal getPrecioAdicionales() {
        return precioAdicionales;
    }

    public void setPrecioAdicionales(BigDecimal precioAdicionales) {
        this.precioAdicionales = precioAdicionales;
    }

    public DispositivosDTO getDispositivoVenta() {
        return dispositivoVenta;
    }

    public void setDispositivoVenta(DispositivosDTO dispositivoVenta) {
        this.dispositivoVenta = dispositivoVenta;
    }

    public Set<OpcionesDTO> getPersonalizacionesVentas() {
        return personalizacionesVentas;
    }

    public void setPersonalizacionesVentas(Set<OpcionesDTO> personalizacionesVentas) {
        this.personalizacionesVentas = personalizacionesVentas;
    }

    public Set<AdicionalesDTO> getAdicionalesVentas() {
        return adicionalesVentas;
    }

    public void setAdicionalesVentas(Set<AdicionalesDTO> adicionalesVentas) {
        this.adicionalesVentas = adicionalesVentas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VentaDTO)) {
            return false;
        }

        VentaDTO ventaDTO = (VentaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ventaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VentaDTO{" +
            "id=" + getId() +
            ", precioFinal=" + getPrecioFinal() +
            ", fechaVenta='" + getFechaVenta() + "'" +
            ", precioPersonalizaciones=" + getPrecioPersonalizaciones() +
            ", precioAdicionales=" + getPrecioAdicionales() +
            ", dispositivoVenta=" + getDispositivoVenta() +
            ", personalizacionesVentas=" + getPersonalizacionesVentas() +
            ", adicionalesVentas=" + getAdicionalesVentas() +
            "}";
    }
}
