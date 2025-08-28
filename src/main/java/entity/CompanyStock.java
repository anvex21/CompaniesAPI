package entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name="company_stocks")
public class CompanyStock extends PanacheEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private Double marketCapitalization;

    @Column(nullable = false)
    private Double shareOutstanding;

    @Column(nullable = false)
    private Instant fetchedAt;

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public Double getMarketCapitalization() { return marketCapitalization; }
    public void setMarketCapitalization(Double marketCapitalization) { this.marketCapitalization = marketCapitalization; }

    public Double getShareOutstanding() { return shareOutstanding; }
    public void setShareOutstanding(Double shareOutstanding) { this.shareOutstanding = shareOutstanding; }

    public Instant getFetchedAt() { return fetchedAt; }
    public void setFetchedAt(Instant fetchedAt) { this.fetchedAt = fetchedAt; }
}
