package com.couchbase.client.core.env;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DynamicCorePropertiesTest {

    @Test
    public void shouldLoadDefaults() {
        CoreProperties props = DynamicCoreProperties.create();
        assertEquals(DefaultCoreProperties.BINARY_ENDPOINTS, props.binaryServiceEndpoints());
        assertEquals(DefaultCoreProperties.IO_POOL_SIZE, props.ioPoolSize());
    }

    @Test
    public void builderShouldTakePrecedence() {
        CoreProperties props = DynamicCoreProperties
            .builder()
            .ioPoolSize(123)
            .build();

        assertEquals(DefaultCoreProperties.BINARY_ENDPOINTS, props.binaryServiceEndpoints());
        assertEquals(123, props.ioPoolSize());
    }

    @Test
    public void sysPropertyShouldTakePrecedence() {
        System.setProperty("com.couchbase.ioPoolSize", "456");

        CoreProperties props = DynamicCoreProperties
            .builder()
            .ioPoolSize(123)
            .build();

        assertEquals(DefaultCoreProperties.BINARY_ENDPOINTS, props.binaryServiceEndpoints());
        assertEquals(456, props.ioPoolSize());

        System.clearProperty("com.couchbase.ioPoolSize");
    }
}
