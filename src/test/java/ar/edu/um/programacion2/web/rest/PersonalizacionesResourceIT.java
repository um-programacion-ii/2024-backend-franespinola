package ar.edu.um.programacion2.web.rest;

import static ar.edu.um.programacion2.domain.PersonalizacionesAsserts.*;
import static ar.edu.um.programacion2.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.um.programacion2.IntegrationTest;
import ar.edu.um.programacion2.domain.Personalizaciones;
import ar.edu.um.programacion2.repository.PersonalizacionesRepository;
import ar.edu.um.programacion2.service.dto.PersonalizacionesDTO;
import ar.edu.um.programacion2.service.mapper.PersonalizacionesMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PersonalizacionesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonalizacionesResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/personalizaciones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PersonalizacionesRepository personalizacionesRepository;

    @Autowired
    private PersonalizacionesMapper personalizacionesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonalizacionesMockMvc;

    private Personalizaciones personalizaciones;

    private Personalizaciones insertedPersonalizaciones;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personalizaciones createEntity() {
        return new Personalizaciones().nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personalizaciones createUpdatedEntity() {
        return new Personalizaciones().nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
    }

    @BeforeEach
    public void initTest() {
        personalizaciones = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPersonalizaciones != null) {
            personalizacionesRepository.delete(insertedPersonalizaciones);
            insertedPersonalizaciones = null;
        }
    }

    @Test
    @Transactional
    void createPersonalizaciones() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Personalizaciones
        PersonalizacionesDTO personalizacionesDTO = personalizacionesMapper.toDto(personalizaciones);
        var returnedPersonalizacionesDTO = om.readValue(
            restPersonalizacionesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personalizacionesDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PersonalizacionesDTO.class
        );

        // Validate the Personalizaciones in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPersonalizaciones = personalizacionesMapper.toEntity(returnedPersonalizacionesDTO);
        assertPersonalizacionesUpdatableFieldsEquals(returnedPersonalizaciones, getPersistedPersonalizaciones(returnedPersonalizaciones));

        insertedPersonalizaciones = returnedPersonalizaciones;
    }

    @Test
    @Transactional
    void createPersonalizacionesWithExistingId() throws Exception {
        // Create the Personalizaciones with an existing ID
        personalizaciones.setId(1L);
        PersonalizacionesDTO personalizacionesDTO = personalizacionesMapper.toDto(personalizaciones);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalizacionesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personalizacionesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Personalizaciones in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        personalizaciones.setNombre(null);

        // Create the Personalizaciones, which fails.
        PersonalizacionesDTO personalizacionesDTO = personalizacionesMapper.toDto(personalizaciones);

        restPersonalizacionesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personalizacionesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        personalizaciones.setDescripcion(null);

        // Create the Personalizaciones, which fails.
        PersonalizacionesDTO personalizacionesDTO = personalizacionesMapper.toDto(personalizaciones);

        restPersonalizacionesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personalizacionesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPersonalizaciones() throws Exception {
        // Initialize the database
        insertedPersonalizaciones = personalizacionesRepository.saveAndFlush(personalizaciones);

        // Get all the personalizacionesList
        restPersonalizacionesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personalizaciones.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getPersonalizaciones() throws Exception {
        // Initialize the database
        insertedPersonalizaciones = personalizacionesRepository.saveAndFlush(personalizaciones);

        // Get the personalizaciones
        restPersonalizacionesMockMvc
            .perform(get(ENTITY_API_URL_ID, personalizaciones.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personalizaciones.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingPersonalizaciones() throws Exception {
        // Get the personalizaciones
        restPersonalizacionesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPersonalizaciones() throws Exception {
        // Initialize the database
        insertedPersonalizaciones = personalizacionesRepository.saveAndFlush(personalizaciones);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the personalizaciones
        Personalizaciones updatedPersonalizaciones = personalizacionesRepository.findById(personalizaciones.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPersonalizaciones are not directly saved in db
        em.detach(updatedPersonalizaciones);
        updatedPersonalizaciones.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        PersonalizacionesDTO personalizacionesDTO = personalizacionesMapper.toDto(updatedPersonalizaciones);

        restPersonalizacionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personalizacionesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(personalizacionesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Personalizaciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPersonalizacionesToMatchAllProperties(updatedPersonalizaciones);
    }

    @Test
    @Transactional
    void putNonExistingPersonalizaciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        personalizaciones.setId(longCount.incrementAndGet());

        // Create the Personalizaciones
        PersonalizacionesDTO personalizacionesDTO = personalizacionesMapper.toDto(personalizaciones);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalizacionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personalizacionesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(personalizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personalizaciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonalizaciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        personalizaciones.setId(longCount.incrementAndGet());

        // Create the Personalizaciones
        PersonalizacionesDTO personalizacionesDTO = personalizacionesMapper.toDto(personalizaciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalizacionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(personalizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personalizaciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonalizaciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        personalizaciones.setId(longCount.incrementAndGet());

        // Create the Personalizaciones
        PersonalizacionesDTO personalizacionesDTO = personalizacionesMapper.toDto(personalizaciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalizacionesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personalizacionesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Personalizaciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonalizacionesWithPatch() throws Exception {
        // Initialize the database
        insertedPersonalizaciones = personalizacionesRepository.saveAndFlush(personalizaciones);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the personalizaciones using partial update
        Personalizaciones partialUpdatedPersonalizaciones = new Personalizaciones();
        partialUpdatedPersonalizaciones.setId(personalizaciones.getId());

        partialUpdatedPersonalizaciones.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restPersonalizacionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonalizaciones.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPersonalizaciones))
            )
            .andExpect(status().isOk());

        // Validate the Personalizaciones in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersonalizacionesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPersonalizaciones, personalizaciones),
            getPersistedPersonalizaciones(personalizaciones)
        );
    }

    @Test
    @Transactional
    void fullUpdatePersonalizacionesWithPatch() throws Exception {
        // Initialize the database
        insertedPersonalizaciones = personalizacionesRepository.saveAndFlush(personalizaciones);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the personalizaciones using partial update
        Personalizaciones partialUpdatedPersonalizaciones = new Personalizaciones();
        partialUpdatedPersonalizaciones.setId(personalizaciones.getId());

        partialUpdatedPersonalizaciones.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restPersonalizacionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonalizaciones.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPersonalizaciones))
            )
            .andExpect(status().isOk());

        // Validate the Personalizaciones in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersonalizacionesUpdatableFieldsEquals(
            partialUpdatedPersonalizaciones,
            getPersistedPersonalizaciones(partialUpdatedPersonalizaciones)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPersonalizaciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        personalizaciones.setId(longCount.incrementAndGet());

        // Create the Personalizaciones
        PersonalizacionesDTO personalizacionesDTO = personalizacionesMapper.toDto(personalizaciones);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalizacionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personalizacionesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(personalizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personalizaciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonalizaciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        personalizaciones.setId(longCount.incrementAndGet());

        // Create the Personalizaciones
        PersonalizacionesDTO personalizacionesDTO = personalizacionesMapper.toDto(personalizaciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalizacionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(personalizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personalizaciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonalizaciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        personalizaciones.setId(longCount.incrementAndGet());

        // Create the Personalizaciones
        PersonalizacionesDTO personalizacionesDTO = personalizacionesMapper.toDto(personalizaciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalizacionesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(personalizacionesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Personalizaciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonalizaciones() throws Exception {
        // Initialize the database
        insertedPersonalizaciones = personalizacionesRepository.saveAndFlush(personalizaciones);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the personalizaciones
        restPersonalizacionesMockMvc
            .perform(delete(ENTITY_API_URL_ID, personalizaciones.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return personalizacionesRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Personalizaciones getPersistedPersonalizaciones(Personalizaciones personalizaciones) {
        return personalizacionesRepository.findById(personalizaciones.getId()).orElseThrow();
    }

    protected void assertPersistedPersonalizacionesToMatchAllProperties(Personalizaciones expectedPersonalizaciones) {
        assertPersonalizacionesAllPropertiesEquals(expectedPersonalizaciones, getPersistedPersonalizaciones(expectedPersonalizaciones));
    }

    protected void assertPersistedPersonalizacionesToMatchUpdatableProperties(Personalizaciones expectedPersonalizaciones) {
        assertPersonalizacionesAllUpdatablePropertiesEquals(
            expectedPersonalizaciones,
            getPersistedPersonalizaciones(expectedPersonalizaciones)
        );
    }
}
