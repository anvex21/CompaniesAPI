package dto;

import java.time.Instant;

public class CompanyDTO {
    private String name;
    private String country;
    private String symbol;
    private String website;
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
