package com.couchbase.client.core.config.parser;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.config.BucketConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class BucketConfigParser {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static BucketConfig parse(final String input) {
        try {
            return OBJECT_MAPPER.readValue(input, BucketConfig.class);
        } catch (IOException e) {
            throw new CouchbaseException("Could not parse configuration", e);
        }
    }
}
