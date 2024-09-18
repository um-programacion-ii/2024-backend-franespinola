package ar.edu.um.programacion2.web.rest;

import static ar.edu.um.programacion2.domain.CaracteristicasAsserts.*;
import static ar.edu.um.programacion2.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.um.programacion2.IntegrationTest;
import ar.edu.um.programacion2.domain.Caracteristicas;
import ar.edu.um.programacion2.domain.Dispositivos;
import ar.edu.um.programacion2.repository.CaracteristicasRepository;
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
 * Integration tests for the {@link CaracteristicasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CaracteristicasResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/caracteristicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CaracteristicasRepository caracteristicasRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCaracteristicasMockMvc;

    private Caracteristicas caracteristicas;

    private Caracteristicas insertedCaracteristicas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caracteristicas createEntity(EntityManager em) {
        Caracteristicas caracteristicas = new Caracteristicas().nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION);
        // Add required entity
        Dispositivos dispositivos;
        if (TestUtil.findAll(em, Dispositivos.class).isEmpty()) {
            dispositivos = DispositivosResourceIT.createEntity();
            em.persist(dispositivos);
            em.flush();
        } else {
            dispositivos = TestUtil.findAll(em, Dispositivos.class).get(0);
        }
        caracteristicas.setDispositivo(dispositivos);
        return caracteristicas;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caracteristicas createUpdatedEntity(EntityManager em) {
        Caracteristicas updatedCaracteristicas = new Caracteristicas().nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        // Add required entity
        Dispositivos dispositivos;
        if (TestUtil.findAll(em, Dispositivos.class).isEmpty()) {
            dispositivos = DispositivosResourceIT.createUpdatedEntity();
            em.persist(dispositivos);
            em.flush();
        } else {
            dispositivos = TestUtil.findAll(em, Dispositivos.class).get(0);
        }
        updatedCaracteristicas.setDispositivo(dispositivos);
        return updatedCaracteristicas;
    }

    @BeforeEach
    public void initTest() {
        caracteristicas = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCaracteristicas != null) {
            caracteristicasRepository.delete(insertedCaracteristicas);
            insertedCaracteristicas = null;
        }
    }

    @Test
    @Transactional
    void createCaracteristicas() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Caracteristicas
        var returnedCaracteristicas = om.readValue(
            restCaracteristicasMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(caracteristicas)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Caracteristicas.class
        );

        // Validate the Caracteristicas in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCaracteristicasUpdatableFieldsEquals(returnedCaracteristicas, getPersistedCaracteristicas(returnedCaracteristicas));

        insertedCaracteristicas = returnedCaracteristicas;
    }

    @Test
    @Transactional
    void createCaracteristicasWithExistingId() throws Exception {
        // Create the Caracteristicas with an existing ID
        caracteristicas.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaracteristicasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(caracteristicas)))
            .andExpect(status().isBadRequest());

        // Validate the Caracteristicas in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        caracteristicas.setNombre(null);

        // Create the Caracteristicas, which fails.

        restCaracteristicasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(caracteristicas)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        caracteristicas.setDescripcion(null);

        // Create the Caracteristicas, which fails.

        restCaracteristicasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(caracteristicas)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCaracteristicas() throws Exception {
        // Initialize the database
        insertedCaracteristicas = caracteristicasRepository.saveAndFlush(caracteristicas);

        // Get all the caracteristicasList
        restCaracteristicasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caracteristicas.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getCaracteristicas() throws Exception {
        // Initialize the database
        insertedCaracteristicas = caracteristicasRepository.saveAndFlush(caracteristicas);

        // Get the caracteristicas
        restCaracteristicasMockMvc
            .perform(get(ENTITY_API_URL_ID, caracteristicas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(caracteristicas.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingCaracteristicas() throws Exception {
        // Get the caracteristicas
        restCaracteristicasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCaracteristicas() throws Exception {
        // Initialize the database
        insertedCaracteristicas = caracteristicasRepository.saveAndFlush(caracteristicas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the caracteristicas
        Caracteristicas updatedCaracteristicas = caracteristicasRepository.findById(caracteristicas.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCaracteristicas are not directly saved in db
        em.detach(updatedCaracteristicas);
        updatedCaracteristicas.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restCaracteristicasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCaracteristicas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCaracteristicas))
            )
            .andExpect(status().isOk());

        // Validate the Caracteristicas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCaracteristicasToMatchAllProperties(updatedCaracteristicas);
    }

    @Test
    @Transactional
    void putNonExistingCaracteristicas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caracteristicas.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaracteristicasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, caracteristicas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(caracteristicas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caracteristicas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCaracteristicas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caracteristicas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaracteristicasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(caracteristicas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caracteristicas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCaracteristicas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caracteristicas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaracteristicasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(caracteristicas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Caracteristicas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCaracteristicasWithPatch() throws Exception {
        // Initialize the database
        insertedCaracteristicas = caracteristicasRepository.saveAndFlush(caracteristicas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the caracteristicas using partial update
        Caracteristicas partialUpdatedCaracteristicas = new Caracteristicas();
        partialUpdatedCaracteristicas.setId(caracteristicas.getId());

        partialUpdatedCaracteristicas.nombre(UPDATED_NOMBRE);

        restCaracteristicasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaracteristicas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCaracteristicas))
            )
            .andExpect(status().isOk());

        // Validate the Caracteristicas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCaracteristicasUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCaracteristicas, caracteristicas),
            getPersistedCaracteristicas(caracteristicas)
        );
    }

    @Test
    @Transactional
    void fullUpdateCaracteristicasWithPatch() throws Exception {
        // Initialize the database
        insertedCaracteristicas = caracteristicasRepository.saveAndFlush(caracteristicas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the caracteristicas using partial update
        Caracteristicas partialUpdatedCaracteristicas = new Caracteristicas();
        partialUpdatedCaracteristicas.setId(caracteristicas.getId());

        partialUpdatedCaracteristicas.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restCaracteristicasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaracteristicas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCaracteristicas))
            )
            .andExpect(status().isOk());

        // Validate the Caracteristicas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCaracteristicasUpdatableFieldsEquals(
            partialUpdatedCaracteristicas,
            getPersistedCaracteristicas(partialUpdatedCaracteristicas)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCaracteristicas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caracteristicas.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaracteristicasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, caracteristicas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(caracteristicas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caracteristicas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCaracteristicas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caracteristicas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaracteristicasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(caracteristicas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caracteristicas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCaracteristicas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caracteristicas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaracteristicasMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(caracteristicas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Caracteristicas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCaracteristicas() throws Exception {
        // Initialize the database
        insertedCaracteristicas = caracteristicasRepository.saveAndFlush(caracteristicas);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the caracteristicas
        restCaracteristicasMockMvc
            .perform(delete(ENTITY_API_URL_ID, caracteristicas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return caracteristicasRepository.count();
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

    protected Caracteristicas getPersistedCaracteristicas(Caracteristicas caracteristicas) {
        return caracteristicasRepository.findById(caracteristicas.getId()).orElseThrow();
    }

    protected void assertPersistedCaracteristicasToMatchAllProperties(Caracteristicas expectedCaracteristicas) {
        assertCaracteristicasAllPropertiesEquals(expectedCaracteristicas, getPersistedCaracteristicas(expectedCaracteristicas));
    }

    protected void assertPersistedCaracteristicasToMatchUpdatableProperties(Caracteristicas expectedCaracteristicas) {
        assertCaracteristicasAllUpdatablePropertiesEquals(expectedCaracteristicas, getPersistedCaracteristicas(expectedCaracteristicas));
    }
}
