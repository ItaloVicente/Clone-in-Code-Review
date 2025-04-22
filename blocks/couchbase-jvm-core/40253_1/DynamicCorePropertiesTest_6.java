package com.couchbase.client.core.env;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class DefaultCoreEnvironmentTest {

    @Test
    public void shouldInitAndShutdownCoreEnvironment() throws Exception {
        CoreEnvironment env = DefaultCoreEnvironment.create();

        assertNotNull(env.ioPool());
        assertNotNull(env.properties());
        assertNotNull(env.scheduler());

        assertTrue(env.shutdown().toBlocking().single());
    }

    @Test
    public void shouldAcceptCustomProperties() throws Exception {
        CoreProperties props = mock(CoreProperties.class);
        CoreEnvironment env = DefaultCoreEnvironment.create(props);
        assertEquals(props, env.properties());
    }
}
