package service;

import dto.CompanyStockDTO;
import entity.Company;
import entity.CompanyStock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.TestTransaction;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CompanyStockServiceTest {

    @Inject
    CompanyStockService stockService;

    @Test
    @TestTransaction
    void testFetchFromFinnhubAndCache() {
        Company company = new Company();
        company.setName("ATA Creativity Global");
        company.setSymbol("AACG");
        company.setCountry("US");
        company.setWebsite("https://ata.com");
        company.setEmail("contact@ata.com");
        company.setCreatedAt(Instant.now());
        company.persist();

        CompanyStock stock1 = stockService.getOrFetchCompanyStock(company.id);
        assertNotNull(stock1.getMarketCapitalization());

        CompanyStock stock2 = stockService.getOrFetchCompanyStock(company.id);
        assertEquals(stock1.id, stock2.id, "Should reuse cached record");
    }

    @Test
    @TestTransaction
    void testGetCompanyStockDTONotFound() {
        WebApplicationException ex = assertThrows(WebApplicationException.class, () -> {
            stockService.getCompanyStockDTO(99999L);
        });
        assertEquals(404, ex.getResponse().getStatus());
    }
}
