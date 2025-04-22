package com.couchbase.client.core.endpoint;

import com.couchbase.client.core.env.CoreEnvironment;
import org.junit.Test;

import javax.net.ssl.SSLEngine;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SSLEngineFactoryTest {

    @Test(expected = SSLException.class)
    public void shouldFailOnEmptyKeystoreFile() {
        CoreEnvironment environment = mock(CoreEnvironment.class);
        SSLEngineFactory factory = new SSLEngineFactory(environment);
        factory.get();
    }

    @Test(expected = SSLException.class)
    public void shouldFailOnKeystoreFileNotFound() {
        CoreEnvironment environment = mock(CoreEnvironment.class);
        when(environment.sslKeystoreFile()).thenReturn("somefile");

        SSLEngineFactory factory = new SSLEngineFactory(environment);
        factory.get();
    }

    @Test
    public void shouldLoadSSLEngine() {
        CoreEnvironment environment = mock(CoreEnvironment.class);
        when(environment.sslKeystoreFile()).thenReturn(this.getClass().getResource("keystore.jks").getPath());
        when(environment.sslKeystorePassword()).thenReturn("keystore");

        SSLEngineFactory factory = new SSLEngineFactory(environment);
        SSLEngine engine = factory.get();
        assertTrue(engine.getUseClientMode());
    }
}
