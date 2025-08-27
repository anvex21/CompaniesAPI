package service;

import dto.CompanyDTO;
import entity.Company;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import repository.CompanyRepository;

import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class CompanyService {
    @Inject
    CompanyRepository repository;

    @Transactional
    public Company createCompany(CompanyDTO dto) {
        Company c = new Company();
        c.name = dto.name;
        c.country = dto.country;
        c.symbol = dto.symbol;
        c.website = dto.website;
        c.email = dto.email;
        c.createdAt = Instant.now();
        repository.persist(c);
        return c;
    }

    public List<Company> getAllCompanies() {
        return repository.listAll();
    }
}
