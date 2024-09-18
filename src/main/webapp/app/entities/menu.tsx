import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/dispositivos">
        <Translate contentKey="global.menu.entities.dispositivos" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/caracteristicas">
        <Translate contentKey="global.menu.entities.caracteristicas" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/personalizaciones">
        <Translate contentKey="global.menu.entities.personalizaciones" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/opciones">
        <Translate contentKey="global.menu.entities.opciones" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/adicionales">
        <Translate contentKey="global.menu.entities.adicionales" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/venta">
        <Translate contentKey="global.menu.entities.venta" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
