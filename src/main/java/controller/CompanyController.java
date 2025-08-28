package controller;

import jakarta.validation.Valid;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
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
@Tag(name = "Companies")
public class CompanyController {

    @Inject
    CompanyService service;

    @POST
    @Path("/")
    public Company createCompany(@Valid CompanyDTO dto) {
        return service.createCompany(dto);
    }

    @GET
    @Path("/")
    public List<Company> getCompanies() {
        return service.getAllCompanies();
    }

    @PUT
    @Path("/{id}")
    public Company updateCompany(@PathParam("id") Long id, @Valid CompanyDTO dto) {
        return service.updateCompany(id, dto);
    }
}
