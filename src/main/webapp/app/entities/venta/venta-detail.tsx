import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './venta.reducer';

export const VentaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ventaEntity = useAppSelector(state => state.venta.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ventaDetailsHeading">
          <Translate contentKey="demo1App.venta.detail.title">Venta</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.id}</dd>
          <dt>
            <span id="precioFinal">
              <Translate contentKey="demo1App.venta.precioFinal">Precio Final</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.precioFinal}</dd>
          <dt>
            <span id="fechaVenta">
              <Translate contentKey="demo1App.venta.fechaVenta">Fecha Venta</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.fechaVenta ? <TextFormat value={ventaEntity.fechaVenta} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="precioPersonalizaciones">
              <Translate contentKey="demo1App.venta.precioPersonalizaciones">Precio Personalizaciones</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.precioPersonalizaciones}</dd>
          <dt>
            <span id="precioAdicionales">
              <Translate contentKey="demo1App.venta.precioAdicionales">Precio Adicionales</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.precioAdicionales}</dd>
          <dt>
            <Translate contentKey="demo1App.venta.dispositivoVenta">Dispositivo Venta</Translate>
          </dt>
          <dd>{ventaEntity.dispositivoVenta ? ventaEntity.dispositivoVenta.id : ''}</dd>
          <dt>
            <Translate contentKey="demo1App.venta.personalizacionesVenta">Personalizaciones Venta</Translate>
          </dt>
          <dd>
            {ventaEntity.personalizacionesVentas
              ? ventaEntity.personalizacionesVentas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {ventaEntity.personalizacionesVentas && i === ventaEntity.personalizacionesVentas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="demo1App.venta.adicionalesVenta">Adicionales Venta</Translate>
          </dt>
          <dd>
            {ventaEntity.adicionalesVentas
              ? ventaEntity.adicionalesVentas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {ventaEntity.adicionalesVentas && i === ventaEntity.adicionalesVentas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/venta" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/venta/${ventaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VentaDetail;
