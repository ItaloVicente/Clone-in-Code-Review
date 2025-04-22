
package com.couchbase.client.core.endpoint.kv;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorMap implements Comparable<ErrorMap> {

    private final int version;
    private final int revision;
    private final Map<String, ErrorCode> errors;

    @JsonCreator
    public ErrorMap(
        @JsonProperty("version") int version,
        @JsonProperty("revision") int revision,
        @JsonProperty("errors") Map<String, ErrorCode> errors) {
        this.version = version;
        this.revision = revision;
        this.errors = errors;
    }

    @Override
    public int compareTo(ErrorMap o) {
        return 0;
    }

    public int version() {
        return version;
    }

    public int revision() {
        return revision;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ErrorCode {
        private final String name;
        private final String description;
        private final List<ErrorAttribute> attributes;

        @JsonCreator
        public ErrorCode(
            @JsonProperty("name") String name,
            @JsonProperty("desc") String description,
            @JsonProperty("attrs") List<ErrorAttribute> attributes) {
            this.name = name;
            this.description = description;
            this.attributes = attributes;
        }

        public String name() {
            return name;
        }

        public String description() {
            return description;
        }

        public List<ErrorAttribute> attributes() {
            return attributes;
        }

        @Override
        public String toString() {
            return "ErrorCode{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", attributes=" + attributes +
                    '}';
        }
    }

    public enum ErrorAttribute {
        ITEM_ONLY("item-only"),
        INVALID_INPUT("invalid-input"),
        FETCH_CONFIG("fetch-config"),
        CONN_STATE_INVALIDATED("conn-state-invalidated"),
        AUTH("auth"),
        SPECIAL_HANDLING("special-handling"),
        SUPPORT("support"),
        TEMP("temp"),
        INTERNAL("internal"),
        RETRY_NOW("retry-now"),
        RETRY_LATER("retry-later"),
        SUBDOC("subdoc"),
        DCP("dcp");

        private final String raw;

        ErrorAttribute(String raw) {
            this.raw = raw;
        }

        @JsonValue
        public String raw() {
            return raw;
        }
    }

    @Override
    public String toString() {
        return "ErrorMap{" +
                "version=" + version +
                ", revision=" + revision +
                ", errors=" + errors +
                '}';
    }
}
