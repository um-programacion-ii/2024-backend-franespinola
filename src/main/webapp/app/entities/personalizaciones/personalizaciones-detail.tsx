import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './personalizaciones.reducer';

export const PersonalizacionesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const personalizacionesEntity = useAppSelector(state => state.personalizaciones.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="personalizacionesDetailsHeading">
          <Translate contentKey="demo1App.personalizaciones.detail.title">Personalizaciones</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{personalizacionesEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="demo1App.personalizaciones.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{personalizacionesEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="demo1App.personalizaciones.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{personalizacionesEntity.descripcion}</dd>
        </dl>
        <Button tag={Link} to="/personalizaciones" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/personalizaciones/${personalizacionesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PersonalizacionesDetail;
