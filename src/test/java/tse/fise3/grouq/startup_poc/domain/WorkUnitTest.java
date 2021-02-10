package tse.fise3.grouq.startup_poc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tse.fise3.grouq.startup_poc.web.rest.TestUtil;

public class WorkUnitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkUnit.class);
        WorkUnit workUnit1 = new WorkUnit();
        workUnit1.setId(1L);
        WorkUnit workUnit2 = new WorkUnit();
        workUnit2.setId(workUnit1.getId());
        assertThat(workUnit1).isEqualTo(workUnit2);
        workUnit2.setId(2L);
        assertThat(workUnit1).isNotEqualTo(workUnit2);
        workUnit1.setId(null);
        assertThat(workUnit1).isNotEqualTo(workUnit2);
    }
}
