
package com.couchbase.client.core.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum AlternateAddressType {
    EXTERNAL("external");

    private final String raw;

    AlternateAddressType(final String raw) {
        this.raw = raw;
    }

    @JsonValue
    public String getRaw() {
        return raw;
    }
}
