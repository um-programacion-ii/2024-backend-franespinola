package ar.edu.um.programacion2.service;

import ar.edu.um.programacion2.domain.Adicionales;
import ar.edu.um.programacion2.repository.AdicionalesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.edu.um.programacion2.domain.Adicionales}.
 */
@Service
@Transactional
public class AdicionalesService {

    private static final Logger LOG = LoggerFactory.getLogger(AdicionalesService.class);

    private final AdicionalesRepository adicionalesRepository;

    public AdicionalesService(AdicionalesRepository adicionalesRepository) {
        this.adicionalesRepository = adicionalesRepository;
    }

    /**
     * Save a adicionales.
     *
     * @param adicionales the entity to save.
     * @return the persisted entity.
     */
    public Adicionales save(Adicionales adicionales) {
        LOG.debug("Request to save Adicionales : {}", adicionales);
        return adicionalesRepository.save(adicionales);
    }

    /**
     * Update a adicionales.
     *
     * @param adicionales the entity to save.
     * @return the persisted entity.
     */
    public Adicionales update(Adicionales adicionales) {
        LOG.debug("Request to update Adicionales : {}", adicionales);
        return adicionalesRepository.save(adicionales);
    }

    /**
     * Partially update a adicionales.
     *
     * @param adicionales the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Adicionales> partialUpdate(Adicionales adicionales) {
        LOG.debug("Request to partially update Adicionales : {}", adicionales);

        return adicionalesRepository
            .findById(adicionales.getId())
            .map(existingAdicionales -> {
                if (adicionales.getNombre() != null) {
                    existingAdicionales.setNombre(adicionales.getNombre());
                }
                if (adicionales.getDescripcion() != null) {
                    existingAdicionales.setDescripcion(adicionales.getDescripcion());
                }
                if (adicionales.getPrecio() != null) {
                    existingAdicionales.setPrecio(adicionales.getPrecio());
                }
                if (adicionales.getPrecioGratis() != null) {
                    existingAdicionales.setPrecioGratis(adicionales.getPrecioGratis());
                }

                return existingAdicionales;
            })
            .map(adicionalesRepository::save);
    }

    /**
     * Get all the adicionales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Adicionales> findAll(Pageable pageable) {
        LOG.debug("Request to get all Adicionales");
        return adicionalesRepository.findAll(pageable);
    }

    /**
     * Get one adicionales by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Adicionales> findOne(Long id) {
        LOG.debug("Request to get Adicionales : {}", id);
        return adicionalesRepository.findById(id);
    }

    /**
     * Delete the adicionales by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Adicionales : {}", id);
        adicionalesRepository.deleteById(id);
    }
}
