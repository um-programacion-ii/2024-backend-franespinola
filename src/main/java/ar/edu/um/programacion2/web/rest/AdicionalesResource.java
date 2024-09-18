package ar.edu.um.programacion2.web.rest;

import ar.edu.um.programacion2.repository.AdicionalesRepository;
import ar.edu.um.programacion2.service.AdicionalesService;
import ar.edu.um.programacion2.service.dto.AdicionalesDTO;
import ar.edu.um.programacion2.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ar.edu.um.programacion2.domain.Adicionales}.
 */
@RestController
@RequestMapping("/api/adicionales")
public class AdicionalesResource {

    private static final Logger LOG = LoggerFactory.getLogger(AdicionalesResource.class);

    private static final String ENTITY_NAME = "adicionales";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdicionalesService adicionalesService;

    private final AdicionalesRepository adicionalesRepository;

    public AdicionalesResource(AdicionalesService adicionalesService, AdicionalesRepository adicionalesRepository) {
        this.adicionalesService = adicionalesService;
        this.adicionalesRepository = adicionalesRepository;
    }

    /**
     * {@code POST  /adicionales} : Create a new adicionales.
     *
     * @param adicionalesDTO the adicionalesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adicionalesDTO, or with status {@code 400 (Bad Request)} if the adicionales has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AdicionalesDTO> createAdicionales(@Valid @RequestBody AdicionalesDTO adicionalesDTO) throws URISyntaxException {
        LOG.debug("REST request to save Adicionales : {}", adicionalesDTO);
        if (adicionalesDTO.getId() != null) {
            throw new BadRequestAlertException("A new adicionales cannot already have an ID", ENTITY_NAME, "idexists");
        }
        adicionalesDTO = adicionalesService.save(adicionalesDTO);
        return ResponseEntity.created(new URI("/api/adicionales/" + adicionalesDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, adicionalesDTO.getId().toString()))
            .body(adicionalesDTO);
    }

    /**
     * {@code PUT  /adicionales/:id} : Updates an existing adicionales.
     *
     * @param id the id of the adicionalesDTO to save.
     * @param adicionalesDTO the adicionalesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adicionalesDTO,
     * or with status {@code 400 (Bad Request)} if the adicionalesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adicionalesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdicionalesDTO> updateAdicionales(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdicionalesDTO adicionalesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Adicionales : {}, {}", id, adicionalesDTO);
        if (adicionalesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adicionalesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adicionalesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        adicionalesDTO = adicionalesService.update(adicionalesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adicionalesDTO.getId().toString()))
            .body(adicionalesDTO);
    }

    /**
     * {@code PATCH  /adicionales/:id} : Partial updates given fields of an existing adicionales, field will ignore if it is null
     *
     * @param id the id of the adicionalesDTO to save.
     * @param adicionalesDTO the adicionalesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adicionalesDTO,
     * or with status {@code 400 (Bad Request)} if the adicionalesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adicionalesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adicionalesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdicionalesDTO> partialUpdateAdicionales(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdicionalesDTO adicionalesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Adicionales partially : {}, {}", id, adicionalesDTO);
        if (adicionalesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adicionalesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adicionalesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdicionalesDTO> result = adicionalesService.partialUpdate(adicionalesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adicionalesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /adicionales} : get all the adicionales.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adicionales in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AdicionalesDTO>> getAllAdicionales(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Adicionales");
        Page<AdicionalesDTO> page = adicionalesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /adicionales/:id} : get the "id" adicionales.
     *
     * @param id the id of the adicionalesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adicionalesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdicionalesDTO> getAdicionales(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Adicionales : {}", id);
        Optional<AdicionalesDTO> adicionalesDTO = adicionalesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adicionalesDTO);
    }

    /**
     * {@code DELETE  /adicionales/:id} : delete the "id" adicionales.
     *
     * @param id the id of the adicionalesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdicionales(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Adicionales : {}", id);
        adicionalesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
