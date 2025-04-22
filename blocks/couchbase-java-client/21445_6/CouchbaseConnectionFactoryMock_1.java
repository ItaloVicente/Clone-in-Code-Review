
package com.couchbase.client;

import com.couchbase.client.vbucket.ConfigurationProvider;
import com.couchbase.client.vbucket.ConfigurationProviderMemcacheMock;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import net.spy.memcached.TestConfig;

import static org.junit.Assert.assertTrue;

public class CouchbaseClientMemcachedBucketTest {

  @Test
  public void testMemcacheBucketInitialization() throws IOException {
    boolean success = true;

    try {
      List<URI> baseList = new ArrayList<URI>();
      baseList.add(URI.create("http://"+TestConfig.IPV4_ADDR+":8091/pools"));
      ConfigurationProvider provider = new ConfigurationProviderMemcacheMock();

      CouchbaseConnectionFactoryMock factory;
      factory = new CouchbaseConnectionFactoryMock(
        baseList,
        "memcached-default",
        "",
        provider
      );

      CouchbaseClient client = new CouchbaseClient(factory);
      client.reconfigure(factory.getBucket("memcached-default"));
      client.shutdown();
    } catch(Exception e) {
      success = false;
    }

    assertTrue("Could not verify the init of a memcache bucket", success);
  }

}
