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
 * A Opciones.
 */
@Entity
@Table(name = "opciones")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Opciones implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "precio_adicional", precision = 21, scale = 2, nullable = false)
    private BigDecimal precioAdicional;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "opciones" }, allowSetters = true)
    private Personalizaciones personalizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "opciones" }, allowSetters = true)
    private Personalizaciones personalizacionOpciones;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "personalizacionesVentas")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dispositivoVenta", "personalizacionesVentas", "adicionalesVentas" }, allowSetters = true)
    private Set<Venta> ventaOpciones = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Opciones id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Opciones codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Opciones nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Opciones descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioAdicional() {
        return this.precioAdicional;
    }

    public Opciones precioAdicional(BigDecimal precioAdicional) {
        this.setPrecioAdicional(precioAdicional);
        return this;
    }

    public void setPrecioAdicional(BigDecimal precioAdicional) {
        this.precioAdicional = precioAdicional;
    }

    public Personalizaciones getPersonalizacion() {
        return this.personalizacion;
    }

    public void setPersonalizacion(Personalizaciones personalizaciones) {
        this.personalizacion = personalizaciones;
    }

    public Opciones personalizacion(Personalizaciones personalizaciones) {
        this.setPersonalizacion(personalizaciones);
        return this;
    }

    public Personalizaciones getPersonalizacionOpciones() {
        return this.personalizacionOpciones;
    }

    public void setPersonalizacionOpciones(Personalizaciones personalizaciones) {
        this.personalizacionOpciones = personalizaciones;
    }

    public Opciones personalizacionOpciones(Personalizaciones personalizaciones) {
        this.setPersonalizacionOpciones(personalizaciones);
        return this;
    }

    public Set<Venta> getVentaOpciones() {
        return this.ventaOpciones;
    }

    public void setVentaOpciones(Set<Venta> ventas) {
        if (this.ventaOpciones != null) {
            this.ventaOpciones.forEach(i -> i.removePersonalizacionesVenta(this));
        }
        if (ventas != null) {
            ventas.forEach(i -> i.addPersonalizacionesVenta(this));
        }
        this.ventaOpciones = ventas;
    }

    public Opciones ventaOpciones(Set<Venta> ventas) {
        this.setVentaOpciones(ventas);
        return this;
    }

    public Opciones addVentaOpciones(Venta venta) {
        this.ventaOpciones.add(venta);
        venta.getPersonalizacionesVentas().add(this);
        return this;
    }

    public Opciones removeVentaOpciones(Venta venta) {
        this.ventaOpciones.remove(venta);
        venta.getPersonalizacionesVentas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Opciones)) {
            return false;
        }
        return getId() != null && getId().equals(((Opciones) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Opciones{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", precioAdicional=" + getPrecioAdicional() +
            "}";
    }
}
