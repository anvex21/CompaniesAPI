package controller;

import dto.CompanyResponseDTO;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import service.CompanyService;
import entity.Company;
import dto.CompanyDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.net.URI;
import java.util.List;

@Path("/companies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Companies")
public class CompanyController {

    private final CompanyService service;

    // Constructor injection
    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @POST
    public Response createCompany(@Valid CompanyDTO dto) {
        Company company = service.createCompany(dto);
        // sets the http status code to 201, sets the Location header (Location: /companies/{id}), .entity defines the body of the response, .build finalizies
        return Response.created(URI.create("/companies/" + company.id))
                .entity(CompanyResponseDTO.fromEntity(company))
                .build();
    }

    @GET
    public Response getCompanies() {
        // takes a list with companies from the service, converts it into stream<company>, appliesCompanyResponseDTO.fromEntity(company), collects
        // all the dtos into a list
        List<CompanyResponseDTO> companies = service.getAllCompanies()
                .stream()
                .map(CompanyResponseDTO::fromEntity)
                .toList();
        return Response.ok(companies).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCompany(@PathParam("id") Long id, @Valid CompanyDTO dto) {
        Company updated = service.updateCompany(id, dto);
        return Response.ok(CompanyResponseDTO.fromEntity(updated)).build();
    }
}
