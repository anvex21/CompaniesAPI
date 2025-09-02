package repository;

import entity.Company;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.TestTransaction;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CompanyRepositoryTest {

    @Inject
    CompanyRepository repository;

    @Test
    @TestTransaction
    void testPersistAndFind() {
        Company c = new Company();
        c.setName("Oracle");
        c.setSymbol("ORCL");
        c.setCountry("US");
        c.setWebsite("https://oracle.com");
        c.setEmail("contact@oracle.com");
        c.setCreatedAt(Instant.now());
        repository.persist(c);

        Company found = repository.findById(c.id);
        assertNotNull(found);
        assertEquals("ORCL", found.getSymbol());
    }
}