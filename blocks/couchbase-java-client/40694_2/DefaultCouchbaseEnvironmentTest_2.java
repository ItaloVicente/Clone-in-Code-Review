package com.couchbase.client.java.env;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DefaultCouchbaseEnvironmentTest {

    @Test
    public void shouldApplyDefaultSettings() {
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.create();

        assertEquals(DefaultCouchbaseEnvironment.BINARY_ENDPOINTS, env.binaryServiceEndpoints());
        assertNotNull(env.ioPool());
    }

    @Test
    public void shouldOverrideSettings() {
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.
            builder()
            .binaryServiceEndpoints(5)
            .build();

        assertEquals(5, env.binaryServiceEndpoints());
        assertNotNull(env.ioPool());
    }

    @Test
    public void systemPropertiesShouldTakePrecedence() {
        System.setProperty("com.couchbase.binaryServiceEndpoints", "10");
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.
            builder()
            .binaryServiceEndpoints(5)
            .build();

        assertEquals(10, env.binaryServiceEndpoints());
        System.clearProperty("com.couchbase.binaryServiceEndpoints");
    }
}
