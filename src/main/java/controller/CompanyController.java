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
import java.util.stream.Collectors;

@Path("/companies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Companies")
public class CompanyController {

    @Inject
    CompanyService service;

    @POST
    public Response createCompany(@Valid CompanyDTO dto) {
        Company company = service.createCompany(dto);
        return Response.created(URI.create("/companies/" + company.id))
                .entity(CompanyResponseDTO.fromEntity(company))
                .build();
    }

    @GET
    public Response getCompanies() {
        List<CompanyResponseDTO> companies = service.getAllCompanies()
                .stream()
                .map(CompanyResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(companies).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCompany(@PathParam("id") Long id, @Valid CompanyDTO dto) {
        Company updated = service.updateCompany(id, dto);
        return Response.ok(CompanyResponseDTO.fromEntity(updated)).build();
    }
}
