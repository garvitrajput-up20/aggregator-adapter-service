package com.nagarro.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.config.UberStoreConfig;
import com.nagarro.ubermodels.Store;
import com.nagarro.ubermodels.StoreData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Path("/store")
@Slf4j
public class FetchByStoreId {
    @Inject
    UberAuthService uberAuthService;

    @Inject
    @RestClient
    UberStoreConfig uberStoreConfig;

    @GET
    @Path("/{storeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findStoreById(@PathParam("storeId") String storeId) {
        try {
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
            Response storeByIdResponse = uberStoreConfig.getStoreById(authorizationHeader, storeId);
            if (storeByIdResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                StoreData store = objectMapper.readValue(storeByIdResponse.readEntity(String.class), StoreData.class);
                String storeJson = objectMapper.writeValueAsString(store);
                return Response.ok(storeJson).build();
            } else {
                log.error("Failed to fetch store data for ID {}. Response code: {}", storeId, storeByIdResponse.getStatus());
                return storeByIdResponse;
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
