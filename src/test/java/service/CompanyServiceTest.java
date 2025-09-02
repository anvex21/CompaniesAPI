package service;

import dto.CompanyDTO;
import entity.Company;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.TestTransaction;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CompanyServiceTest {

    @Inject
    CompanyService companyService;

    @Test
    @TestTransaction
    void testCreateAndFindCompany() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName("Meta Platforms");
        dto.setCountry("US");
        dto.setSymbol("META");
        dto.setWebsite("https://meta.com");
        dto.setEmail("contact@meta.com");

        Company saved = companyService.createCompany(dto);
        assertNotNull(saved.id);

        assertTrue(companyService.getAllCompanies().stream()
                .anyMatch(c -> c.getSymbol().equals("META")));
    }

    @Test
    @TestTransaction
    void testUpdateCompanyDuplicateSymbol() {
        CompanyDTO dto1 = new CompanyDTO();
        dto1.setName("Company1");
        dto1.setCountry("US");
        dto1.setSymbol("CMP1");
        dto1.setWebsite("https://c1.com");
        dto1.setEmail("c1@c.com");
        Company c1 = companyService.createCompany(dto1);

        CompanyDTO dto2 = new CompanyDTO();
        dto2.setName("Company2");
        dto2.setCountry("US");
        dto2.setSymbol("CMP2");
        dto2.setWebsite("https://c2.com");
        dto2.setEmail("c2@c.com");
        Company c2 = companyService.createCompany(dto2);

        dto2.setSymbol("CMP1"); // conflict
        WebApplicationException ex = assertThrows(WebApplicationException.class, () -> {
            companyService.updateCompany(c2.id, dto2);
        });
        assertTrue(ex.getResponse().getEntity().toString().contains("already exists"));
    }
}