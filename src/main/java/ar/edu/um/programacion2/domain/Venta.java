package ar.edu.um.programacion2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Venta.
 */
@Entity
@Table(name = "venta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "precio_final", precision = 21, scale = 2, nullable = false)
    private BigDecimal precioFinal;

    @NotNull
    @Column(name = "fecha_venta", nullable = false)
    private Instant fechaVenta;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "precio_personalizaciones", precision = 21, scale = 2, nullable = false)
    private BigDecimal precioPersonalizaciones;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "precio_adicionales", precision = 21, scale = 2, nullable = false)
    private BigDecimal precioAdicionales;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "caracteristicas" }, allowSetters = true)
    private Dispositivos dispositivoVenta;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_venta__personalizaciones_venta",
        joinColumns = @JoinColumn(name = "venta_id"),
        inverseJoinColumns = @JoinColumn(name = "personalizaciones_venta_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "personalizacion", "personalizacionOpciones", "ventaOpciones" }, allowSetters = true)
    private Set<Opciones> personalizacionesVentas = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_venta__adicionales_venta",
        joinColumns = @JoinColumn(name = "venta_id"),
        inverseJoinColumns = @JoinColumn(name = "adicionales_venta_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ventaAdicionales" }, allowSetters = true)
    private Set<Adicionales> adicionalesVentas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Venta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrecioFinal() {
        return this.precioFinal;
    }

    public Venta precioFinal(BigDecimal precioFinal) {
        this.setPrecioFinal(precioFinal);
        return this;
    }

    public void setPrecioFinal(BigDecimal precioFinal) {
        this.precioFinal = precioFinal;
    }

    public Instant getFechaVenta() {
        return this.fechaVenta;
    }

    public Venta fechaVenta(Instant fechaVenta) {
        this.setFechaVenta(fechaVenta);
        return this;
    }

    public void setFechaVenta(Instant fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getPrecioPersonalizaciones() {
        return this.precioPersonalizaciones;
    }

    public Venta precioPersonalizaciones(BigDecimal precioPersonalizaciones) {
        this.setPrecioPersonalizaciones(precioPersonalizaciones);
        return this;
    }

    public void setPrecioPersonalizaciones(BigDecimal precioPersonalizaciones) {
        this.precioPersonalizaciones = precioPersonalizaciones;
    }

    public BigDecimal getPrecioAdicionales() {
        return this.precioAdicionales;
    }

    public Venta precioAdicionales(BigDecimal precioAdicionales) {
        this.setPrecioAdicionales(precioAdicionales);
        return this;
    }

    public void setPrecioAdicionales(BigDecimal precioAdicionales) {
        this.precioAdicionales = precioAdicionales;
    }

    public Dispositivos getDispositivoVenta() {
        return this.dispositivoVenta;
    }

    public void setDispositivoVenta(Dispositivos dispositivos) {
        this.dispositivoVenta = dispositivos;
    }

    public Venta dispositivoVenta(Dispositivos dispositivos) {
        this.setDispositivoVenta(dispositivos);
        return this;
    }

    public Set<Opciones> getPersonalizacionesVentas() {
        return this.personalizacionesVentas;
    }

    public void setPersonalizacionesVentas(Set<Opciones> opciones) {
        this.personalizacionesVentas = opciones;
    }

    public Venta personalizacionesVentas(Set<Opciones> opciones) {
        this.setPersonalizacionesVentas(opciones);
        return this;
    }

    public Venta addPersonalizacionesVenta(Opciones opciones) {
        this.personalizacionesVentas.add(opciones);
        return this;
    }

    public Venta removePersonalizacionesVenta(Opciones opciones) {
        this.personalizacionesVentas.remove(opciones);
        return this;
    }

    public Set<Adicionales> getAdicionalesVentas() {
        return this.adicionalesVentas;
    }

    public void setAdicionalesVentas(Set<Adicionales> adicionales) {
        this.adicionalesVentas = adicionales;
    }

    public Venta adicionalesVentas(Set<Adicionales> adicionales) {
        this.setAdicionalesVentas(adicionales);
        return this;
    }

    public Venta addAdicionalesVenta(Adicionales adicionales) {
        this.adicionalesVentas.add(adicionales);
        return this;
    }

    public Venta removeAdicionalesVenta(Adicionales adicionales) {
        this.adicionalesVentas.remove(adicionales);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venta)) {
            return false;
        }
        return getId() != null && getId().equals(((Venta) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venta{" +
            "id=" + getId() +
            ", precioFinal=" + getPrecioFinal() +
            ", fechaVenta='" + getFechaVenta() + "'" +
            ", precioPersonalizaciones=" + getPrecioPersonalizaciones() +
            ", precioAdicionales=" + getPrecioAdicionales() +
            "}";
    }
}
