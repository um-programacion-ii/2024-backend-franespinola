import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Opciones from './opciones';
import OpcionesDetail from './opciones-detail';
import OpcionesUpdate from './opciones-update';
import OpcionesDeleteDialog from './opciones-delete-dialog';

const OpcionesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Opciones />} />
    <Route path="new" element={<OpcionesUpdate />} />
    <Route path=":id">
      <Route index element={<OpcionesDetail />} />
      <Route path="edit" element={<OpcionesUpdate />} />
      <Route path="delete" element={<OpcionesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OpcionesRoutes;
