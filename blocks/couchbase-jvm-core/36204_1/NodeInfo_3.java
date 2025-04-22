package com.couchbase.client.core.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class DefaultNodeInfo implements NodeInfo {

    private final String viewUri;
    private final String hostname;
    private final Map<String, Integer> ports;

    @JsonCreator
    public DefaultNodeInfo(
        @JsonProperty("couchApiBase") String viewUri,
        @JsonProperty("hostname") String hostname,
        @JsonProperty("ports") Map<String, Integer> ports) {
        this.viewUri = viewUri;
        this.hostname = hostname;
        this.ports = ports;
    }

    @Override
    public String viewUri() {
        return viewUri;
    }

    @Override
    public String hostname() {
        return hostname;
    }

    @Override
    public Map<String, Integer> ports() {
        return ports;
    }
}
