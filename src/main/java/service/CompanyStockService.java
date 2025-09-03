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

    private final CompanyRepository companyRepository;
    private final CompanyStockRepository stockRepository;
    private final FinnhubClient finnhubClient;
    private final String apiKey;

    public CompanyStockService(
            CompanyRepository companyRepository,
            CompanyStockRepository stockRepository,
            @RestClient FinnhubClient finnhubClient,
            @ConfigProperty(name = "finnhub.api.key") String apiKey
    ) {
        this.companyRepository = companyRepository;
        this.stockRepository = stockRepository;
        this.finnhubClient = finnhubClient;
        this.apiKey = apiKey;
    }

    @Transactional // Starts a transaction, if the method finishes normally -> saves changes in the db. If not -> cancels changes (rollback)
    public CompanyStock getOrFetchCompanyStock(Long companyId) {
        Company company = companyRepository.findById(companyId);
        if (company == null) {
            throw new WebApplicationException("Company not found", 404);
        }

        Optional<CompanyStock> latestStock = Optional.ofNullable(stockRepository.findLatestByCompanyId(companyId));
        Instant oneDayAgo = Instant.now().minus(java.time.Duration.ofDays(1));
        // if the last record for the company is made within 24 hours, returns it
        if (latestStock.isPresent() && latestStock.get().getFetchedAt().isAfter(oneDayAgo)) {
            return latestStock.get();
        }

        // Fetch from Finnhub
        // if the last record  is older than 24 hours, it calls the Finnhub API
        CompanyProfileResponse profile = finnhubClient.getCompanyProfile(company.getSymbol(), apiKey);

        CompanyStock stock = new CompanyStock();
        stock.setCompany(company);
        stock.setMarketCapitalization(profile.getMarketCapitalization());
        stock.setShareOutstanding(profile.getShareOutstanding());
        stock.setFetchedAt(Instant.now());

        stockRepository.persist(stock);
        return stock;
    }

    // returns the DTO with all the necessary fields
    @Transactional // Starts a transaction, if the method finishes normally -> saves changes in the db. If not -> cancels changes (rollback)
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
