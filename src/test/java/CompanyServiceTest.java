import dto.CompanyDTO;
import entity.Company;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.Test;
import service.CompanyService;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals("META", saved.getSymbol());
    }

    @Test
    @TestTransaction
    void testUpdateCompany() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName("Spotify");
        dto.setCountry("SE");
        dto.setSymbol("SPOT");
        dto.setWebsite("https://spotify.com");
        dto.setEmail("contact@spotify.com");
        Company created = companyService.createCompany(dto);

        CompanyDTO updateDto = new CompanyDTO();
        updateDto.setName("Spotify Ltd");
        updateDto.setCountry("SE");
        updateDto.setSymbol("SPOT");
        updateDto.setWebsite("https://spotify.com");
        updateDto.setEmail("support@spotify.com");
        Company updated = companyService.updateCompany(created.id, updateDto);
        assertEquals("Spotify Ltd", updated.getName());
        assertEquals("support@spotify.com", updated.getEmail());
    }

    @Test
    @TestTransaction
    void testUpdateCompanyNotFound() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName("Ghost");
        dto.setCountry("US");
        dto.setSymbol("GHOST");
        dto.setWebsite("https://ghost.com");
        dto.setEmail("contact@ghost.com");

        WebApplicationException ex = assertThrows(WebApplicationException.class, () -> {
            companyService.updateCompany(99999L, dto);
        });
        // Check entity for error message
        assertTrue(ex.getResponse().getEntity().toString().contains("not found"), "Exception should mention 'not found'");
    }

    @Test
    @TestTransaction
    void testDuplicateSymbolThrows() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName("Zoom");
        dto.setCountry("US");
        dto.setSymbol("ZM");
        dto.setWebsite("https://zoom.com");
        dto.setEmail("contact@zoom.com");
        companyService.createCompany(dto);

        WebApplicationException ex = assertThrows(WebApplicationException.class, () -> {
            companyService.createCompany(dto);
        });
        // Check entity for error message
        assertTrue(ex.getResponse().getEntity().toString().contains("already exists"), "Exception should mention 'already exists'");
    }
}