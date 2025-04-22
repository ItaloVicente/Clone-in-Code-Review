package com.couchbase.client.core.message.internal;

import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.ResponseStatus;

public class HealthCheckResponse extends AbstractCouchbaseResponse {

    private final ServicesHealth servicesHealth;

    public HealthCheckResponse(final ServicesHealth servicesHealth) {
        super(ResponseStatus.SUCCESS, null);
        this.servicesHealth = servicesHealth;
    }

    public ServicesHealth servicesHealth() {
        return servicesHealth;
    }

}
