package ar.edu.um.programacion2.service;

import ar.edu.um.programacion2.domain.Dispositivo;
import ar.edu.um.programacion2.repository.DispositivoRepository;
import ar.edu.um.programacion2.service.dto.DispositivoDTO;
import ar.edu.um.programacion2.service.mapper.DispositivoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.edu.um.programacion2.domain.Dispositivo}.
 */
@Service
@Transactional
public class DispositivoService {

    private static final Logger LOG = LoggerFactory.getLogger(DispositivoService.class);

    private final DispositivoRepository dispositivoRepository;

    private final DispositivoMapper dispositivoMapper;

    public DispositivoService(DispositivoRepository dispositivoRepository, DispositivoMapper dispositivoMapper) {
        this.dispositivoRepository = dispositivoRepository;
        this.dispositivoMapper = dispositivoMapper;
    }

    /**
     * Save a dispositivo.
     *
     * @param dispositivoDTO the entity to save.
     * @return the persisted entity.
     */
    public DispositivoDTO save(DispositivoDTO dispositivoDTO) {
        LOG.debug("Request to save Dispositivo : {}", dispositivoDTO);
        Dispositivo dispositivo = dispositivoMapper.toEntity(dispositivoDTO);
        dispositivo = dispositivoRepository.save(dispositivo);
        return dispositivoMapper.toDto(dispositivo);
    }

    /**
     * Update a dispositivo.
     *
     * @param dispositivoDTO the entity to save.
     * @return the persisted entity.
     */
    public DispositivoDTO update(DispositivoDTO dispositivoDTO) {
        LOG.debug("Request to update Dispositivo : {}", dispositivoDTO);
        Dispositivo dispositivo = dispositivoMapper.toEntity(dispositivoDTO);
        dispositivo = dispositivoRepository.save(dispositivo);
        return dispositivoMapper.toDto(dispositivo);
    }

    /**
     * Partially update a dispositivo.
     *
     * @param dispositivoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DispositivoDTO> partialUpdate(DispositivoDTO dispositivoDTO) {
        LOG.debug("Request to partially update Dispositivo : {}", dispositivoDTO);

        return dispositivoRepository
            .findById(dispositivoDTO.getId())
            .map(existingDispositivo -> {
                dispositivoMapper.partialUpdate(existingDispositivo, dispositivoDTO);

                return existingDispositivo;
            })
            .map(dispositivoRepository::save)
            .map(dispositivoMapper::toDto);
    }

    /**
     * Get all the dispositivos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DispositivoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Dispositivos");
        return dispositivoRepository.findAll(pageable).map(dispositivoMapper::toDto);
    }

    /**
     * Get one dispositivo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DispositivoDTO> findOne(Long id) {
        LOG.debug("Request to get Dispositivo : {}", id);
        return dispositivoRepository.findById(id).map(dispositivoMapper::toDto);
    }

    /**
     * Delete the dispositivo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Dispositivo : {}", id);
        dispositivoRepository.deleteById(id);
    }
}
