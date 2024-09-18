import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Caracteristicas from './caracteristicas';
import CaracteristicasDetail from './caracteristicas-detail';
import CaracteristicasUpdate from './caracteristicas-update';
import CaracteristicasDeleteDialog from './caracteristicas-delete-dialog';

const CaracteristicasRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Caracteristicas />} />
    <Route path="new" element={<CaracteristicasUpdate />} />
    <Route path=":id">
      <Route index element={<CaracteristicasDetail />} />
      <Route path="edit" element={<CaracteristicasUpdate />} />
      <Route path="delete" element={<CaracteristicasDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CaracteristicasRoutes;
