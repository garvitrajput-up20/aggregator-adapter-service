package com.nagarro.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.config.UberStoreConfig;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.Body;

@ApplicationScoped
@Unremovable
@Slf4j
@Path("/store")
public class UberStoreStatusService {
    @Inject
    UberAuthService uberAuthService;



    @Inject
    @RestClient
    UberStoreConfig uberStoreConfig;

    @GET
    @Path("/get-status/{storeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchStoreStatus(@PathParam("storeId") String storeId) {
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
            Response storeStatusResponse = uberStoreConfig.getStoreStatus(authorizationHeader, storeId);
            if (storeStatusResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                String storeStatusJson = storeStatusResponse.readEntity(String.class);
                return Response.ok(storeStatusJson).build();
            } else {
                log.error("Failed to fetch store status for ID {}. Response code: {}", storeId, storeStatusResponse.getStatus());
                return storeStatusResponse;
            }
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON", e);
            return Response.serverError().build();
        } catch (Exception e) {
            log.error("Error processing API request", e);
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/status/{storeId}/update-status")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setStoreStatus(@PathParam("storeId") String storeId, String status) {
        try {
            log.info("Attempting to get token...");
            Response tokenResponse = uberAuthService.getToken();
            log.info("Status: {}", status);
            log.info("StoreID: {}", storeId);
            if (tokenResponse == null) {
                log.error("Token response is null");
                return Response.serverError().entity("Token response is null").build();
            }

            log.info("Token response status: {}", tokenResponse.getStatus());

            if (tokenResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                log.error("Failed to get access token. Status code: {}", tokenResponse.getStatus());
                return Response.status(tokenResponse.getStatus()).build();
            }

            String jsonResponse = tokenResponse.readEntity(String.class);
            log.info("Token response entity: {}", jsonResponse);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode authResponse = objectMapper.readTree(jsonResponse);
            String accessToken = authResponse.get("access_token").asText();
            String authorizationHeader = "Bearer " + accessToken;

            Response updateResponse = uberStoreConfig.setStoreStatus(authorizationHeader, storeId, status);

            if (updateResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                log.info("Store Status Changed to: {}", status);
                return Response.ok().build();
            } else {
                log.error("Failed to update store status for ID {}. Response code: {}", storeId, updateResponse.getStatus());
                return Response.status(updateResponse.getStatus()).build();
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