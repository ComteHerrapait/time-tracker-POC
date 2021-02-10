package tse.fise3.grouq.startup_poc.repository;

import tse.fise3.grouq.startup_poc.domain.WorkUnit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the WorkUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkUnitRepository extends JpaRepository<WorkUnit, Long> {

    @Query("select workUnit from WorkUnit workUnit where workUnit.user.login = ?#{principal.username}")
    List<WorkUnit> findByUserIsCurrentUser();
}
