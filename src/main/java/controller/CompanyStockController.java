package controller;

import dto.CompanyStockDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import service.CompanyStockService;

@Path("/company-stocks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Company Stocks")
public class CompanyStockController {

    @Inject
    CompanyStockService stockService;

    @GET
    @Path("/{companyId}")
    public CompanyStockDTO getCompanyStock(@PathParam("companyId") Long companyId) {
        return stockService.getCompanyStockDTO(companyId);
    }
}
