package ar.edu.um.programacion2.web.rest;

import ar.edu.um.programacion2.domain.Opciones;
import ar.edu.um.programacion2.repository.OpcionesRepository;
import ar.edu.um.programacion2.service.OpcionesService;
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
 * REST controller for managing {@link ar.edu.um.programacion2.domain.Opciones}.
 */
@RestController
@RequestMapping("/api/opciones")
public class OpcionesResource {

    private static final Logger LOG = LoggerFactory.getLogger(OpcionesResource.class);

    private static final String ENTITY_NAME = "opciones";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpcionesService opcionesService;

    private final OpcionesRepository opcionesRepository;

    public OpcionesResource(OpcionesService opcionesService, OpcionesRepository opcionesRepository) {
        this.opcionesService = opcionesService;
        this.opcionesRepository = opcionesRepository;
    }

    /**
     * {@code POST  /opciones} : Create a new opciones.
     *
     * @param opciones the opciones to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opciones, or with status {@code 400 (Bad Request)} if the opciones has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Opciones> createOpciones(@Valid @RequestBody Opciones opciones) throws URISyntaxException {
        LOG.debug("REST request to save Opciones : {}", opciones);
        if (opciones.getId() != null) {
            throw new BadRequestAlertException("A new opciones cannot already have an ID", ENTITY_NAME, "idexists");
        }
        opciones = opcionesService.save(opciones);
        return ResponseEntity.created(new URI("/api/opciones/" + opciones.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, opciones.getId().toString()))
            .body(opciones);
    }

    /**
     * {@code PUT  /opciones/:id} : Updates an existing opciones.
     *
     * @param id the id of the opciones to save.
     * @param opciones the opciones to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opciones,
     * or with status {@code 400 (Bad Request)} if the opciones is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opciones couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Opciones> updateOpciones(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Opciones opciones
    ) throws URISyntaxException {
        LOG.debug("REST request to update Opciones : {}, {}", id, opciones);
        if (opciones.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opciones.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opcionesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        opciones = opcionesService.update(opciones);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opciones.getId().toString()))
            .body(opciones);
    }

    /**
     * {@code PATCH  /opciones/:id} : Partial updates given fields of an existing opciones, field will ignore if it is null
     *
     * @param id the id of the opciones to save.
     * @param opciones the opciones to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opciones,
     * or with status {@code 400 (Bad Request)} if the opciones is not valid,
     * or with status {@code 404 (Not Found)} if the opciones is not found,
     * or with status {@code 500 (Internal Server Error)} if the opciones couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Opciones> partialUpdateOpciones(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Opciones opciones
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Opciones partially : {}, {}", id, opciones);
        if (opciones.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opciones.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opcionesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Opciones> result = opcionesService.partialUpdate(opciones);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opciones.getId().toString())
        );
    }

    /**
     * {@code GET  /opciones} : get all the opciones.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opciones in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Opciones>> getAllOpciones(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Opciones");
        Page<Opciones> page = opcionesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /opciones/:id} : get the "id" opciones.
     *
     * @param id the id of the opciones to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opciones, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Opciones> getOpciones(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Opciones : {}", id);
        Optional<Opciones> opciones = opcionesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(opciones);
    }

    /**
     * {@code DELETE  /opciones/:id} : delete the "id" opciones.
     *
     * @param id the id of the opciones to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpciones(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Opciones : {}", id);
        opcionesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
