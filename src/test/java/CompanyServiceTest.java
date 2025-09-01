import dto.CompanyDTO;
import entity.Company;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import service.CompanyService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class CompanyServiceTest {

    @Inject
    CompanyService companyService;

    @Test
    @TestTransaction
     void testCreateCompany() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName("Meta Platforms");
        dto.setCountry("US");
        dto.setSymbol("META");
        dto.setWebsite("https://meta.com");
        dto.setEmail("contact@meta.com");

        Company saved = companyService.createCompany(dto);
        assertNotNull(saved.id);
    }
}
