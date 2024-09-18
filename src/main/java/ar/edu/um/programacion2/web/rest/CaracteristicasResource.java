package ar.edu.um.programacion2.web.rest;

import ar.edu.um.programacion2.domain.Caracteristicas;
import ar.edu.um.programacion2.repository.CaracteristicasRepository;
import ar.edu.um.programacion2.service.CaracteristicasService;
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
 * REST controller for managing {@link ar.edu.um.programacion2.domain.Caracteristicas}.
 */
@RestController
@RequestMapping("/api/caracteristicas")
public class CaracteristicasResource {

    private static final Logger LOG = LoggerFactory.getLogger(CaracteristicasResource.class);

    private static final String ENTITY_NAME = "caracteristicas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaracteristicasService caracteristicasService;

    private final CaracteristicasRepository caracteristicasRepository;

    public CaracteristicasResource(CaracteristicasService caracteristicasService, CaracteristicasRepository caracteristicasRepository) {
        this.caracteristicasService = caracteristicasService;
        this.caracteristicasRepository = caracteristicasRepository;
    }

    /**
     * {@code POST  /caracteristicas} : Create a new caracteristicas.
     *
     * @param caracteristicas the caracteristicas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new caracteristicas, or with status {@code 400 (Bad Request)} if the caracteristicas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Caracteristicas> createCaracteristicas(@Valid @RequestBody Caracteristicas caracteristicas)
        throws URISyntaxException {
        LOG.debug("REST request to save Caracteristicas : {}", caracteristicas);
        if (caracteristicas.getId() != null) {
            throw new BadRequestAlertException("A new caracteristicas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        caracteristicas = caracteristicasService.save(caracteristicas);
        return ResponseEntity.created(new URI("/api/caracteristicas/" + caracteristicas.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, caracteristicas.getId().toString()))
            .body(caracteristicas);
    }

    /**
     * {@code PUT  /caracteristicas/:id} : Updates an existing caracteristicas.
     *
     * @param id the id of the caracteristicas to save.
     * @param caracteristicas the caracteristicas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caracteristicas,
     * or with status {@code 400 (Bad Request)} if the caracteristicas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the caracteristicas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Caracteristicas> updateCaracteristicas(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Caracteristicas caracteristicas
    ) throws URISyntaxException {
        LOG.debug("REST request to update Caracteristicas : {}, {}", id, caracteristicas);
        if (caracteristicas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caracteristicas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caracteristicasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        caracteristicas = caracteristicasService.update(caracteristicas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caracteristicas.getId().toString()))
            .body(caracteristicas);
    }

    /**
     * {@code PATCH  /caracteristicas/:id} : Partial updates given fields of an existing caracteristicas, field will ignore if it is null
     *
     * @param id the id of the caracteristicas to save.
     * @param caracteristicas the caracteristicas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caracteristicas,
     * or with status {@code 400 (Bad Request)} if the caracteristicas is not valid,
     * or with status {@code 404 (Not Found)} if the caracteristicas is not found,
     * or with status {@code 500 (Internal Server Error)} if the caracteristicas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Caracteristicas> partialUpdateCaracteristicas(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Caracteristicas caracteristicas
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Caracteristicas partially : {}, {}", id, caracteristicas);
        if (caracteristicas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caracteristicas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caracteristicasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Caracteristicas> result = caracteristicasService.partialUpdate(caracteristicas);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caracteristicas.getId().toString())
        );
    }

    /**
     * {@code GET  /caracteristicas} : get all the caracteristicas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of caracteristicas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Caracteristicas>> getAllCaracteristicas(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Caracteristicas");
        Page<Caracteristicas> page = caracteristicasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /caracteristicas/:id} : get the "id" caracteristicas.
     *
     * @param id the id of the caracteristicas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the caracteristicas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Caracteristicas> getCaracteristicas(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Caracteristicas : {}", id);
        Optional<Caracteristicas> caracteristicas = caracteristicasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(caracteristicas);
    }

    /**
     * {@code DELETE  /caracteristicas/:id} : delete the "id" caracteristicas.
     *
     * @param id the id of the caracteristicas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCaracteristicas(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Caracteristicas : {}", id);
        caracteristicasService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
