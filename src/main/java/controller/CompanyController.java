package controller;

import service.CompanyService;
import entity.Company;
import dto.CompanyDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/companies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompanyController {

    @Inject
    CompanyService service;

    @POST
    @Path("/CreateCompany")
    public Company createCompany(CompanyDTO dto) {
        return service.createCompany(dto);
    }

    @GET
    @Path("/GetAllCompanies")
    public List<Company> getCompanies() {
        return service.getAllCompanies();
    }

    @PUT
    @Path("/UpdateCompany/{id}")
    public Company updateCompany(@PathParam("id") Long id, CompanyDTO dto) {
        return service.updateCompany(id, dto);
    }
}
