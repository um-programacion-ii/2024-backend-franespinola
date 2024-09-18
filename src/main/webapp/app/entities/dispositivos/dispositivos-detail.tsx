import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './dispositivos.reducer';

export const DispositivosDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dispositivosEntity = useAppSelector(state => state.dispositivos.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dispositivosDetailsHeading">
          <Translate contentKey="demo1App.dispositivos.detail.title">Dispositivos</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{dispositivosEntity.id}</dd>
          <dt>
            <span id="codigo">
              <Translate contentKey="demo1App.dispositivos.codigo">Codigo</Translate>
            </span>
          </dt>
          <dd>{dispositivosEntity.codigo}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="demo1App.dispositivos.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{dispositivosEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="demo1App.dispositivos.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{dispositivosEntity.descripcion}</dd>
          <dt>
            <span id="precioBase">
              <Translate contentKey="demo1App.dispositivos.precioBase">Precio Base</Translate>
            </span>
          </dt>
          <dd>{dispositivosEntity.precioBase}</dd>
          <dt>
            <span id="moneda">
              <Translate contentKey="demo1App.dispositivos.moneda">Moneda</Translate>
            </span>
          </dt>
          <dd>{dispositivosEntity.moneda}</dd>
        </dl>
        <Button tag={Link} to="/dispositivos" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/dispositivos/${dispositivosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DispositivosDetail;
