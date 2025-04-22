package com.couchbase.client.java.analytics;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.TranscodingException;
import com.couchbase.client.java.query.AsyncN1qlQueryRow;
import com.couchbase.client.java.transcoder.JacksonTransformers;

import java.io.IOException;

@InterfaceStability.Committed
@InterfaceAudience.Public
public class DefaultAsyncAnalyticsQueryRow implements AsyncAnalyticsQueryRow {

    private static final ObjectMapper OBJECT_MAPPER = JacksonTransformers.MAPPER;

    private JsonObject value = null;
    private final byte[] byteValue;

    public DefaultAsyncAnalyticsQueryRow(byte[] value) {
        this.byteValue = value;
    }

    @Override
    public byte[] byteValue() {
        return byteValue;
    }

    @Override
    public JsonObject value() {
        if (byteValue == null) {
            return null;
        } else if (value == null) {
            try {
                value = OBJECT_MAPPER.readValue(byteValue, JsonObject.class);
                return value;
            } catch (IOException e) {
                throw new TranscodingException("Error deserializing row value from bytes to JsonObject", e);
            }
        } else {
            return value;
        }
    }

    @Override
    public String toString() {
        return byteValue == null ? "null" : value().toString();
    }
}
