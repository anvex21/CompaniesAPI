package com.example.company;

import client.CompanyProfileResponse;
import client.FinnhubClient;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import service.CompanyStockService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@Transactional
public class FinnhubServiceTest {

    @Inject
    CompanyStockService companyStockService;

    @Test
    public void testGetCompanyProfile() {
        var stock = companyStockService.getOrFetchCompanyStock(51L);
        assertNotNull(stock);
        assertEquals("AAPL", stock.getCompany().getSymbol());
    }
}
