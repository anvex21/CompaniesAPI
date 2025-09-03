package service;

import dto.CompanyDTO;
import entity.Company;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CompanyServiceTest {

    @Inject
    CompanyService companyService;

    @Test
    @TestTransaction
    void testCreateCompanySuccess() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName("NewCorp");
        dto.setCountry("US");
        dto.setSymbol("NEW");
        dto.setWebsite("http://new.com");
        dto.setEmail("info@new.com");

        Company created = companyService.createCompany(dto);

        assertNotNull(created.id);
        assertEquals("NEW", created.getSymbol());
        assertEquals("NewCorp", created.getName());
    }

    @Test
    @TestTransaction
    void testCreateCompanyDuplicateSymbol() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName("TestCorp");
        dto.setCountry("US");
        dto.setSymbol("DUP");
        dto.setWebsite("http://test.com");
        dto.setEmail("contact@test.com");

        // Create first company
        companyService.createCompany(dto);

        // Prepare expected error map inline extraction
        Map<String, String> expectedError = Map.of("error", "Company symbol '" + dto.getSymbol() + "' already exists");

        // Attempt duplicate
        WebApplicationException ex = assertThrows(WebApplicationException.class, () -> companyService.createCompany(dto));
        assertTrue(ex.getResponse().getEntity().toString().contains(expectedError.get("error")));
    }

    @Test
    @TestTransaction
    void testUpdateCompanySuccess() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName("Gamma");
        dto.setCountry("US");
        dto.setSymbol("GAMMA");
        dto.setWebsite("http://gamma.com");
        dto.setEmail("contact@gamma.com");

        Company c = companyService.createCompany(dto);

        // Update
        dto.setName("Gamma Inc");
        dto.setEmail("info@gamma.com");
        Company updated = companyService.updateCompany(c.id, dto);

        assertEquals("Gamma Inc", updated.getName());
        assertEquals("info@gamma.com", updated.getEmail());
    }

    @Test
    @TestTransaction
    void testUpdateCompanyNotFound() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName("GhostCorp");
        dto.setCountry("US");
        dto.setSymbol("GHOST");
        dto.setWebsite("http://ghost.com");
        dto.setEmail("ghost@ghost.com");

        Map<String, String> expectedError = Map.of("error", "Company with id 99999 not found");

        WebApplicationException ex = assertThrows(WebApplicationException.class, () -> companyService.updateCompany(99999L, dto));
        assertTrue(ex.getResponse().getEntity().toString().contains(expectedError.get("error")));
    }

    @Test
    @TestTransaction
    void testUpdateCompanyDuplicateSymbol() {
        // Create first company
        CompanyDTO first = new CompanyDTO();
        first.setName("Alpha");
        first.setCountry("US");
        first.setSymbol("ALPHA");
        first.setWebsite("http://alpha.com");
        first.setEmail("contact@alpha.com");
        companyService.createCompany(first);

        // Create second company
        CompanyDTO second = new CompanyDTO();
        second.setName("Beta");
        second.setCountry("US");
        second.setSymbol("BETA");
        second.setWebsite("http://beta.com");
        second.setEmail("contact@beta.com");
        Company c2 = companyService.createCompany(second);

        // Attempt to update second company with duplicate symbol
        second.setSymbol("ALPHA");
        Map<String, String> expectedError = Map.of("error", "Company symbol '" + second.getSymbol() + "' already exists");

        WebApplicationException ex = assertThrows(WebApplicationException.class, () -> companyService.updateCompany(c2.id, second));
        assertTrue(ex.getResponse().getEntity().toString().contains(expectedError.get("error")));
    }

    @Test
    @TestTransaction
    void testGetAllCompanies() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName("Delta");
        dto.setCountry("US");
        dto.setSymbol("DELTA");
        dto.setWebsite("http://delta.com");
        dto.setEmail("contact@delta.com");

        companyService.createCompany(dto);

        List<Company> all = companyService.getAllCompanies();

        // Ensure list is non-empty and contains our created company
        assertFalse(all.isEmpty());
        assertTrue(all.stream().anyMatch(c -> "DELTA".equals(c.getSymbol())));
    }
}
