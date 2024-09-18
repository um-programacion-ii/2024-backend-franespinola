package ar.edu.um.programacion2.service;

import ar.edu.um.programacion2.domain.Opciones;
import ar.edu.um.programacion2.repository.OpcionesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.edu.um.programacion2.domain.Opciones}.
 */
@Service
@Transactional
public class OpcionesService {

    private static final Logger LOG = LoggerFactory.getLogger(OpcionesService.class);

    private final OpcionesRepository opcionesRepository;

    public OpcionesService(OpcionesRepository opcionesRepository) {
        this.opcionesRepository = opcionesRepository;
    }

    /**
     * Save a opciones.
     *
     * @param opciones the entity to save.
     * @return the persisted entity.
     */
    public Opciones save(Opciones opciones) {
        LOG.debug("Request to save Opciones : {}", opciones);
        return opcionesRepository.save(opciones);
    }

    /**
     * Update a opciones.
     *
     * @param opciones the entity to save.
     * @return the persisted entity.
     */
    public Opciones update(Opciones opciones) {
        LOG.debug("Request to update Opciones : {}", opciones);
        return opcionesRepository.save(opciones);
    }

    /**
     * Partially update a opciones.
     *
     * @param opciones the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Opciones> partialUpdate(Opciones opciones) {
        LOG.debug("Request to partially update Opciones : {}", opciones);

        return opcionesRepository
            .findById(opciones.getId())
            .map(existingOpciones -> {
                if (opciones.getCodigo() != null) {
                    existingOpciones.setCodigo(opciones.getCodigo());
                }
                if (opciones.getNombre() != null) {
                    existingOpciones.setNombre(opciones.getNombre());
                }
                if (opciones.getDescripcion() != null) {
                    existingOpciones.setDescripcion(opciones.getDescripcion());
                }
                if (opciones.getPrecioAdicional() != null) {
                    existingOpciones.setPrecioAdicional(opciones.getPrecioAdicional());
                }

                return existingOpciones;
            })
            .map(opcionesRepository::save);
    }

    /**
     * Get all the opciones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Opciones> findAll(Pageable pageable) {
        LOG.debug("Request to get all Opciones");
        return opcionesRepository.findAll(pageable);
    }

    /**
     * Get one opciones by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Opciones> findOne(Long id) {
        LOG.debug("Request to get Opciones : {}", id);
        return opcionesRepository.findById(id);
    }

    /**
     * Delete the opciones by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Opciones : {}", id);
        opcionesRepository.deleteById(id);
    }
}
