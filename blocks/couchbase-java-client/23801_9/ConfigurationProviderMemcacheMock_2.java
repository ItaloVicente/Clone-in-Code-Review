package com.couchbase.client;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.spy.memcached.TestConfig;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class CouchbaseConnectionFactoryTest {

  private List<URI> uris;
  private CouchbaseConnectionFactoryBuilder instance;

  @Before
  public void setUp() {
    instance = new CouchbaseConnectionFactoryBuilder();
    uris = Arrays.asList(URI.create("http://" + TestConfig.IPV4_ADDR
      + ":8091/pools"));
  }

  private CouchbaseConnectionFactory buildFactory() throws IOException {
    return instance.buildCouchbaseConnection(uris, "default", "");
  }

  @Test
  public void testPastReconnectThreshold() throws IOException {
    CouchbaseConnectionFactory connFact = buildFactory();

    for(int i=1;i<connFact.getMaxConfigCheck();i++) {
      boolean pastReconnThreshold = connFact.pastReconnThreshold();
      assertFalse(pastReconnThreshold);
    }

    boolean pastReconnThreshold = connFact.pastReconnThreshold();
    assertTrue(pastReconnThreshold);
  }

  @Test
  public void testPastReconnectThresholdWithSleep() throws Exception {
    CouchbaseConnectionFactory connFact = buildFactory();

    for(int i=1;i<=connFact.getMaxConfigCheck()-1;i++) {
      boolean pastReconnThreshold = connFact.pastReconnThreshold();
      assertFalse(pastReconnThreshold);
    }

    Thread.sleep(TimeUnit.SECONDS.toMillis(11));

    for(int i=1;i<=connFact.getMaxConfigCheck()-1;i++) {
      boolean pastReconnThreshold = connFact.pastReconnThreshold();
      assertFalse(pastReconnThreshold);
    }
  }

}
