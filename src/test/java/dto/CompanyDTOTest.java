package dto;

import io.quarkus.test.TestTransaction;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CompanyDTOTest {

    @Test
    @TestTransaction
    void testValidation() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName(""); // invalid
        dto.setCountry("USA"); // invalid
        dto.setSymbol(""); // invalid

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CompanyDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @TestTransaction
    void testGettersSetters() {
        CompanyDTO dto = new CompanyDTO();
        dto.setName("IBM");
        dto.setCountry("US");
        dto.setSymbol("IBM");
        dto.setWebsite("https://ibm.com");
        dto.setEmail("contact@ibm.com");

        assertEquals("IBM", dto.getName());
        assertEquals("US", dto.getCountry());
        assertEquals("IBM", dto.getSymbol());
    }
}