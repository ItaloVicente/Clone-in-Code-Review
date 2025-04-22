package com.couchbase.client.core.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = DefaultNodeInfo.class)
public interface NodeInfo {

    String viewUri();

    String hostname();

    Map<String, Integer> ports();

}
