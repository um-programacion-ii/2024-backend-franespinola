package ar.edu.um.programacion2.service;

import ar.edu.um.programacion2.domain.Caracteristicas;
import ar.edu.um.programacion2.repository.CaracteristicasRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.edu.um.programacion2.domain.Caracteristicas}.
 */
@Service
@Transactional
public class CaracteristicasService {

    private static final Logger LOG = LoggerFactory.getLogger(CaracteristicasService.class);

    private final CaracteristicasRepository caracteristicasRepository;

    public CaracteristicasService(CaracteristicasRepository caracteristicasRepository) {
        this.caracteristicasRepository = caracteristicasRepository;
    }

    /**
     * Save a caracteristicas.
     *
     * @param caracteristicas the entity to save.
     * @return the persisted entity.
     */
    public Caracteristicas save(Caracteristicas caracteristicas) {
        LOG.debug("Request to save Caracteristicas : {}", caracteristicas);
        return caracteristicasRepository.save(caracteristicas);
    }

    /**
     * Update a caracteristicas.
     *
     * @param caracteristicas the entity to save.
     * @return the persisted entity.
     */
    public Caracteristicas update(Caracteristicas caracteristicas) {
        LOG.debug("Request to update Caracteristicas : {}", caracteristicas);
        return caracteristicasRepository.save(caracteristicas);
    }

    /**
     * Partially update a caracteristicas.
     *
     * @param caracteristicas the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Caracteristicas> partialUpdate(Caracteristicas caracteristicas) {
        LOG.debug("Request to partially update Caracteristicas : {}", caracteristicas);

        return caracteristicasRepository
            .findById(caracteristicas.getId())
            .map(existingCaracteristicas -> {
                if (caracteristicas.getNombre() != null) {
                    existingCaracteristicas.setNombre(caracteristicas.getNombre());
                }
                if (caracteristicas.getDescripcion() != null) {
                    existingCaracteristicas.setDescripcion(caracteristicas.getDescripcion());
                }

                return existingCaracteristicas;
            })
            .map(caracteristicasRepository::save);
    }

    /**
     * Get all the caracteristicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Caracteristicas> findAll(Pageable pageable) {
        LOG.debug("Request to get all Caracteristicas");
        return caracteristicasRepository.findAll(pageable);
    }

    /**
     * Get one caracteristicas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Caracteristicas> findOne(Long id) {
        LOG.debug("Request to get Caracteristicas : {}", id);
        return caracteristicasRepository.findById(id);
    }

    /**
     * Delete the caracteristicas by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Caracteristicas : {}", id);
        caracteristicasRepository.deleteById(id);
    }
}
