package service;

import client.CompanyProfileResponse;
import client.FinnhubClient;
import dto.CompanyStockDTO;
import entity.Company;
import entity.CompanyStock;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import repository.CompanyRepository;
import repository.CompanyStockRepository;

import java.time.Instant;
import java.util.Optional;

@ApplicationScoped
public class CompanyStockService {

    @Inject
    CompanyRepository companyRepository;

    @Inject
    CompanyStockRepository stockRepository;

    @Inject
    @RestClient
    FinnhubClient finnhubClient;

    @ConfigProperty(name = "finnhub.api.key")
    String apiKey;

    @Transactional
    public CompanyStock getOrFetchCompanyStock(Long companyId) {
        Company company = companyRepository.findById(companyId);
        if (company == null) {
            throw new WebApplicationException("Company not found", 404);
        }

        Optional<CompanyStock> latestStock = Optional.ofNullable(stockRepository.findLatestByCompanyId(companyId));
        Instant oneDayAgo = Instant.now().minus(java.time.Duration.ofDays(1));
        if (latestStock.isPresent() && latestStock.get().getFetchedAt().isAfter(oneDayAgo)) {
            return latestStock.get();
        }

        // Fetch from Finnhub
        CompanyProfileResponse profile = finnhubClient.getCompanyProfile(company.getSymbol(), apiKey);

        CompanyStock stock = new CompanyStock();
        stock.setCompany(company);
        stock.setMarketCapitalization(profile.getMarketCapitalization());
        stock.setShareOutstanding(profile.getShareOutstanding());
        stock.setFetchedAt(Instant.now());

        stockRepository.persist(stock);
        return stock;
    }

    @Transactional
    public CompanyStockDTO getCompanyStockDTO(Long companyId) {
        CompanyStock stock = getOrFetchCompanyStock(companyId);

        CompanyStockDTO dto = new CompanyStockDTO();
        dto.setName(stock.getCompany().getName());
        dto.setCountry(stock.getCompany().getCountry());
        dto.setSymbol(stock.getCompany().getSymbol());
        dto.setWebsite(stock.getCompany().getWebsite());
        dto.setEmail(stock.getCompany().getEmail());
        dto.setMarketCapitalization(stock.getMarketCapitalization());
        dto.setShareOutstanding(stock.getShareOutstanding());
        dto.setFetchedAt(stock.getFetchedAt());

        return dto;
    }
}
