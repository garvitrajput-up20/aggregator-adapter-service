package com.nagarro.config;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/oauth/v2")
@RegisterRestClient(configKey="uber-api")
public interface UberAuthClient {
    @POST
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    String getToken(@FormParam("client_id") String clientId,
                    @FormParam("client_secret") String clientSecret,
                    @FormParam("scope") String scope,
                    @FormParam("grant_type") String grantType);
}