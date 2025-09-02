package repository;

import entity.Company;
import entity.CompanyStock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.TestTransaction;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CompanyStockRepositoryTest {

    @Inject
    CompanyRepository companyRepository;

    @Inject
    CompanyStockRepository stockRepository;

    @Test
    @TestTransaction
    void testFindLatestByCompanyId() {
        Company company = new Company();
        company.setName("Spotify");
        company.setSymbol("SPOT");
        company.setCountry("SE");
        company.setWebsite("https://spotify.com");
        company.setEmail("contact@spotify.com");
        company.setCreatedAt(Instant.now());
        companyRepository.persist(company);

        CompanyStock stock = new CompanyStock();
        stock.setCompany(company);
        stock.setMarketCapitalization(1000.0);
        stock.setShareOutstanding(500.0);
        stock.setFetchedAt(Instant.now());
        stockRepository.persist(stock);

        CompanyStock latest = stockRepository.findLatestByCompanyId(company.id);
        assertNotNull(latest);
        assertEquals(1000.0, latest.getMarketCapitalization());
    }
}
