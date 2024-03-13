package com.nagarro.service;

import com.nagarro.config.UberAuthClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Slf4j
@ApplicationScoped
@Path("/auth")
public class UberAuthService {
    @Inject
    @RestClient
    UberAuthClient uberAuthClient;

    @ConfigProperty(name = "uber.client_id")
    String clientId;

    @ConfigProperty(name = "uber.client_secret")
    String clientSecret;
    @ConfigProperty(name = "uber.scopes")
    String scope;

    @ConfigProperty(name = "uber.grant_type")
    String grantType;

    @POST
    @ActivateRequestContext
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToken() {
        try {
            String token = uberAuthClient.getToken(clientId, clientSecret, scope, grantType);
            log.info("API HIT SUCCESS");
            return Response.ok(token).build();
        } catch (Exception e) {
            log.error("Error fetching token: {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching token").build();
        }
    }
}