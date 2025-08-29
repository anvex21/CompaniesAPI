package dto;

import java.time.Instant;

public class CompanyResponseDTO {
    private Long id;
    private String name;
    private String country;
    private String symbol;
    private String website;
    private String email;
    private Instant createdAt;

    public CompanyResponseDTO(Long id, String name, String country, String symbol,
                              String website, String email, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.symbol = symbol;
        this.website = website;
        this.email = email;
        this.createdAt = createdAt;
    }

    public static CompanyResponseDTO fromEntity(entity.Company c) {
        return new CompanyResponseDTO(
                c.id, c.getName(), c.getCountry(), c.getSymbol(),
                c.getWebsite(), c.getEmail(), c.getCreatedAt()
        );
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getSymbol() { return symbol; }
    public String getWebsite() { return website; }
    public String getEmail() { return email; }
    public Instant getCreatedAt() { return createdAt; }
}
