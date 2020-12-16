package tse.fise3.grouq.startup_poc;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("tse.fise3.grouq.startup_poc");

        noClasses()
            .that()
                .resideInAnyPackage("tse.fise3.grouq.startup_poc.service..")
            .or()
                .resideInAnyPackage("tse.fise3.grouq.startup_poc.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..tse.fise3.grouq.startup_poc.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
