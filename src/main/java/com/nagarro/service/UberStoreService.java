package com.nagarro.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.config.UberStoreConfig;
import com.nagarro.ubermodels.Stores;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Path("/stores")
@Slf4j
public class UberStoreService {

    @Inject
    UberAuthService uberAuthService;

    @Inject
    @RestClient
    UberStoreConfig uberStoreConfig;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchStore() {
        try {
            // Get token response
            Response tokenResponse = uberAuthService.getToken();
            if (tokenResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                log.error("Failed to get access token. Status code: {}", tokenResponse.getStatus());
                return tokenResponse;
            }

            String jsonResponse = tokenResponse.readEntity(String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            String accessToken = jsonNode.get("access_token").asText();
            String authorizationHeader = "Bearer " + accessToken;

            // Get stores response
            Response storesResponse = uberStoreConfig.getStores(authorizationHeader);
            if (storesResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                Stores stores = objectMapper.readValue(storesResponse.readEntity(String.class), Stores.class);
                String storesJson = objectMapper.writeValueAsString(stores);

                return Response.ok(storesJson).build();
            } else {
                log.error("Failed to fetch store data. Response code: {}", storesResponse.getStatus());
                return storesResponse;
            }
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON", e);
            return Response.serverError().build();
        } catch (Exception e) {
            log.error("Error processing API request", e);
            return Response.serverError().build();
        }
    }
}