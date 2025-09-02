import dto.CompanyDTO;
import dto.CompanyResponseDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import service.CompanyService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CompanyControllerTest {

    @Inject
    CompanyService companyService;

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
    public void testCreateCompanyDuplicateSymbol() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName("IBM");
        dto.setCountry("US");
        dto.setSymbol("IBM");
        dto.setWebsite("https://ibm.com");
        dto.setEmail("contact@ibm.com");
        // create first
        given()
                .contentType("application/json")
                .body(dto)
                .when()
                .post("/companies")
                .then()
                .statusCode(201);

        // duplicate
        given()
                .contentType("application/json")
                .body(dto)
                .when()
                .post("/companies")
                .then()
                .statusCode(409)
                .body("error", containsString("already exists"));
    }

    @Test
    @TestTransaction
    public void testGetCompanies() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName("Oracle");
        dto.setCountry("US");
        dto.setSymbol("ORCL");
        dto.setWebsite("http://oracle.com");
        dto.setEmail("contact@oracle.com");
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
                .body("name", hasItem("Oracle"));
    }

    @Test
    @TestTransaction
    public void testUpdateCompanySuccess() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName("Tesla");
        dto.setCountry("US");
        dto.setSymbol("TSLA");
        dto.setWebsite("https://tesla.com");
        dto.setEmail("contact@tesla.com");

        // create
        CompanyResponseDTO created = given()
                .contentType("application/json")
                .body(dto)
                .when()
                .post("/companies")
                .then()
                .statusCode(201)
                .extract().as(CompanyResponseDTO.class);

        // update
        CompanyDTO updateDto = new CompanyDTO();
        updateDto.setName("Tesla Inc");
        updateDto.setCountry("US");
        updateDto.setSymbol("TSLA");
        updateDto.setWebsite("https://tesla.com");
        updateDto.setEmail("info@tesla.com");
        given()
                .contentType("application/json")
                .body(updateDto)
                .when()
                .put("/companies/" + created.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Tesla Inc"))
                .body("email", equalTo("info@tesla.com"));
    }

    @Test
    @TestTransaction
    public void testUpdateCompanyNotFound() {
        CompanyDTO updateDto = new CompanyDTO();
        updateDto.setName("Nonexistent");
        updateDto.setCountry("US");
        updateDto.setSymbol("NONE");
        updateDto.setWebsite("https://none.com");
        updateDto.setEmail("none@none.com");
        given()
                .contentType("application/json")
                .body(updateDto)
                .when()
                .put("/companies/99999")
                .then()
                .statusCode(404)
                .body("error", containsString("not found"));
    }
}