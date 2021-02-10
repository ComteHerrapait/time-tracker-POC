package tse.fise3.grouq.startup_poc.web.rest;

import tse.fise3.grouq.startup_poc.StartupPocApp;
import tse.fise3.grouq.startup_poc.domain.WorkUnit;
import tse.fise3.grouq.startup_poc.domain.User;
import tse.fise3.grouq.startup_poc.repository.WorkUnitRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WorkUnitResource} REST controller.
 */
@SpringBootTest(classes = StartupPocApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkUnitResourceIT {

    private static final Duration DEFAULT_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_DURATION = Duration.ofHours(12);

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private WorkUnitRepository workUnitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkUnitMockMvc;

    private WorkUnit workUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkUnit createEntity(EntityManager em) {
        WorkUnit workUnit = new WorkUnit()
            .duration(DEFAULT_DURATION)
            .date(DEFAULT_DATE)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        workUnit.setUser(user);
        return workUnit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkUnit createUpdatedEntity(EntityManager em) {
        WorkUnit workUnit = new WorkUnit()
            .duration(UPDATED_DURATION)
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        workUnit.setUser(user);
        return workUnit;
    }

    @BeforeEach
    public void initTest() {
        workUnit = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkUnit() throws Exception {
        int databaseSizeBeforeCreate = workUnitRepository.findAll().size();
        // Create the WorkUnit
        restWorkUnitMockMvc.perform(post("/api/work-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workUnit)))
            .andExpect(status().isCreated());

        // Validate the WorkUnit in the database
        List<WorkUnit> workUnitList = workUnitRepository.findAll();
        assertThat(workUnitList).hasSize(databaseSizeBeforeCreate + 1);
        WorkUnit testWorkUnit = workUnitList.get(workUnitList.size() - 1);
        assertThat(testWorkUnit.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testWorkUnit.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testWorkUnit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createWorkUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workUnitRepository.findAll().size();

        // Create the WorkUnit with an existing ID
        workUnit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkUnitMockMvc.perform(post("/api/work-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workUnit)))
            .andExpect(status().isBadRequest());

        // Validate the WorkUnit in the database
        List<WorkUnit> workUnitList = workUnitRepository.findAll();
        assertThat(workUnitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = workUnitRepository.findAll().size();
        // set the field null
        workUnit.setDuration(null);

        // Create the WorkUnit, which fails.


        restWorkUnitMockMvc.perform(post("/api/work-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workUnit)))
            .andExpect(status().isBadRequest());

        List<WorkUnit> workUnitList = workUnitRepository.findAll();
        assertThat(workUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkUnits() throws Exception {
        // Initialize the database
        workUnitRepository.saveAndFlush(workUnit);

        // Get all the workUnitList
        restWorkUnitMockMvc.perform(get("/api/work-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getWorkUnit() throws Exception {
        // Initialize the database
        workUnitRepository.saveAndFlush(workUnit);

        // Get the workUnit
        restWorkUnitMockMvc.perform(get("/api/work-units/{id}", workUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workUnit.getId().intValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingWorkUnit() throws Exception {
        // Get the workUnit
        restWorkUnitMockMvc.perform(get("/api/work-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkUnit() throws Exception {
        // Initialize the database
        workUnitRepository.saveAndFlush(workUnit);

        int databaseSizeBeforeUpdate = workUnitRepository.findAll().size();

        // Update the workUnit
        WorkUnit updatedWorkUnit = workUnitRepository.findById(workUnit.getId()).get();
        // Disconnect from session so that the updates on updatedWorkUnit are not directly saved in db
        em.detach(updatedWorkUnit);
        updatedWorkUnit
            .duration(UPDATED_DURATION)
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION);

        restWorkUnitMockMvc.perform(put("/api/work-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkUnit)))
            .andExpect(status().isOk());

        // Validate the WorkUnit in the database
        List<WorkUnit> workUnitList = workUnitRepository.findAll();
        assertThat(workUnitList).hasSize(databaseSizeBeforeUpdate);
        WorkUnit testWorkUnit = workUnitList.get(workUnitList.size() - 1);
        assertThat(testWorkUnit.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testWorkUnit.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testWorkUnit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkUnit() throws Exception {
        int databaseSizeBeforeUpdate = workUnitRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkUnitMockMvc.perform(put("/api/work-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workUnit)))
            .andExpect(status().isBadRequest());

        // Validate the WorkUnit in the database
        List<WorkUnit> workUnitList = workUnitRepository.findAll();
        assertThat(workUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkUnit() throws Exception {
        // Initialize the database
        workUnitRepository.saveAndFlush(workUnit);

        int databaseSizeBeforeDelete = workUnitRepository.findAll().size();

        // Delete the workUnit
        restWorkUnitMockMvc.perform(delete("/api/work-units/{id}", workUnit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkUnit> workUnitList = workUnitRepository.findAll();
        assertThat(workUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
