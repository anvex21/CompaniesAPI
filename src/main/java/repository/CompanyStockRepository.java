package repository;

import entity.CompanyStock;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

public class CompanyStockRepository implements PanacheRepository<CompanyStock> {
    public CompanyStock findLatestByCompanyId(Long companyId) {
        // Returns the latest CompanyStock entry for the given companyId
        return find("company.id = ?1 ORDER BY fetchedAt DESC", companyId).firstResult();
    }
    public List<CompanyStock> findAllByCompanyId(Long companyId) {
        return find("companyId", companyId).list();
    }
}
