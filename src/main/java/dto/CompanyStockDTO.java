package dto;

import java.time.Instant;

public class CompanyStockDTO {
    // Company fields
    private String name;
    private String country;
    private String symbol;
    private String website;
    private String email;

    // CompanyStock fields
    private Double marketCapitalization;
    private Double shareOutstanding;
    private Instant fetchedAt;

    // Getters
    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getSymbol() { return symbol; }
    public String getWebsite() { return website; }
    public String getEmail() { return email; }
    public Double getMarketCapitalization() { return marketCapitalization; }
    public Double getShareOutstanding() { return shareOutstanding; }
    public Instant getFetchedAt() { return fetchedAt; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setCountry(String country) { this.country = country; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public void setWebsite(String website) { this.website = website; }
    public void setEmail(String email) { this.email = email; }
    public void setMarketCapitalization(Double marketCapitalization) { this.marketCapitalization = marketCapitalization; }
    public void setShareOutstanding(Double shareOutstanding) { this.shareOutstanding = shareOutstanding; }
    public void setFetchedAt(Instant fetchedAt) { this.fetchedAt = fetchedAt; }
}