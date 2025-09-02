import entity.Company;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import repository.CompanyRepository;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CompanyRepositoryTest {

    @Inject
    CompanyRepository repository;

    @Test
    @TestTransaction
    public void testSaveAndFindById() {
        Company c = new Company();
        c.setName("Netflix");
        c.setSymbol("NFLX");
        c.setCountry("US");
        c.setWebsite("https://netflix.com");
        c.setEmail("contact@netflix.com");
        c.setCreatedAt(Instant.now());
        c.persist();

        Company found = repository.findById(c.id);
        assertNotNull(found);
        assertEquals("NFLX", found.getSymbol());
    }

    @Test
    @TestTransaction
    public void testListAll() {
        Company c = new Company();
        c.setName("Amazon");
        c.setSymbol("AMZN");
        c.setCountry("US");
        c.setWebsite("https://amazon.com");
        c.setEmail("contact@amazon.com");
        c.setCreatedAt(Instant.now());
        c.persist();

        var all = repository.listAll();
        assertTrue(all.stream().anyMatch(comp -> "AMZN".equals(comp.getSymbol())));
    }
}