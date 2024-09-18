package ar.edu.um.programacion2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Adicionales.
 */
@Entity
@Table(name = "adicionales")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Adicionales implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "precio", precision = 21, scale = 2, nullable = false)
    private BigDecimal precio;

    @NotNull
    @DecimalMin(value = "-1")
    @Column(name = "precio_gratis", precision = 21, scale = 2, nullable = false)
    private BigDecimal precioGratis;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "adicionalesVentas")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dispositivoVenta", "personalizacionesVentas", "adicionalesVentas" }, allowSetters = true)
    private Set<Venta> ventaAdicionales = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Adicionales id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Adicionales nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Adicionales descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return this.precio;
    }

    public Adicionales precio(BigDecimal precio) {
        this.setPrecio(precio);
        return this;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getPrecioGratis() {
        return this.precioGratis;
    }

    public Adicionales precioGratis(BigDecimal precioGratis) {
        this.setPrecioGratis(precioGratis);
        return this;
    }

    public void setPrecioGratis(BigDecimal precioGratis) {
        this.precioGratis = precioGratis;
    }

    public Set<Venta> getVentaAdicionales() {
        return this.ventaAdicionales;
    }

    public void setVentaAdicionales(Set<Venta> ventas) {
        if (this.ventaAdicionales != null) {
            this.ventaAdicionales.forEach(i -> i.removeAdicionalesVenta(this));
        }
        if (ventas != null) {
            ventas.forEach(i -> i.addAdicionalesVenta(this));
        }
        this.ventaAdicionales = ventas;
    }

    public Adicionales ventaAdicionales(Set<Venta> ventas) {
        this.setVentaAdicionales(ventas);
        return this;
    }

    public Adicionales addVentaAdicionales(Venta venta) {
        this.ventaAdicionales.add(venta);
        venta.getAdicionalesVentas().add(this);
        return this;
    }

    public Adicionales removeVentaAdicionales(Venta venta) {
        this.ventaAdicionales.remove(venta);
        venta.getAdicionalesVentas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Adicionales)) {
            return false;
        }
        return getId() != null && getId().equals(((Adicionales) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Adicionales{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", precio=" + getPrecio() +
            ", precioGratis=" + getPrecioGratis() +
            "}";
    }
}
