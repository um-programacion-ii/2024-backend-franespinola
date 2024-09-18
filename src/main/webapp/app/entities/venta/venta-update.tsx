import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getDispositivos } from 'app/entities/dispositivos/dispositivos.reducer';
import { getEntities as getOpciones } from 'app/entities/opciones/opciones.reducer';
import { getEntities as getAdicionales } from 'app/entities/adicionales/adicionales.reducer';
import { createEntity, getEntity, updateEntity } from './venta.reducer';

export const VentaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dispositivos = useAppSelector(state => state.dispositivos.entities);
  const opciones = useAppSelector(state => state.opciones.entities);
  const adicionales = useAppSelector(state => state.adicionales.entities);
  const ventaEntity = useAppSelector(state => state.venta.entity);
  const loading = useAppSelector(state => state.venta.loading);
  const updating = useAppSelector(state => state.venta.updating);
  const updateSuccess = useAppSelector(state => state.venta.updateSuccess);

  const handleClose = () => {
    navigate('/venta');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getDispositivos({}));
    dispatch(getOpciones({}));
    dispatch(getAdicionales({}));
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
    if (values.precioFinal !== undefined && typeof values.precioFinal !== 'number') {
      values.precioFinal = Number(values.precioFinal);
    }
    values.fechaVenta = convertDateTimeToServer(values.fechaVenta);
    if (values.precioPersonalizaciones !== undefined && typeof values.precioPersonalizaciones !== 'number') {
      values.precioPersonalizaciones = Number(values.precioPersonalizaciones);
    }
    if (values.precioAdicionales !== undefined && typeof values.precioAdicionales !== 'number') {
      values.precioAdicionales = Number(values.precioAdicionales);
    }

    const entity = {
      ...ventaEntity,
      ...values,
      dispositivoVenta: dispositivos.find(it => it.id.toString() === values.dispositivoVenta?.toString()),
      personalizacionesVentas: mapIdList(values.personalizacionesVentas),
      adicionalesVentas: mapIdList(values.adicionalesVentas),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          fechaVenta: displayDefaultDateTime(),
        }
      : {
          ...ventaEntity,
          fechaVenta: convertDateTimeFromServer(ventaEntity.fechaVenta),
          dispositivoVenta: ventaEntity?.dispositivoVenta?.id,
          personalizacionesVentas: ventaEntity?.personalizacionesVentas?.map(e => e.id.toString()),
          adicionalesVentas: ventaEntity?.adicionalesVentas?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo1App.venta.home.createOrEditLabel" data-cy="VentaCreateUpdateHeading">
            <Translate contentKey="demo1App.venta.home.createOrEditLabel">Create or edit a Venta</Translate>
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
                  id="venta-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('demo1App.venta.precioFinal')}
                id="venta-precioFinal"
                name="precioFinal"
                data-cy="precioFinal"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('demo1App.venta.fechaVenta')}
                id="venta-fechaVenta"
                name="fechaVenta"
                data-cy="fechaVenta"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('demo1App.venta.precioPersonalizaciones')}
                id="venta-precioPersonalizaciones"
                name="precioPersonalizaciones"
                data-cy="precioPersonalizaciones"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('demo1App.venta.precioAdicionales')}
                id="venta-precioAdicionales"
                name="precioAdicionales"
                data-cy="precioAdicionales"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="venta-dispositivoVenta"
                name="dispositivoVenta"
                data-cy="dispositivoVenta"
                label={translate('demo1App.venta.dispositivoVenta')}
                type="select"
                required
              >
                <option value="" key="0" />
                {dispositivos
                  ? dispositivos.map(otherEntity => (
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
                label={translate('demo1App.venta.personalizacionesVenta')}
                id="venta-personalizacionesVenta"
                data-cy="personalizacionesVenta"
                type="select"
                multiple
                name="personalizacionesVentas"
              >
                <option value="" key="0" />
                {opciones
                  ? opciones.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('demo1App.venta.adicionalesVenta')}
                id="venta-adicionalesVenta"
                data-cy="adicionalesVenta"
                type="select"
                multiple
                name="adicionalesVentas"
              >
                <option value="" key="0" />
                {adicionales
                  ? adicionales.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/venta" replace color="info">
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

export default VentaUpdate;
