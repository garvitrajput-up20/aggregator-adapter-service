package com.nagarro.config;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/v1/delivery")
@RegisterRestClient(configKey="uber-store")
@Consumes(MediaType.APPLICATION_JSON)
public interface UberStoreConfig {
    @GET
    @Path("/stores")
    Response getStores(@HeaderParam("Authorization") String authorizationHeader);
    @GET
    @Path("/store/{storeId}")
    Response getStoreById(@HeaderParam("Authorization") String authorizationHeader, @PathParam("storeId") String storeId);
    @GET
    @Path("/store/{store_id}/status")
    Response getStoreStatus(@HeaderParam("Authorization") String authorizationHeader, @PathParam("store_id") String storeId);
    @POST
    @Path("/store/{store_id}/update-store-status")
    Response setStoreStatus(@HeaderParam("Authorization") String authorizationHeader, @PathParam("store_id") String storeId, String status);

}