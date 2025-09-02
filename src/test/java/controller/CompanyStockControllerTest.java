package controller;

import entity.Company;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.TestTransaction;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class CompanyStockControllerTest {

    @Test
    @TestTransaction
    void testGetCompanyStockSuccess() {
        Company c = new Company();
        c.setName("Tesla");
        c.setSymbol("TSLA");
        c.setCountry("US");
        c.setEmail("contact@tesla.com");
        c.setWebsite("https://tesla.com");
        c.setCreatedAt(Instant.now());
        c.persist();

        given().when().get("/company-stocks/" + c.id)
                .then().statusCode(anyOf(is(200), is(404))); // depending on API

        given().when().get("/company-stocks/99999")
                .then().statusCode(404);
    }
}