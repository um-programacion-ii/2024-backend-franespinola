package ar.edu.um.programacion2.web.rest;

import static ar.edu.um.programacion2.domain.OpcionesAsserts.*;
import static ar.edu.um.programacion2.web.rest.TestUtil.createUpdateProxyForBean;
import static ar.edu.um.programacion2.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.um.programacion2.IntegrationTest;
import ar.edu.um.programacion2.domain.Opciones;
import ar.edu.um.programacion2.domain.Personalizaciones;
import ar.edu.um.programacion2.repository.OpcionesRepository;
import ar.edu.um.programacion2.service.dto.OpcionesDTO;
import ar.edu.um.programacion2.service.mapper.OpcionesMapper;
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
 * Integration tests for the {@link OpcionesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OpcionesResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRECIO_ADICIONAL = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRECIO_ADICIONAL = new BigDecimal(1);

    private static final String ENTITY_API_URL = "/api/opciones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OpcionesRepository opcionesRepository;

    @Autowired
    private OpcionesMapper opcionesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpcionesMockMvc;

    private Opciones opciones;

    private Opciones insertedOpciones;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opciones createEntity(EntityManager em) {
        Opciones opciones = new Opciones()
            .codigo(DEFAULT_CODIGO)
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .precioAdicional(DEFAULT_PRECIO_ADICIONAL);
        // Add required entity
        Personalizaciones personalizaciones;
        if (TestUtil.findAll(em, Personalizaciones.class).isEmpty()) {
            personalizaciones = PersonalizacionesResourceIT.createEntity();
            em.persist(personalizaciones);
            em.flush();
        } else {
            personalizaciones = TestUtil.findAll(em, Personalizaciones.class).get(0);
        }
        opciones.setPersonalizacion(personalizaciones);
        return opciones;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opciones createUpdatedEntity(EntityManager em) {
        Opciones updatedOpciones = new Opciones()
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .precioAdicional(UPDATED_PRECIO_ADICIONAL);
        // Add required entity
        Personalizaciones personalizaciones;
        if (TestUtil.findAll(em, Personalizaciones.class).isEmpty()) {
            personalizaciones = PersonalizacionesResourceIT.createUpdatedEntity();
            em.persist(personalizaciones);
            em.flush();
        } else {
            personalizaciones = TestUtil.findAll(em, Personalizaciones.class).get(0);
        }
        updatedOpciones.setPersonalizacion(personalizaciones);
        return updatedOpciones;
    }

    @BeforeEach
    public void initTest() {
        opciones = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedOpciones != null) {
            opcionesRepository.delete(insertedOpciones);
            insertedOpciones = null;
        }
    }

    @Test
    @Transactional
    void createOpciones() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Opciones
        OpcionesDTO opcionesDTO = opcionesMapper.toDto(opciones);
        var returnedOpcionesDTO = om.readValue(
            restOpcionesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opcionesDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OpcionesDTO.class
        );

        // Validate the Opciones in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOpciones = opcionesMapper.toEntity(returnedOpcionesDTO);
        assertOpcionesUpdatableFieldsEquals(returnedOpciones, getPersistedOpciones(returnedOpciones));

        insertedOpciones = returnedOpciones;
    }

    @Test
    @Transactional
    void createOpcionesWithExistingId() throws Exception {
        // Create the Opciones with an existing ID
        opciones.setId(1L);
        OpcionesDTO opcionesDTO = opcionesMapper.toDto(opciones);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpcionesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opcionesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Opciones in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        opciones.setCodigo(null);

        // Create the Opciones, which fails.
        OpcionesDTO opcionesDTO = opcionesMapper.toDto(opciones);

        restOpcionesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opcionesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        opciones.setNombre(null);

        // Create the Opciones, which fails.
        OpcionesDTO opcionesDTO = opcionesMapper.toDto(opciones);

        restOpcionesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opcionesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        opciones.setDescripcion(null);

        // Create the Opciones, which fails.
        OpcionesDTO opcionesDTO = opcionesMapper.toDto(opciones);

        restOpcionesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opcionesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrecioAdicionalIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        opciones.setPrecioAdicional(null);

        // Create the Opciones, which fails.
        OpcionesDTO opcionesDTO = opcionesMapper.toDto(opciones);

        restOpcionesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opcionesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOpciones() throws Exception {
        // Initialize the database
        insertedOpciones = opcionesRepository.saveAndFlush(opciones);

        // Get all the opcionesList
        restOpcionesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opciones.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].precioAdicional").value(hasItem(sameNumber(DEFAULT_PRECIO_ADICIONAL))));
    }

    @Test
    @Transactional
    void getOpciones() throws Exception {
        // Initialize the database
        insertedOpciones = opcionesRepository.saveAndFlush(opciones);

        // Get the opciones
        restOpcionesMockMvc
            .perform(get(ENTITY_API_URL_ID, opciones.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opciones.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.precioAdicional").value(sameNumber(DEFAULT_PRECIO_ADICIONAL)));
    }

    @Test
    @Transactional
    void getNonExistingOpciones() throws Exception {
        // Get the opciones
        restOpcionesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOpciones() throws Exception {
        // Initialize the database
        insertedOpciones = opcionesRepository.saveAndFlush(opciones);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opciones
        Opciones updatedOpciones = opcionesRepository.findById(opciones.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOpciones are not directly saved in db
        em.detach(updatedOpciones);
        updatedOpciones
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .precioAdicional(UPDATED_PRECIO_ADICIONAL);
        OpcionesDTO opcionesDTO = opcionesMapper.toDto(updatedOpciones);

        restOpcionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opcionesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(opcionesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Opciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOpcionesToMatchAllProperties(updatedOpciones);
    }

    @Test
    @Transactional
    void putNonExistingOpciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opciones.setId(longCount.incrementAndGet());

        // Create the Opciones
        OpcionesDTO opcionesDTO = opcionesMapper.toDto(opciones);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpcionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opcionesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(opcionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opciones.setId(longCount.incrementAndGet());

        // Create the Opciones
        OpcionesDTO opcionesDTO = opcionesMapper.toDto(opciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(opcionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opciones.setId(longCount.incrementAndGet());

        // Create the Opciones
        OpcionesDTO opcionesDTO = opcionesMapper.toDto(opciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcionesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opcionesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Opciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpcionesWithPatch() throws Exception {
        // Initialize the database
        insertedOpciones = opcionesRepository.saveAndFlush(opciones);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opciones using partial update
        Opciones partialUpdatedOpciones = new Opciones();
        partialUpdatedOpciones.setId(opciones.getId());

        partialUpdatedOpciones.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION).precioAdicional(UPDATED_PRECIO_ADICIONAL);

        restOpcionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpciones.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpciones))
            )
            .andExpect(status().isOk());

        // Validate the Opciones in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpcionesUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedOpciones, opciones), getPersistedOpciones(opciones));
    }

    @Test
    @Transactional
    void fullUpdateOpcionesWithPatch() throws Exception {
        // Initialize the database
        insertedOpciones = opcionesRepository.saveAndFlush(opciones);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opciones using partial update
        Opciones partialUpdatedOpciones = new Opciones();
        partialUpdatedOpciones.setId(opciones.getId());

        partialUpdatedOpciones
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .precioAdicional(UPDATED_PRECIO_ADICIONAL);

        restOpcionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpciones.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpciones))
            )
            .andExpect(status().isOk());

        // Validate the Opciones in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpcionesUpdatableFieldsEquals(partialUpdatedOpciones, getPersistedOpciones(partialUpdatedOpciones));
    }

    @Test
    @Transactional
    void patchNonExistingOpciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opciones.setId(longCount.incrementAndGet());

        // Create the Opciones
        OpcionesDTO opcionesDTO = opcionesMapper.toDto(opciones);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpcionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, opcionesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(opcionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opciones.setId(longCount.incrementAndGet());

        // Create the Opciones
        OpcionesDTO opcionesDTO = opcionesMapper.toDto(opciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(opcionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opciones.setId(longCount.incrementAndGet());

        // Create the Opciones
        OpcionesDTO opcionesDTO = opcionesMapper.toDto(opciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcionesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(opcionesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Opciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpciones() throws Exception {
        // Initialize the database
        insertedOpciones = opcionesRepository.saveAndFlush(opciones);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the opciones
        restOpcionesMockMvc
            .perform(delete(ENTITY_API_URL_ID, opciones.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return opcionesRepository.count();
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

    protected Opciones getPersistedOpciones(Opciones opciones) {
        return opcionesRepository.findById(opciones.getId()).orElseThrow();
    }

    protected void assertPersistedOpcionesToMatchAllProperties(Opciones expectedOpciones) {
        assertOpcionesAllPropertiesEquals(expectedOpciones, getPersistedOpciones(expectedOpciones));
    }

    protected void assertPersistedOpcionesToMatchUpdatableProperties(Opciones expectedOpciones) {
        assertOpcionesAllUpdatablePropertiesEquals(expectedOpciones, getPersistedOpciones(expectedOpciones));
    }
}
