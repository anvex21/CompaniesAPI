package dto;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class CompanyStockDTOTest {

    @Test
    void testGettersAndSetters() {
        CompanyStockDTO dto = new CompanyStockDTO();
        Instant now = Instant.now();

        dto.setName("TestCo");
        dto.setCountry("BG");
        dto.setSymbol("TST");
        dto.setWebsite("https://testco.com");
        dto.setEmail("info@testco.com");
        dto.setMarketCapitalization(12345.0);
        dto.setShareOutstanding(6789.0);
        dto.setFetchedAt(now);

        // call getters to cover them
        assertEquals("TestCo", dto.getName());
        assertEquals("BG", dto.getCountry());
        assertEquals("TST", dto.getSymbol());
        assertEquals("https://testco.com", dto.getWebsite());
        assertEquals("info@testco.com", dto.getEmail());
        assertEquals(12345.0, dto.getMarketCapitalization());
        assertEquals(6789.0, dto.getShareOutstanding());
        assertEquals(now, dto.getFetchedAt());
    }
}