package ar.edu.um.programacion2.service;

import ar.edu.um.programacion2.domain.Dispositivos;
import ar.edu.um.programacion2.repository.DispositivosRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.edu.um.programacion2.domain.Dispositivos}.
 */
@Service
@Transactional
public class DispositivosService {

    private static final Logger LOG = LoggerFactory.getLogger(DispositivosService.class);

    private final DispositivosRepository dispositivosRepository;

    public DispositivosService(DispositivosRepository dispositivosRepository) {
        this.dispositivosRepository = dispositivosRepository;
    }

    /**
     * Save a dispositivos.
     *
     * @param dispositivos the entity to save.
     * @return the persisted entity.
     */
    public Dispositivos save(Dispositivos dispositivos) {
        LOG.debug("Request to save Dispositivos : {}", dispositivos);
        return dispositivosRepository.save(dispositivos);
    }

    /**
     * Update a dispositivos.
     *
     * @param dispositivos the entity to save.
     * @return the persisted entity.
     */
    public Dispositivos update(Dispositivos dispositivos) {
        LOG.debug("Request to update Dispositivos : {}", dispositivos);
        return dispositivosRepository.save(dispositivos);
    }

    /**
     * Partially update a dispositivos.
     *
     * @param dispositivos the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Dispositivos> partialUpdate(Dispositivos dispositivos) {
        LOG.debug("Request to partially update Dispositivos : {}", dispositivos);

        return dispositivosRepository
            .findById(dispositivos.getId())
            .map(existingDispositivos -> {
                if (dispositivos.getCodigo() != null) {
                    existingDispositivos.setCodigo(dispositivos.getCodigo());
                }
                if (dispositivos.getNombre() != null) {
                    existingDispositivos.setNombre(dispositivos.getNombre());
                }
                if (dispositivos.getDescripcion() != null) {
                    existingDispositivos.setDescripcion(dispositivos.getDescripcion());
                }
                if (dispositivos.getPrecioBase() != null) {
                    existingDispositivos.setPrecioBase(dispositivos.getPrecioBase());
                }
                if (dispositivos.getMoneda() != null) {
                    existingDispositivos.setMoneda(dispositivos.getMoneda());
                }

                return existingDispositivos;
            })
            .map(dispositivosRepository::save);
    }

    /**
     * Get all the dispositivos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Dispositivos> findAll(Pageable pageable) {
        LOG.debug("Request to get all Dispositivos");
        return dispositivosRepository.findAll(pageable);
    }

    /**
     * Get one dispositivos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Dispositivos> findOne(Long id) {
        LOG.debug("Request to get Dispositivos : {}", id);
        return dispositivosRepository.findById(id);
    }

    /**
     * Delete the dispositivos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Dispositivos : {}", id);
        dispositivosRepository.deleteById(id);
    }
}
