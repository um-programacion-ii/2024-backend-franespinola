import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Dispositivos from './dispositivos';
import Caracteristicas from './caracteristicas';
import Personalizaciones from './personalizaciones';
import Opciones from './opciones';
import Adicionales from './adicionales';
import Venta from './venta';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="dispositivos/*" element={<Dispositivos />} />
        <Route path="caracteristicas/*" element={<Caracteristicas />} />
        <Route path="personalizaciones/*" element={<Personalizaciones />} />
        <Route path="opciones/*" element={<Opciones />} />
        <Route path="adicionales/*" element={<Adicionales />} />
        <Route path="venta/*" element={<Venta />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
