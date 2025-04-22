package com.couchbase.client.core.config;

import com.couchbase.client.core.service.ServiceType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = DefaultPortInfo.class)
public interface PortInfo {

    Map<ServiceType, Integer> ports();

    Map<ServiceType, Integer> sslPorts();

}
