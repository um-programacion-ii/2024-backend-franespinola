import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './adicional.reducer';

export const AdicionalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const adicionalEntity = useAppSelector(state => state.adicional.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adicionalDetailsHeading">
          <Translate contentKey="servidorApiApp.adicional.detail.title">Adicional</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{adicionalEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="servidorApiApp.adicional.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{adicionalEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="servidorApiApp.adicional.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{adicionalEntity.descripcion}</dd>
          <dt>
            <span id="precio">
              <Translate contentKey="servidorApiApp.adicional.precio">Precio</Translate>
            </span>
          </dt>
          <dd>{adicionalEntity.precio}</dd>
          <dt>
            <span id="precioGratis">
              <Translate contentKey="servidorApiApp.adicional.precioGratis">Precio Gratis</Translate>
            </span>
          </dt>
          <dd>{adicionalEntity.precioGratis}</dd>
          <dt>
            <Translate contentKey="servidorApiApp.adicional.dispositivo">Dispositivo</Translate>
          </dt>
          <dd>{adicionalEntity.dispositivo ? adicionalEntity.dispositivo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/adicional" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adicional/${adicionalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdicionalDetail;
