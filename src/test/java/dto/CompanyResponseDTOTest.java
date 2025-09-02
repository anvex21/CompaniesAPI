package dto;

import entity.Company;
import io.quarkus.test.TestTransaction;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyResponseDTOTest {

    @Test
    public void testGettersAndFromEntity() {
        Instant now = Instant.now();
        Company c = new Company();
        c.id = 1L;
        c.setName("TestCo");
        c.setCountry("BG");
        c.setSymbol("TST");
        c.setWebsite("https://testco.com");
        c.setEmail("info@testco.com");
        c.setCreatedAt(now);

        CompanyResponseDTO dto = CompanyResponseDTO.fromEntity(c);

        // call getters to cover them
        assertEquals(1L, dto.getId());
        assertEquals("TestCo", dto.getName());
        assertEquals("BG", dto.getCountry());
        assertEquals("TST", dto.getSymbol());
        assertEquals("https://testco.com", dto.getWebsite());
        assertEquals("info@testco.com", dto.getEmail());
        assertEquals(now, dto.getCreatedAt());
    }
}