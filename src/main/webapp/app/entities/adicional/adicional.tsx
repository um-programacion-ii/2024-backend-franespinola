import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './adicional.reducer';

export const Adicional = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const adicionalList = useAppSelector(state => state.adicional.entities);
  const loading = useAppSelector(state => state.adicional.loading);
  const links = useAppSelector(state => state.adicional.links);
  const updateSuccess = useAppSelector(state => state.adicional.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="adicional-heading" data-cy="AdicionalHeading">
        <Translate contentKey="servidorApiApp.adicional.home.title">Adicionals</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="servidorApiApp.adicional.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/adicional/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="servidorApiApp.adicional.home.createLabel">Create new Adicional</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={adicionalList ? adicionalList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {adicionalList && adicionalList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="servidorApiApp.adicional.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('nombre')}>
                    <Translate contentKey="servidorApiApp.adicional.nombre">Nombre</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('nombre')} />
                  </th>
                  <th className="hand" onClick={sort('descripcion')}>
                    <Translate contentKey="servidorApiApp.adicional.descripcion">Descripcion</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('descripcion')} />
                  </th>
                  <th className="hand" onClick={sort('precio')}>
                    <Translate contentKey="servidorApiApp.adicional.precio">Precio</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('precio')} />
                  </th>
                  <th className="hand" onClick={sort('precioGratis')}>
                    <Translate contentKey="servidorApiApp.adicional.precioGratis">Precio Gratis</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('precioGratis')} />
                  </th>
                  <th>
                    <Translate contentKey="servidorApiApp.adicional.dispositivo">Dispositivo</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {adicionalList.map((adicional, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/adicional/${adicional.id}`} color="link" size="sm">
                        {adicional.id}
                      </Button>
                    </td>
                    <td>{adicional.nombre}</td>
                    <td>{adicional.descripcion}</td>
                    <td>{adicional.precio}</td>
                    <td>{adicional.precioGratis}</td>
                    <td>
                      {adicional.dispositivo ? <Link to={`/dispositivo/${adicional.dispositivo.id}`}>{adicional.dispositivo.id}</Link> : ''}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/adicional/${adicional.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/adicional/${adicional.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/adicional/${adicional.id}/delete`)}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="servidorApiApp.adicional.home.notFound">No Adicionals found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Adicional;