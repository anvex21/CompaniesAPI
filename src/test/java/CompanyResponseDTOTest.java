import dto.CompanyResponseDTO;
import entity.Company;
import io.quarkus.test.TestTransaction;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyResponseDTOTest {

    @Test
    @TestTransaction
    public void testFromEntity() {
        Company c = new Company();
        c.setName("Google");
        c.setSymbol("GOOGL");
        c.setCountry("US");
        c.setWebsite("https://google.com");
        c.setEmail("contact@google.com");
        c.setCreatedAt(Instant.now());
        c.id = 1L;

        CompanyResponseDTO dto = CompanyResponseDTO.fromEntity(c);
        assertEquals("GOOGL", dto.getSymbol());
        assertEquals("Google", dto.getName());
    }
}