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
    public String name;
    @Column(nullable = false, length = 2)
    public String country;

    @Column(nullable = false, unique = true)
    public String symbol;

    public String website;
    public String email;

    @Column(nullable = false)
    public Instant createdAt;
}
