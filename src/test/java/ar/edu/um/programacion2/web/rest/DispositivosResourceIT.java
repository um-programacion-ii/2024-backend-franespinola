package ar.edu.um.programacion2.web.rest;

import static ar.edu.um.programacion2.domain.DispositivosAsserts.*;
import static ar.edu.um.programacion2.web.rest.TestUtil.createUpdateProxyForBean;
import static ar.edu.um.programacion2.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.um.programacion2.IntegrationTest;
import ar.edu.um.programacion2.domain.Dispositivos;
import ar.edu.um.programacion2.repository.DispositivosRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link DispositivosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DispositivosResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRECIO_BASE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRECIO_BASE = new BigDecimal(1);

    private static final String DEFAULT_MONEDA = "AAAAAAAAAA";
    private static final String UPDATED_MONEDA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dispositivos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DispositivosRepository dispositivosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDispositivosMockMvc;

    private Dispositivos dispositivos;

    private Dispositivos insertedDispositivos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dispositivos createEntity() {
        return new Dispositivos()
            .codigo(DEFAULT_CODIGO)
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .precioBase(DEFAULT_PRECIO_BASE)
            .moneda(DEFAULT_MONEDA);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dispositivos createUpdatedEntity() {
        return new Dispositivos()
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .precioBase(UPDATED_PRECIO_BASE)
            .moneda(UPDATED_MONEDA);
    }

    @BeforeEach
    public void initTest() {
        dispositivos = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDispositivos != null) {
            dispositivosRepository.delete(insertedDispositivos);
            insertedDispositivos = null;
        }
    }

    @Test
    @Transactional
    void createDispositivos() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Dispositivos
        var returnedDispositivos = om.readValue(
            restDispositivosMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dispositivos)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Dispositivos.class
        );

        // Validate the Dispositivos in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDispositivosUpdatableFieldsEquals(returnedDispositivos, getPersistedDispositivos(returnedDispositivos));

        insertedDispositivos = returnedDispositivos;
    }

    @Test
    @Transactional
    void createDispositivosWithExistingId() throws Exception {
        // Create the Dispositivos with an existing ID
        dispositivos.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDispositivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dispositivos)))
            .andExpect(status().isBadRequest());

        // Validate the Dispositivos in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dispositivos.setCodigo(null);

        // Create the Dispositivos, which fails.

        restDispositivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dispositivos)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dispositivos.setNombre(null);

        // Create the Dispositivos, which fails.

        restDispositivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dispositivos)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dispositivos.setDescripcion(null);

        // Create the Dispositivos, which fails.

        restDispositivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dispositivos)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrecioBaseIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dispositivos.setPrecioBase(null);

        // Create the Dispositivos, which fails.

        restDispositivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dispositivos)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMonedaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dispositivos.setMoneda(null);

        // Create the Dispositivos, which fails.

        restDispositivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dispositivos)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDispositivos() throws Exception {
        // Initialize the database
        insertedDispositivos = dispositivosRepository.saveAndFlush(dispositivos);

        // Get all the dispositivosList
        restDispositivosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dispositivos.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].precioBase").value(hasItem(sameNumber(DEFAULT_PRECIO_BASE))))
            .andExpect(jsonPath("$.[*].moneda").value(hasItem(DEFAULT_MONEDA)));
    }

    @Test
    @Transactional
    void getDispositivos() throws Exception {
        // Initialize the database
        insertedDispositivos = dispositivosRepository.saveAndFlush(dispositivos);

        // Get the dispositivos
        restDispositivosMockMvc
            .perform(get(ENTITY_API_URL_ID, dispositivos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dispositivos.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.precioBase").value(sameNumber(DEFAULT_PRECIO_BASE)))
            .andExpect(jsonPath("$.moneda").value(DEFAULT_MONEDA));
    }

    @Test
    @Transactional
    void getNonExistingDispositivos() throws Exception {
        // Get the dispositivos
        restDispositivosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDispositivos() throws Exception {
        // Initialize the database
        insertedDispositivos = dispositivosRepository.saveAndFlush(dispositivos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dispositivos
        Dispositivos updatedDispositivos = dispositivosRepository.findById(dispositivos.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDispositivos are not directly saved in db
        em.detach(updatedDispositivos);
        updatedDispositivos
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .precioBase(UPDATED_PRECIO_BASE)
            .moneda(UPDATED_MONEDA);

        restDispositivosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDispositivos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDispositivos))
            )
            .andExpect(status().isOk());

        // Validate the Dispositivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDispositivosToMatchAllProperties(updatedDispositivos);
    }

    @Test
    @Transactional
    void putNonExistingDispositivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositivos.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositivosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dispositivos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dispositivos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dispositivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDispositivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositivos.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositivosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dispositivos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dispositivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDispositivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositivos.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositivosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dispositivos)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dispositivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDispositivosWithPatch() throws Exception {
        // Initialize the database
        insertedDispositivos = dispositivosRepository.saveAndFlush(dispositivos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dispositivos using partial update
        Dispositivos partialUpdatedDispositivos = new Dispositivos();
        partialUpdatedDispositivos.setId(dispositivos.getId());

        partialUpdatedDispositivos.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION).precioBase(UPDATED_PRECIO_BASE);

        restDispositivosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDispositivos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDispositivos))
            )
            .andExpect(status().isOk());

        // Validate the Dispositivos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDispositivosUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDispositivos, dispositivos),
            getPersistedDispositivos(dispositivos)
        );
    }

    @Test
    @Transactional
    void fullUpdateDispositivosWithPatch() throws Exception {
        // Initialize the database
        insertedDispositivos = dispositivosRepository.saveAndFlush(dispositivos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dispositivos using partial update
        Dispositivos partialUpdatedDispositivos = new Dispositivos();
        partialUpdatedDispositivos.setId(dispositivos.getId());

        partialUpdatedDispositivos
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .precioBase(UPDATED_PRECIO_BASE)
            .moneda(UPDATED_MONEDA);

        restDispositivosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDispositivos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDispositivos))
            )
            .andExpect(status().isOk());

        // Validate the Dispositivos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDispositivosUpdatableFieldsEquals(partialUpdatedDispositivos, getPersistedDispositivos(partialUpdatedDispositivos));
    }

    @Test
    @Transactional
    void patchNonExistingDispositivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositivos.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositivosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dispositivos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dispositivos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dispositivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDispositivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositivos.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositivosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dispositivos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dispositivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDispositivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositivos.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositivosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(dispositivos)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dispositivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDispositivos() throws Exception {
        // Initialize the database
        insertedDispositivos = dispositivosRepository.saveAndFlush(dispositivos);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the dispositivos
        restDispositivosMockMvc
            .perform(delete(ENTITY_API_URL_ID, dispositivos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return dispositivosRepository.count();
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

    protected Dispositivos getPersistedDispositivos(Dispositivos dispositivos) {
        return dispositivosRepository.findById(