package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;


public class CompanyDTO {

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotBlank(message = "Country cannot be blank.")
    @Size(min = 2, max = 2)
    private String country;

    @NotBlank(message = "Symbol cannot be blank.")
    private String symbol;

    private String website;

    @Email
    private String email;

    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getSymbol() { return symbol; }
    public String getWebsite() { return website; }
    public String getEmail() { return email; }


    public void setName(String name) { this.name = name; }
    public void setCountry(String country) { this.country = country; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public void setWebsite(String website) { this.website = website; }
    public void setEmail(String email) { this.email = email; }

}
