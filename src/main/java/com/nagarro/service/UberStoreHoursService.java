package com.nagarro.service;

import com.nagarro.config.UberAuthClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Slf4j
@ApplicationScoped
@Path("/hours")
public class UberStoreHoursService {
    @Inject
    @RestClient
    UberAuthClient authClient;

}
