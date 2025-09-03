package service;

import dto.CompanyDTO;
import entity.Company;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import repository.CompanyRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class CompanyService {
    private static final String ERROR_KEY = "error";
    private final CompanyRepository repository;

    // Constructor injection
    @Inject
    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    @Transactional // Starts a transaction, if the method finishes normally -> saves changes in the db. If not -> cancels changes (rollback)
    public Company createCompany(CompanyDTO dto) {
        if (repository.find("symbol", dto.getSymbol()).firstResult() != null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity(Map.of(ERROR_KEY, "Company symbol '" + dto.getSymbol() + "' already exists"))
                            .build()
            );
        }
        Company c = new Company();
        c.setName(dto.getName());
        c.setCountry(dto.getCountry());
        c.setSymbol(dto.getSymbol());
        c.setWebsite(dto.getWebsite());
        c.setEmail(dto.getEmail());
        c.setCreatedAt(Instant.now());

        repository.persist(c);
        return c;
    }

    @Transactional // Starts a transaction, if the method finishes normally -> saves changes in the db. If not -> cancels changes (rollback)
    public Company updateCompany(Long id, CompanyDTO dto) {
        Company c = repository.findById(id);
        if (c == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(Map.of(ERROR_KEY, "Company with id " + id + " not found"))
                            .build()
            );
        }

        Company existing = repository.find("symbol", dto.getSymbol()).firstResult();
        if (existing != null && !existing.id.equals(id)) {
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity(Map.of(ERROR_KEY, "Company symbol '" + dto.getSymbol() + "' already exists"))
                            .build()
            );
        }
        c.setName(dto.getName());
        c.setCountry(dto.getCountry());
        c.setSymbol(dto.getSymbol());
        c.setWebsite(dto.getWebsite());
        c.setEmail(dto.getEmail());
        repository.persist(c);
        return c;

    }

    public List<Company> getAllCompanies() {
        return repository.listAll();
    }
}
