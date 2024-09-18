import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './caracteristicas.reducer';

export const CaracteristicasDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const caracteristicasEntity = useAppSelector(state => state.caracteristicas.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="caracteristicasDetailsHeading">
          <Translate contentKey="demo1App.caracteristicas.detail.title">Caracteristicas</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{caracteristicasEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="demo1App.caracteristicas.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{caracteristicasEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="demo1App.caracteristicas.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{caracteristicasEntity.descripcion}</dd>
          <dt>
            <Translate contentKey="demo1App.caracteristicas.dispositivo">Dispositivo</Translate>
          </dt>
          <dd>{caracteristicasEntity.dispositivo ? caracteristicasEntity.dispositivo.id : ''}</dd>
          <dt>
            <Translate contentKey="demo1App.caracteristicas.dispositivoCaracteristicas">Dispositivo Caracteristicas</Translate>
          </dt>
          <dd>{caracteristicasEntity.dispositivoCaracteristicas ? caracteristicasEntity.dispositivoCaracteristicas.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/caracteristicas" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/caracteristicas/${caracteristicasEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CaracteristicasDetail;
