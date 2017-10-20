package com.herolynx.service.monitoring

import javax.ws.rs.Path
import javax.ws.rs.GET

@Path("probes")
class HealthProbesRestService {

    @GET
    @Path("/health")
    fun healthCheck(): String {
        return "alive"
    }

    @GET
    @Path("/readiness")
    fun readinessCheck(): String {
        return "ready"
    }

}