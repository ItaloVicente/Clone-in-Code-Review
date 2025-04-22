package com.couchbase.client.core.endpoint;

import com.couchbase.client.core.endpoint.kv.KeyValueFeatureHandler;

import java.util.List;

public class ServerFeaturesEvent {

    private final List<ServerFeatures> supportedFeatures;

    public ServerFeaturesEvent(List<ServerFeatures> supportedFeatures) {
        this.supportedFeatures = supportedFeatures;
    }

    public List<ServerFeatures> supportedFeatures() {
        return supportedFeatures;
    }

}
