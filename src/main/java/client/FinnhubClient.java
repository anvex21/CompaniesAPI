package client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@RegisterRestClient(configKey = "finnhub-api")
public interface FinnhubClient {

    @GET
    @Path("/stock/profile2")
    CompanyProfileResponse getCompanyProfile(@QueryParam("symbol") String symbol,
                                             @QueryParam("token") String token);
}
