
package com.couchbase.client.core.config;

import com.couchbase.client.core.service.ServiceType;
import com.couchbase.client.core.utils.NetworkAddress;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = DefaultAlternateAddress.class)
public interface AlternateAddress {

    NetworkAddress hostname();

    String rawHostname();

    Map<ServiceType, Integer> services();

    Map<ServiceType, Integer> sslServices();

}
