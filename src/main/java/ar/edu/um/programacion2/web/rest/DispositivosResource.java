package ar.edu.um.programacion2.web.rest;

import ar.edu.um.programacion2.domain.Dispositivos;
import ar.edu.um.programacion2.repository.DispositivosRepository;
import ar.edu.um.programacion2.service.DispositivosService;
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
 * REST controller for managing {@link ar.edu.um.programacion2.domain.Dispositivos}.
 */
@RestController
@RequestMapping("/api/dispositivos")
public class DispositivosResource {

    private static final Logger LOG = LoggerFactory.getLogger(DispositivosResource.class);

    private static final String ENTITY_NAME = "dispositivos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DispositivosService dispositivosService;

    private final DispositivosRepository dispositivosRepository;

    public DispositivosResource(DispositivosService dispositivosService, DispositivosRepository dispositivosRepository) {
        this.dispositivosService = dispositivosService;
        this.dispositivosRepository = dispositivosRepository;
    }

    /**
     * {@code POST  /dispositivos} : Create a new dispositivos.
     *
     * @param dispositivos the dispositivos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dispositivos, or with status {@code 400 (Bad Request)} if the dispositivos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Dispositivos> createDispositivos(@Valid @RequestBody Dispositivos dispositivos) throws URISyntaxException {
        LOG.debug("REST request to save Dispositivos : {}", dispositivos);
        if (dispositivos.getId() != null) {
            throw new BadRequestAlertException("A new dispositivos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dispositivos = dispositivosService.save(dispositivos);
        return ResponseEntity.created(new URI("/api/dispositivos/" + dispositivos.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dispositivos.getId().toString()))
            .body(dispositivos);
    }

    /**
     * {@code PUT  /dispositivos/:id} : Updates an existing dispositivos.
     *
     * @param id the id of the dispositivos to save.
     * @param dispositivos the dispositivos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispositivos,
     * or with status {@code 400 (Bad Request)} if the dispositivos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dispositivos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Dispositivos> updateDispositivos(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Dispositivos dispositivos
    ) throws URISyntaxException {
        LOG.debug("REST request to update Dispositivos : {}, {}", id, dispositivos);
        if (dispositivos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dispositivos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispositivosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        dispositivos = dispositivosService.update(dispositivos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dispositivos.getId().toString()))
            .body(dispositivos);
    }

    /**
     * {@code PATCH  /dispositivos/:id} : Partial updates given fields of an existing dispositivos, field will ignore if it is null
     *
     * @param id the id of the dispositivos to save.
     * @param dispositivos the dispositivos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispositivos,
     * or with status {@code 400 (Bad Request)} if the dispositivos is not valid,
     * or with status {@code 404 (Not Found)} if the dispositivos is not found,
     * or with status {@code 500 (Internal Server Error)} if the dispositivos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Dispositivos> partialUpdateDispositivos(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Dispositivos dispositivos
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Dispositivos partially : {}, {}", id, dispositivos);
        if (dispositivos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dispositivos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispositivosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Dispositivos> result = dispositivosService.partialUpdate(dispositivos);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dispositivos.getId().toString())
        );
    }

    /**
     * {@code GET  /dispositivos} : get all the dispositivos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dispositivos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Dispositivos>> getAllDispositivos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Dispositivos");
        Page<Dispositivos> page = dispositivosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dispositivos/:id} : get the "id" dispositivos.
     *
     * @param id the id of the dispositivos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dispositivos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Dispositivos> getDispositivos(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Dispositivos : {}", id);
        Optional<Dispositivos> dispositivos = dispositivosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dispositivos);
    }

    /**
     * {@code DELETE  /dispositivos/:id} : delete the "id" dispositivos.
     *
     * @param id the id of the dispositivos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDispositivos(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Dispositivos : {}", id);
        dispositivosService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
