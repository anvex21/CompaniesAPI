package entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "companies")
public class Company extends PanacheEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 2)
    private String country;

    @Column(nullable = false, unique = true)
    private String symbol;

    private String website;
    private String email;

    @Column(nullable = false)
    private Instant createdAt;

    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getSymbol() { return symbol; }
    public String getWebsite() { return website; }
    public String getEmail() { return email; }
    public Instant getCreatedAt() { return createdAt; }

    public void setName(String name) { this.name = name; }
    public void setCountry(String country) { this.country = country; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public void setWebsite(String website) { this.website = website; }
    public void setEmail(String email) { this.email = email; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
