import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './opciones.reducer';

export const OpcionesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const opcionesEntity = useAppSelector(state => state.opciones.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="opcionesDetailsHeading">
          <Translate contentKey="demo1App.opciones.detail.title">Opciones</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{opcionesEntity.id}</dd>
          <dt>
            <span id="codigo">
              <Translate contentKey="demo1App.opciones.codigo">Codigo</Translate>
            </span>
          </dt>
          <dd>{opcionesEntity.codigo}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="demo1App.opciones.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{opcionesEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="demo1App.opciones.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{opcionesEntity.descripcion}</dd>
          <dt>
            <span id="precioAdicional">
              <Translate contentKey="demo1App.opciones.precioAdicional">Precio Adicional</Translate>
            </span>
          </dt>
          <dd>{opcionesEntity.precioAdicional}</dd>
          <dt>
            <Translate contentKey="demo1App.opciones.personalizacion">Personalizacion</Translate>
          </dt>
          <dd>{opcionesEntity.personalizacion ? opcionesEntity.personalizacion.id : ''}</dd>
          <dt>
            <Translate contentKey="demo1App.opciones.personalizacionOpciones">Personalizacion Opciones</Translate>
          </dt>
          <dd>{opcionesEntity.personalizacionOpciones ? opcionesEntity.personalizacionOpciones.id : ''}</dd>
          <dt>
            <Translate contentKey="demo1App.opciones.ventaOpciones">Venta Opciones</Translate>
          </dt>
          <dd>
            {opcionesEntity.ventaOpciones
              ? opcionesEntity.ventaOpciones.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {opcionesEntity.ventaOpciones && i === opcionesEntity.ventaOpciones.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/opciones" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/opciones/${opcionesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OpcionesDetail;
