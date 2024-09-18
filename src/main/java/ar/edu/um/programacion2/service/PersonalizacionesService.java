package ar.edu.um.programacion2.service;

import ar.edu.um.programacion2.domain.Personalizaciones;
import ar.edu.um.programacion2.repository.PersonalizacionesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.edu.um.programacion2.domain.Personalizaciones}.
 */
@Service
@Transactional
public class PersonalizacionesService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonalizacionesService.class);

    private final PersonalizacionesRepository personalizacionesRepository;

    public PersonalizacionesService(PersonalizacionesRepository personalizacionesRepository) {
        this.personalizacionesRepository = personalizacionesRepository;
    }

    /**
     * Save a personalizaciones.
     *
     * @param personalizaciones the entity to save.
     * @return the persisted entity.
     */
    public Personalizaciones save(Personalizaciones personalizaciones) {
        LOG.debug("Request to save Personalizaciones : {}", personalizaciones);
        return personalizacionesRepository.save(personalizaciones);
    }

    /**
     * Update a personalizaciones.
     *
     * @param personalizaciones the entity to save.
     * @return the persisted entity.
     */
    public Personalizaciones update(Personalizaciones personalizaciones) {
        LOG.debug("Request to update Personalizaciones : {}", personalizaciones);
        return personalizacionesRepository.save(personalizaciones);
    }

    /**
     * Partially update a personalizaciones.
     *
     * @param personalizaciones the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Personalizaciones> partialUpdate(Personalizaciones personalizaciones) {
        LOG.debug("Request to partially update Personalizaciones : {}", personalizaciones);

        return personalizacionesRepository
            .findById(personalizaciones.getId())
            .map(existingPersonalizaciones -> {
                if (personalizaciones.getNombre() != null) {
                    existingPersonalizaciones.setNombre(personalizaciones.getNombre());
                }
                if (personalizaciones.getDescripcion() != null) {
                    existingPersonalizaciones.setDescripcion(personalizaciones.getDescripcion());
                }

                return existingPersonalizaciones;
            })
            .map(personalizacionesRepository::save);
    }

    /**
     * Get all the personalizaciones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Personalizaciones> findAll(Pageable pageable) {
        LOG.debug("Request to get all Personalizaciones");
        return personalizacionesRepository.findAll(pageable);
    }

    /**
     * Get one personalizaciones by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Personalizaciones> findOne(Long id) {
        LOG.debug("Request to get Personalizaciones : {}", id);
        return personalizacionesRepository.findById(id);
    }

    /**
     * Delete the personalizaciones by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Personalizaciones : {}", id);
        personalizacionesRepository.deleteById(id);
    }
}
