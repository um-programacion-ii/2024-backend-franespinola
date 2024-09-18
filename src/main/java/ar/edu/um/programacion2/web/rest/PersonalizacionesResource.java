package ar.edu.um.programacion2.web.rest;

import ar.edu.um.programacion2.domain.Personalizaciones;
import ar.edu.um.programacion2.repository.PersonalizacionesRepository;
import ar.edu.um.programacion2.service.PersonalizacionesService;
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
 * REST controller for managing {@link ar.edu.um.programacion2.domain.Personalizaciones}.
 */
@RestController
@RequestMapping("/api/personalizaciones")
public class PersonalizacionesResource {

    private static final Logger LOG = LoggerFactory.getLogger(PersonalizacionesResource.class);

    private static final String ENTITY_NAME = "personalizaciones";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonalizacionesService personalizacionesService;

    private final PersonalizacionesRepository personalizacionesRepository;

    public PersonalizacionesResource(
        PersonalizacionesService personalizacionesService,
        PersonalizacionesRepository personalizacionesRepository
    ) {
        this.personalizacionesService = personalizacionesService;
        this.personalizacionesRepository = personalizacionesRepository;
    }

    /**
     * {@code POST  /personalizaciones} : Create a new personalizaciones.
     *
     * @param personalizaciones the personalizaciones to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personalizaciones, or with status {@code 400 (Bad Request)} if the personalizaciones has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Personalizaciones> createPersonalizaciones(@Valid @RequestBody Personalizaciones personalizaciones)
        throws URISyntaxException {
        LOG.debug("REST request to save Personalizaciones : {}", personalizaciones);
        if (personalizaciones.getId() != null) {
            throw new BadRequestAlertException("A new personalizaciones cannot already have an ID", ENTITY_NAME, "idexists");
        }
        personalizaciones = personalizacionesService.save(personalizaciones);
        return ResponseEntity.created(new URI("/api/personalizaciones/" + personalizaciones.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, personalizaciones.getId().toString()))
            .body(personalizaciones);
    }

    /**
     * {@code PUT  /personalizaciones/:id} : Updates an existing personalizaciones.
     *
     * @param id the id of the personalizaciones to save.
     * @param personalizaciones the personalizaciones to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personalizaciones,
     * or with status {@code 400 (Bad Request)} if the personalizaciones is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personalizaciones couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Personalizaciones> updatePersonalizaciones(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Personalizaciones personalizaciones
    ) throws URISyntaxException {
        LOG.debug("REST request to update Personalizaciones : {}, {}", id, personalizaciones);
        if (personalizaciones.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personalizaciones.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personalizacionesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        personalizaciones = personalizacionesService.update(personalizaciones);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personalizaciones.getId().toString()))
            .body(personalizaciones);
    }

    /**
     * {@code PATCH  /personalizaciones/:id} : Partial updates given fields of an existing personalizaciones, field will ignore if it is null
     *
     * @param id the id of the personalizaciones to save.
     * @param personalizaciones the personalizaciones to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personalizaciones,
     * or with status {@code 400 (Bad Request)} if the personalizaciones is not valid,
     * or with status {@code 404 (Not Found)} if the personalizaciones is not found,
     * or with status {@code 500 (Internal Server Error)} if the personalizaciones couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Personalizaciones> partialUpdatePersonalizaciones(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Personalizaciones personalizaciones
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Personalizaciones partially : {}, {}", id, personalizaciones);
        if (personalizaciones.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personalizaciones.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personalizacionesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Personalizaciones> result = personalizacionesService.partialUpdate(personalizaciones);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personalizaciones.getId().toString())
        );
    }

    /**
     * {@code GET  /personalizaciones} : get all the personalizaciones.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personalizaciones in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Personalizaciones>> getAllPersonalizaciones(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of Personalizaciones");
        Page<Personalizaciones> page = personalizacionesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /personalizaciones/:id} : get the "id" personalizaciones.
     *
     * @param id the id of the personalizaciones to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personalizaciones, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Personalizaciones> getPersonalizaciones(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Personalizaciones : {}", id);
        Optional<Personalizaciones> personalizaciones = personalizacionesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personalizaciones);
    }

    /**
     * {@code DELETE  /personalizaciones/:id} : delete the "id" personalizaciones.
     *
     * @param id the id of the personalizaciones to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonalizaciones(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Personalizaciones : {}", id);
        personalizacionesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
