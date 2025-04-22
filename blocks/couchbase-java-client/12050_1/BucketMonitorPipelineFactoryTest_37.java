
package com.couchbase.client;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import com.couchbase.client.vbucket.ConfigurationException;
import net.spy.memcached.TestConfig;

public class VBucketMembaseClientTest extends TestCase {
  public void testOps() throws Exception {
    CouchbaseClient mc = null;
    try {
      URI base = new URI("http://" + TestConfig.IPV4_ADDR + ":8091/pools");
      mc = new CouchbaseClient(Arrays.asList(base), "default", "");
    } catch (IOException ex) {
      System.err.println(ex.getMessage());
    } catch (ConfigurationException ex) {
      System.err.println(ex.getMessage());
    } catch (URISyntaxException ex) {
      System.err.println(ex.getMessage());
    }

    Integer i;
    for (i = 0; i < 10000; i++) {
      mc.set("test" + i, 0, i.toString());
    }
    mc.set("hello", 0, "world");
    String result = (String) mc.get("hello");
    assert (result.equals("world"));

    for (i = 0; i < 10000; i++) {
      String res = (String) mc.get("test" + i);
      assert (res.equals(i.toString()));
    }

    assert mc.flush().get().booleanValue();
    mc.shutdown(3, TimeUnit.SECONDS);
  }
}
