package ar.edu.um.programacion2.web.rest;

import static ar.edu.um.programacion2.domain.AdicionalesAsserts.*;
import static ar.edu.um.programacion2.web.rest.TestUtil.createUpdateProxyForBean;
import static ar.edu.um.programacion2.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.um.programacion2.IntegrationTest;
import ar.edu.um.programacion2.domain.Adicionales;
import ar.edu.um.programacion2.repository.AdicionalesRepository;
import ar.edu.um.programacion2.service.dto.AdicionalesDTO;
import ar.edu.um.programacion2.service.mapper.AdicionalesMapper;
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
 * Integration tests for the {@link AdicionalesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdicionalesResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRECIO = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRECIO = new BigDecimal(1);

    private static final BigDecimal DEFAULT_PRECIO_GRATIS = new BigDecimal(-1);
    private static final BigDecimal UPDATED_PRECIO_GRATIS = new BigDecimal(0);

    private static final String ENTITY_API_URL = "/api/adicionales";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AdicionalesRepository adicionalesRepository;

    @Autowired
    private AdicionalesMapper adicionalesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdicionalesMockMvc;

    private Adicionales adicionales;

    private Adicionales insertedAdicionales;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adicionales createEntity() {
        return new Adicionales()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .precio(DEFAULT_PRECIO)
            .precioGratis(DEFAULT_PRECIO_GRATIS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adicionales createUpdatedEntity() {
        return new Adicionales()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .precio(UPDATED_PRECIO)
            .precioGratis(UPDATED_PRECIO_GRATIS);
    }

    @BeforeEach
    public void initTest() {
        adicionales = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAdicionales != null) {
            adicionalesRepository.delete(insertedAdicionales);
            insertedAdicionales = null;
        }
    }

    @Test
    @Transactional
    void createAdicionales() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Adicionales
        AdicionalesDTO adicionalesDTO = adicionalesMapper.toDto(adicionales);
        var returnedAdicionalesDTO = om.readValue(
            restAdicionalesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adicionalesDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AdicionalesDTO.class
        );

        // Validate the Adicionales in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAdicionales = adicionalesMapper.toEntity(returnedAdicionalesDTO);
        assertAdicionalesUpdatableFieldsEquals(returnedAdicionales, getPersistedAdicionales(returnedAdicionales));

        insertedAdicionales = returnedAdicionales;
    }

    @Test
    @Transactional
    void createAdicionalesWithExistingId() throws Exception {
        // Create the Adicionales with an existing ID
        adicionales.setId(1L);
        AdicionalesDTO adicionalesDTO = adicionalesMapper.toDto(adicionales);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdicionalesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adicionalesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adicionales in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adicionales.setNombre(null);

        // Create the Adicionales, which fails.
        AdicionalesDTO adicionalesDTO = adicionalesMapper.toDto(adicionales);

        restAdicionalesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adicionalesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adicionales.setDescripcion(null);

        // Create the Adicionales, which fails.
        AdicionalesDTO adicionalesDTO = adicionalesMapper.toDto(adicionales);

        restAdicionalesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adicionalesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrecioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adicionales.setPrecio(null);

        // Create the Adicionales, which fails.
        AdicionalesDTO adicionalesDTO = adicionalesMapper.toDto(adicionales);

        restAdicionalesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adicionalesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrecioGratisIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adicionales.setPrecioGratis(null);

        // Create the Adicionales, which fails.
        AdicionalesDTO adicionalesDTO = adicionalesMapper.toDto(adicionales);

        restAdicionalesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adicionalesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdicionales() throws Exception {
        // Initialize the database
        insertedAdicionales = adicionalesRepository.saveAndFlush(adicionales);

        // Get all the adicionalesList
        restAdicionalesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adicionales.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(sameNumber(DEFAULT_PRECIO))))
            .andExpect(jsonPath("$.[*].precioGratis").value(hasItem(sameNumber(DEFAULT_PRECIO_GRATIS))));
    }

    @Test
    @Transactional
    void getAdicionales() throws Exception {
        // Initialize the database
        insertedAdicionales = adicionalesRepository.saveAndFlush(adicionales);

        // Get the adicionales
        restAdicionalesMockMvc
            .perform(get(ENTITY_API_URL_ID, adicionales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adicionales.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.precio").value(sameNumber(DEFAULT_PRECIO)))
            .andExpect(jsonPath("$.precioGratis").value(sameNumber(DEFAULT_PRECIO_GRATIS)));
    }

    @Test
    @Transactional
    void getNonExistingAdicionales() throws Exception {
        // Get the adicionales
        restAdicionalesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdicionales() throws Exception {
        // Initialize the database
        insertedAdicionales = adicionalesRepository.saveAndFlush(adicionales);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adicionales
        Adicionales updatedAdicionales = adicionalesRepository.findById(adicionales.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAdicionales are not directly saved in db
        em.detach(updatedAdicionales);
        updatedAdicionales
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .precio(UPDATED_PRECIO)
            .precioGratis(UPDATED_PRECIO_GRATIS);
        AdicionalesDTO adicionalesDTO = adicionalesMapper.toDto(updatedAdicionales);

        restAdicionalesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adicionalesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adicionalesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Adicionales in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAdicionalesToMatchAllProperties(updatedAdicionales);
    }

    @Test
    @Transactional
    void putNonExistingAdicionales() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionales.setId(longCount.incrementAndGet());

        // Create the Adicionales
        AdicionalesDTO adicionalesDTO = adicionalesMapper.toDto(adicionales);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdicionalesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adicionalesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adicionalesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adicionales in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdicionales() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionales.setId(longCount.incrementAndGet());

        // Create the Adicionales
        AdicionalesDTO adicionalesDTO = adicionalesMapper.toDto(adicionales);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdicionalesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adicionalesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adicionales in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdicionales() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionales.setId(longCount.incrementAndGet());

        // Create the Adicionales
        AdicionalesDTO adicionalesDTO = adicionalesMapper.toDto(adicionales);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdicionalesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adicionalesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adicionales in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdicionalesWithPatch() throws Exception {
        // Initialize the database
        insertedAdicionales = adicionalesRepository.saveAndFlush(adicionales);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adicionales using partial update
        Adicionales partialUpdatedAdicionales = new Adicionales();
        partialUpdatedAdicionales.setId(adicionales.getId());

        partialUpdatedAdicionales.nombre(UPDATED_NOMBRE).precioGratis(UPDATED_PRECIO_GRATIS);

        restAdicionalesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdicionales.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdicionales))
            )
            .andExpect(status().isOk());

        // Validate the Adicionales in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdicionalesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAdicionales, adicionales),
            getPersistedAdicionales(adicionales)
        );
    }

    @Test
    @Transactional
    void fullUpdateAdicionalesWithPatch() throws Exception {
        // Initialize the database
        insertedAdicionales = adicionalesRepository.saveAndFlush(adicionales);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adicionales using partial update
        Adicionales partialUpdatedAdicionales = new Adicionales();
        partialUpdatedAdicionales.setId(adicionales.getId());

        partialUpdatedAdicionales
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .precio(UPDATED_PRECIO)
            .precioGratis(UPDATED_PRECIO_GRATIS);

        restAdicionalesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdicionales.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdicionales))
            )
            .andExpect(status().isOk());

        // Validate the Adicionales in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdicionalesUpdatableFieldsEquals(partialUpdatedAdicionales, getPersistedAdicionales(partialUpdatedAdicionales));
    }

    @Test
    @Transactional
    void patchNonExistingAdicionales() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionales.setId(longCount.incrementAndGet());

        // Create the Adicionales
        AdicionalesDTO adicionalesDTO = adicionalesMapper.toDto(adicionales);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdicionalesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adicionalesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adicionalesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adicionales in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdicionales() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionales.setId(longCount.incrementAndGet());

        // Create the Adicionales
        AdicionalesDTO adicionalesDTO = adicionalesMapper.toDto(adicionales);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdicionalesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adicionalesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adicionales in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdicionales() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionales.setId(longCount.incrementAndGet());

        // Create the Adicionales
        AdicionalesDTO adicionalesDTO = adicionalesMapper.toDto(adicionales);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdicionalesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(adicionalesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adicionales in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdicionales() throws Exception {
        // Initialize the database
        insertedAdicionales = adicionalesRepository.saveAndFlush(adicionales);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the adicionales
        restAdicionalesMockMvc
            .perform(delete(ENTITY_API_URL_ID, adicionales.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return adicionalesRepository.count();
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

    protected Adicionales getPersistedAdicionales(Adicionales adicionales) {
        return adicionalesRepository.findById(adicionales.getId()).orElseThrow();
    }

    protected void assertPersistedAdicionalesToMatchAllProperties(Adicionales expectedAdicionales) {
        assertAdicionalesAllPropertiesEquals(expectedAdicionales, getPersistedAdicionales(expectedAdicionales));
    }

    protected void assertPersistedAdicionalesToMatchUpdatableProperties(Adicionales expectedAdicionales) {
        assertAdicionalesAllUpdatablePropertiesEquals(expectedAdicionales, getPersistedAdicionales(expectedAdicionales));
    }
}
