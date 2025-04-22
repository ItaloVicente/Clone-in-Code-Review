
package com.couchbase.client;

import com.couchbase.client.CouchbaseConnectionFactory;
import com.couchbase.client.CouchbaseClient;
import java.net.URI;
import java.util.Arrays;
import net.spy.memcached.BinaryClientTest;
import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.TestConfig;

public class CouchbaseClientTest extends BinaryClientTest {
  @Override
  protected void initClient() throws Exception {
    initClient(new CouchbaseConnectionFactory(Arrays.asList(URI.create("http://"
        + TestConfig.IPV4_ADDR + ":8091/pools")), "default", ""));
  }

  @Override
  protected String getExpectedVersionSource() {
    if (TestConfig.IPV4_ADDR.equals("127.0.0.1")) {
      return "/127.0.0.1:11210";
    }
    return TestConfig.IPV4_ADDR + ":11210";
  }

  @Override
  protected void initClient(ConnectionFactory cf) throws Exception {
    client = new CouchbaseClient((CouchbaseConnectionFactory) cf);
  }

  @Override
  public void testAvailableServers() {
    try {
      Thread.sleep(10); // Let the client warm up
    } catch (InterruptedException e) {
      fail("Interrupted while client was warming up");
    }
    assert client.getAvailableServers().size() == 1 : "Num servers "
            + client.getAvailableServers().size();
  }

  public void testNumVBuckets() throws Exception {
    if (TestConfig.isMembase()) {
      int num = ((CouchbaseClient)client).getNumVBuckets();

      assertTrue("NumVBuckets has to be a power of two", (num & -num)== num);
    }
  }

  public void testGATTimeout() throws Exception {
    assertNull(client.get("gatkey"));
    assert client.set("gatkey", 1, "gatvalue").get().booleanValue();
    assert client.getAndTouch("gatkey", 2).getValue().equals("gatvalue");
    Thread.sleep(1300);
    assert client.get("gatkey").equals("gatvalue");
    Thread.sleep(2000);
    assertNull(client.getAndTouch("gatkey", 3));
  }

  public void testTouchTimeout() throws Exception {
    assertNull(client.get("touchkey"));
    assert client.set("touchkey", 1, "touchvalue").get().booleanValue();
    assert client.touch("touchkey", 2).get().booleanValue();
    Thread.sleep(1300);
    assert client.get("touchkey").equals("touchvalue");
    Thread.sleep(2000);
    assertFalse(client.touch("touchkey", 3).get().booleanValue());
  }

  public void testSimpleGetl() throws Exception {
    assertNull(client.get("getltest"));
    client.set("getltest", 0, "value");
    ((CouchbaseClient)client).getAndLock("getltest", 3);
    Thread.sleep(2000);
    assert !client.set("getltest", 1, "newvalue").get().booleanValue()
      : "Key wasn't locked for the right amount of time";
    Thread.sleep(2000);
    assert client.set("getltest", 1, "newvalue").get().booleanValue()
      : "Key was locked for too long";
  }

  public void testGetStatsSlabs() throws Exception {
  }

  public void testGetStatsSizes() throws Exception {
  }

  public void testGetStatsCacheDump() throws Exception {
  }

  public void testStupidlyLargeSetAndSizeOverride() throws Exception {
  }

  protected void syncGetTimeoutsInitClient() throws Exception {
    initClient(new CouchbaseConnectionFactory(Arrays.asList(URI
        .create("http://localhost:8091/pools")), "default", "") {
      @Override
      public long getOperationTimeout() {
        return 2;
      }

      @Override
      public int getTimeoutExceptionThreshold() {
        return 1000000;
      }
    });
  }
}
