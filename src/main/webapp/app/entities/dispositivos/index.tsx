import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Dispositivos from './dispositivos';
import DispositivosDetail from './dispositivos-detail';
import DispositivosUpdate from './dispositivos-update';
import DispositivosDeleteDialog from './dispositivos-delete-dialog';

const DispositivosRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Dispositivos />} />
    <Route path="new" element={<DispositivosUpdate />} />
    <Route path=":id">
      <Route index element={<DispositivosDetail />} />
      <Route path="edit" element={<DispositivosUpdate />} />
      <Route path="delete" element={<DispositivosDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DispositivosRoutes;
