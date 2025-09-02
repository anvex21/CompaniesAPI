package dto;

import io.quarkus.test.TestTransaction;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyStockDTOTest {

    @Test
    @TestTransaction
    public void testGettersSetters() {
        CompanyStockDTO dto = new CompanyStockDTO();
        dto.setName("Adobe");
        dto.setSymbol("ADBE");
        dto.setCountry("US");
        dto.setWebsite("https://adobe.com");
        dto.setEmail("contact@adobe.com");
        dto.setMarketCapitalization(1000.0);
        dto.setShareOutstanding(10.0);
        dto.setFetchedAt(Instant.now());

        assertEquals("ADBE", dto.getSymbol());
        assertEquals(1000.0, dto.getMarketCapitalization());
    }
}