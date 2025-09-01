package com.example.company;

import client.CompanyProfileResponse;
import client.FinnhubClient;
import entity.Company;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import service.CompanyStockService;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@Transactional
public class FinnhubServiceTest {

    @Inject
    CompanyStockService companyStockService;

    @Test
    public void testGetCompanyProfile() {
        // Setup: Create and persist the company (Panache assigns the ID)
        Company company = new Company();
        company.setSymbol("ADBE");
        company.setName("Adobe");
        company.setCountry("US");
        company.setEmail("contact@adoble.com");
        company.setWebsite("https://adoble.com");
        company.setCreatedAt(Instant.now());
        company.persist(); // Panache persist method

        Long companyId = company.id; // ID is now set

        var stock = companyStockService.getOrFetchCompanyStock(companyId);
        assertNotNull(stock);
        assertEquals("ADBE", stock.getCompany().getSymbol());
    }
}
