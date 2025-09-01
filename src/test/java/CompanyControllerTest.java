import dto.CompanyDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class CompanyControllerTest {

    @Test
    @TestTransaction
    public void testCreateCompanySuccess() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName("Apple");
        dto.setCountry("US");
        dto.setSymbol("AAPL");
        dto.setWebsite("https://apple.com");
        dto.setEmail("contact@apple.com");

        given()
                .contentType("application/json")
                .body(dto)
                .when()
                .post("/companies")
                .then()
                .statusCode(201)
                .body("name", equalTo("Apple"))
                .body("symbol", equalTo("AAPL"));
    }

    @Test
    @TestTransaction
    public void testCreateCompanyInvalidDTO() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName(""); // invalid
        dto.setCountry("USA"); // invalid (length>2)
        dto.setSymbol(""); // invalid

        given()
                .contentType("application/json")
                .body(dto)
                .when()
                .post("/companies")
                .then()
                .statusCode(400);
    }

    @Test
    @TestTransaction
    public void testGetCompanies() {
        // create a company first
        CompanyDTO dto = new CompanyDTO();
        dto.setName("IBM");
        dto.setCountry("US");
        dto.setSymbol("IBM");
        dto.setWebsite("https://ibm.com");
        dto.setEmail("contact@ibm.com");

        given()
                .contentType("application/json")
                .body(dto)
                .when()
                .post("/companies")
                .then()
                .statusCode(201);

        // fetch list
        given()
                .when()
                .get("/companies")
                .then()
                .statusCode(200)
                .body(containsString("IBM"));
    }
}