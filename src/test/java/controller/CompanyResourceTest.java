package controller;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
class CompanyResourceTest {

    @Test
    @TestTransaction
    void testCreateAndGetCompany() {
        given()
                .contentType("application/json")
                .body("{\"name\": \"SpaceX\", \"country\": \"US\", \"symbol\": \"SPCX\", \"website\": \"http://spacex.com\", \"email\": \"contact@spacex.com\"}")
                .when().post("/companies")
                .then()
                .statusCode(201);

        given()
                .when().get("/companies")
                .then()
                .statusCode(200)
                .body(containsString("SpaceX"));
    }
}
