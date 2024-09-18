import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './adicionales.reducer';

export const AdicionalesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const adicionalesEntity = useAppSelector(state => state.adicionales.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adicionalesDetailsHeading">
          <Translate contentKey="demo1App.adicionales.detail.title">Adicionales</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{adicionalesEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="demo1App.adicionales.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{adicionalesEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="demo1App.adicionales.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{adicionalesEntity.descripcion}</dd>
          <dt>
            <span id="precio">
              <Translate contentKey="demo1App.adicionales.precio">Precio</Translate>
            </span>
          </dt>
          <dd>{adicionalesEntity.precio}</dd>
          <dt>
            <span id="precioGratis">
              <Translate contentKey="demo1App.adicionales.precioGratis">Precio Gratis</Translate>
            </span>
          </dt>
          <dd>{adicionalesEntity.precioGratis}</dd>
          <dt>
            <Translate contentKey="demo1App.adicionales.ventaAdicionales">Venta Adicionales</Translate>
          </dt>
          <dd>
            {adicionalesEntity.ventaAdicionales
              ? adicionalesEntity.ventaAdicionales.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {adicionalesEntity.ventaAdicionales && i === adicionalesEntity.ventaAdicionales.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/adicionales" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adicionales/${adicionalesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdicionalesDetail;
