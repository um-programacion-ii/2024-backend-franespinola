import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Personalizaciones from './personalizaciones';
import PersonalizacionesDetail from './personalizaciones-detail';
import PersonalizacionesUpdate from './personalizaciones-update';
import PersonalizacionesDeleteDialog from './personalizaciones-delete-dialog';

const PersonalizacionesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Personalizaciones />} />
    <Route path="new" element={<PersonalizacionesUpdate />} />
    <Route path=":id">
      <Route index element={<PersonalizacionesDetail />} />
      <Route path="edit" element={<PersonalizacionesUpdate />} />
      <Route path="delete" element={<PersonalizacionesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PersonalizacionesRoutes;
