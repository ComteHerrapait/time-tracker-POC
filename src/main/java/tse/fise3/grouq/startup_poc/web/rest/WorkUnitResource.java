package tse.fise3.grouq.startup_poc.web.rest;

import tse.fise3.grouq.startup_poc.domain.WorkUnit;
import tse.fise3.grouq.startup_poc.repository.WorkUnitRepository;
import tse.fise3.grouq.startup_poc.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link tse.fise3.grouq.startup_poc.domain.WorkUnit}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WorkUnitResource {

    private final Logger log = LoggerFactory.getLogger(WorkUnitResource.class);

    private static final String ENTITY_NAME = "workUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkUnitRepository workUnitRepository;

    public WorkUnitResource(WorkUnitRepository workUnitRepository) {
        this.workUnitRepository = workUnitRepository;
    }

    /**
     * {@code POST  /work-units} : Create a new workUnit.
     *
     * @param workUnit the workUnit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workUnit, or with status {@code 400 (Bad Request)} if the workUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-units")
    public ResponseEntity<WorkUnit> createWorkUnit(@Valid @RequestBody WorkUnit workUnit) throws URISyntaxException {
        log.debug("REST request to save WorkUnit : {}", workUnit);
        if (workUnit.getId() != null) {
            throw new BadRequestAlertException("A new workUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkUnit result = workUnitRepository.save(workUnit);
        return ResponseEntity.created(new URI("/api/work-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-units} : Updates an existing workUnit.
     *
     * @param workUnit the workUnit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workUnit,
     * or with status {@code 400 (Bad Request)} if the workUnit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workUnit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-units")
    public ResponseEntity<WorkUnit> updateWorkUnit(@Valid @RequestBody WorkUnit workUnit) throws URISyntaxException {
        log.debug("REST request to update WorkUnit : {}", workUnit);
        if (workUnit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkUnit result = workUnitRepository.save(workUnit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, workUnit.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-units} : get all the workUnits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workUnits in body.
     */
    @GetMapping("/work-units")
    public List<WorkUnit> getAllWorkUnits() {
        log.debug("REST request to get all WorkUnits");
        return workUnitRepository.findAll();
    }

    /**
     * {@code GET  /work-units/:id} : get the "id" workUnit.
     *
     * @param id the id of the workUnit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workUnit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-units/{id}")
    public ResponseEntity<WorkUnit> getWorkUnit(@PathVariable Long id) {
        log.debug("REST request to get WorkUnit : {}", id);
        Optional<WorkUnit> workUnit = workUnitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(workUnit);
    }

    /**
     * {@code DELETE  /work-units/:id} : delete the "id" workUnit.
     *
     * @param id the id of the workUnit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-units/{id}")
    public ResponseEntity<Void> deleteWorkUnit(@PathVariable Long id) {
        log.debug("REST request to delete WorkUnit : {}", id);
        workUnitRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
