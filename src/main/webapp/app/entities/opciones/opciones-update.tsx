import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getPersonalizaciones } from 'app/entities/personalizaciones/personalizaciones.reducer';
import { getEntities as getVentas } from 'app/entities/venta/venta.reducer';
import { createEntity, getEntity, updateEntity } from './opciones.reducer';

export const OpcionesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const personalizaciones = useAppSelector(state => state.personalizaciones.entities);
  const ventas = useAppSelector(state => state.venta.entities);
  const opcionesEntity = useAppSelector(state => state.opciones.entity);
  const loading = useAppSelector(state => state.opciones.loading);
  const updating = useAppSelector(state => state.opciones.updating);
  const updateSuccess = useAppSelector(state => state.opciones.updateSuccess);

  const handleClose = () => {
    navigate('/opciones');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getPersonalizaciones({}));
    dispatch(getVentas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.precioAdicional !== undefined && typeof values.precioAdicional !== 'number') {
      values.precioAdicional = Number(values.precioAdicional);
    }

    const entity = {
      ...opcionesEntity,
      ...values,
      personalizacion: personalizaciones.find(it => it.id.toString() === values.personalizacion?.toString()),
      personalizacionOpciones: personalizaciones.find(it => it.id.toString() === values.personalizacionOpciones?.toString()),
      ventaOpciones: mapIdList(values.ventaOpciones),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...opcionesEntity,
          personalizacion: opcionesEntity?.personalizacion?.id,
          personalizacionOpciones: opcionesEntity?.personalizacionOpciones?.id,
          ventaOpciones: opcionesEntity?.ventaOpciones?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo1App.opciones.home.createOrEditLabel" data-cy="OpcionesCreateUpdateHeading">
            <Translate contentKey="demo1App.opciones.home.createOrEditLabel">Create or edit a Opciones</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="opciones-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('demo1App.opciones.codigo')}
                id="opciones-codigo"
                name="codigo"
                data-cy="codigo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('demo1App.opciones.nombre')}
                id="opciones-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('demo1App.opciones.descripcion')}
                id="opciones-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('demo1App.opciones.precioAdicional')}
                id="opciones-precioAdicional"
                name="precioAdicional"
                data-cy="precioAdicional"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="opciones-personalizacion"
                name="personalizacion"
                data-cy="personalizacion"
                label={translate('demo1App.opciones.personalizacion')}
                type="select"
                required
              >
                <option value="" key="0" />
                {personalizaciones
                  ? personalizaciones.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="opciones-personalizacionOpciones"
                name="personalizacionOpciones"
                data-cy="personalizacionOpciones"
                label={translate('demo1App.opciones.personalizacionOpciones')}
                type="select"
              >
                <option value="" key="0" />
                {personalizaciones
                  ? personalizaciones.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('demo1App.opciones.ventaOpciones')}
                id="opciones-ventaOpciones"
                data-cy="ventaOpciones"
                type="select"
                multiple
                name="ventaOpciones"
              >
                <option value="" key="0" />
                {ventas
                  ? ventas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/opciones" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default OpcionesUpdate;
