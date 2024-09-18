import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Adicionales from './adicionales';
import AdicionalesDetail from './adicionales-detail';
import AdicionalesUpdate from './adicionales-update';
import AdicionalesDeleteDialog from './adicionales-delete-dialog';

const AdicionalesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Adicionales />} />
    <Route path="new" element={<AdicionalesUpdate />} />
    <Route path=":id">
      <Route index element={<AdicionalesDetail />} />
      <Route path="edit" element={<AdicionalesUpdate />} />
      <Route path="delete" element={<AdicionalesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AdicionalesRoutes;
