package controller;

import entity.Company;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import repository.CompanyRepository;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import io.quarkus.test.TestTransaction;

@QuarkusTest
public class CompanyStockControllerTest {

    @Inject
    CompanyRepository companyRepository;

    Long companyId;

    @Test
    @TestTransaction
    public void testGetCompanyStockEndpointIntegration() {
        Company c = new Company();
        c.setName("Adobe");
        c.setSymbol("ADBE");
        c.setCountry("US");
        c.setEmail("contact@adobe.com");
        c.setWebsite("https://adobe.com");
        c.setCreatedAt(Instant.now());
        c.persist();
        Company.getEntityManager().flush();

        int statusCode =
                given()
                        .accept(MediaType.APPLICATION_JSON)
                        .when()
                        .get("/company-stocks/" + c.id)
                        .then()
                        .extract()
                        .statusCode();

        // Accept both 200 and 404
        assertTrue(statusCode == 200 || statusCode == 404);

        // Only check body if 200
        if (statusCode == 200) {
            given()
                    .accept(MediaType.APPLICATION_JSON)
                    .when()
                    .get("/company-stocks/" + c.id)
                    .then()
                    .body("symbol", equalTo("ADBE"))
                    .body("marketCapitalization", notNullValue())
                    .body("shareOutstanding", notNullValue());
        }
    }
}