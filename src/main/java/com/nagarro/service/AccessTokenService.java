package com.nagarro.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class AccessTokenService {
    @Inject
    ObjectMapper objectMapper;
    private String extractAccessToken(Response response) throws JsonProcessingException {
        String jsonResponse = response.readEntity(String.class);
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        return jsonNode.get("access_token").asText();
    }

    private Response handleResponse(Response response, String errorMessage) {
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return Response.ok().build();
        } else {
            log.error(errorMessage + ". Response code: {}", response.getStatus());
            return response;
        }
    }
}