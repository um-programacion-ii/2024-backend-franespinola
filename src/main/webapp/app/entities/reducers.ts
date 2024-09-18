import dispositivos from 'app/entities/dispositivos/dispositivos.reducer';
import caracteristicas from 'app/entities/caracteristicas/caracteristicas.reducer';
import personalizaciones from 'app/entities/personalizaciones/personalizaciones.reducer';
import opciones from 'app/entities/opciones/opciones.reducer';
import adicionales from 'app/entities/adicionales/adicionales.reducer';
import venta from 'app/entities/venta/venta.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  dispositivos,
  caracteristicas,
  personalizaciones,
  opciones,
  adicionales,
  venta,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
