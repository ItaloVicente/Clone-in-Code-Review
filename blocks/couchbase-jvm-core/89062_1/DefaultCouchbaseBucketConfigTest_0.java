
package com.couchbase.client.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Jackson {

    private static final ObjectMapper JACKSON = new ObjectMapper();

    public static ObjectMapper mapper() {
        return JACKSON;
    }

}
