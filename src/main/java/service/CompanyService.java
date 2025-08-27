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
        c.setName(dto.getName());
        c.setCountry(dto.getCountry());
        c.setSymbol(dto.getSymbol());
        c.setWebsite(dto.getWebsite());
        c.setEmail(dto.getEmail());
        c.setCreatedAt(Instant.now());

        repository.persist(c);
        return c;
    }

    @Transactional
    public Company updateCompany(Long id, CompanyDTO dto) {
        Company c = repository.findById(id);
        if(c == null){
            throw new RuntimeException("No such company found.");
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
