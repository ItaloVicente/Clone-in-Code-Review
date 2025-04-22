
package com.couchbase.client.core.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum BucketCapabilities {

    CBHELLO("cbhello"),
    TOUCH("touch"),
    COUCHAPI("couchapi"),
    CCCP("cccp"),
    XDCR_CHECKPOINTING("xdcrCheckpointing"),
    NODES_EXT("nodesExt"),
    DCP("dcp");

    private final String raw;

    BucketCapabilities(String raw) {
        this.raw = raw;
    }

    @JsonValue
    public String getRaw() {
        return raw;
    }
}
