package repository;

import entity.CompanyStock;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class CompanyStockRepository implements PanacheRepository<CompanyStock> {
    public CompanyStock findLatestByCompanyId(Long companyId) {
        // Returns the latest CompanyStock entry for the given companyId (ordering it by fetchedAt desc, returns the first result (latest)
        return find("company.id = ?1 ORDER BY fetchedAt DESC", companyId).firstResult();
    }
}
