
package com.couchbase.client;

import com.couchbase.client.clustermanager.BucketType;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.spy.memcached.TestConfig;
import net.spy.memcached.internal.OperationFuture;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class FlushTest {

  private static String NOFLUSH_BUCKET = "noflush";
  private static CouchbaseClient defaultClient;
  private static CouchbaseClient saslClient;

  @BeforeClass
  public static void prepareBuckets() throws Exception {
    BucketTool bucketTool = new BucketTool();
    bucketTool.deleteAllBuckets();
    bucketTool.createDefaultBucket(BucketType.COUCHBASE, 256, 0, true);
    bucketTool.createSaslBucket(NOFLUSH_BUCKET, BucketType.COUCHBASE, 256, 0,
      false);

    BucketTool.FunctionCallback callback = new BucketTool.FunctionCallback() {
      @Override
      public void callback() throws Exception {
        List<URI> uris = new ArrayList<URI>();
        uris.add(URI.create("http://" + TestConfig.IPV4_ADDR + ":8091/pools"));
        defaultClient = new CouchbaseClient(uris, "default", "");
        saslClient = new CouchbaseClient(uris,
          NOFLUSH_BUCKET, NOFLUSH_BUCKET);
      }

      @Override
      public String success(long elapsedTime) {
        return "Client Initialization took " + elapsedTime + "ms";
      }
    };
    bucketTool.poll(callback);
    bucketTool.waitForWarmup(defaultClient);
    bucketTool.waitForWarmup(saslClient);
  }

  @Ignore("Disabled for JCBC-173/MB-7381.") @Test
  public void testFlushWhenEnabled() throws Exception {
    List<URI> uris = new ArrayList<URI>();
    uris.add(URI.create("http://" + TestConfig.IPV4_ADDR + ":8091/pools"));
    CouchbaseClient client = new CouchbaseClient(uris, "default", "");

    for(int i = 0; i <= 10; i++) {
      client.set("doc:"+ i, 0, "sampledocument").get();
    }

    for(int i = 0; i <= 10; i++) {
      assertEquals("sampledocument", (String) client.get("doc:" + i));
    }

    Boolean response = client.flush().get();
    assertTrue(response);

    for(int i = 0; i <= 10; i++) {
      assertNull(client.get("doc:" + i));
    }

    client.shutdown();
  }

  @Ignore("Disabled for JCBC-173/MB-7381.") @Test
  public void testFlushWhenDisabled() throws Exception {
    List<URI> uris = new ArrayList<URI>();
    uris.add(URI.create("http://" + TestConfig.IPV4_ADDR + ":8091/pools"));
    CouchbaseClient client = new CouchbaseClient(
      uris, NOFLUSH_BUCKET, NOFLUSH_BUCKET);

    for(int i = 0; i <= 10; i++) {
     client.set("doc:"+ i, 0, "sampledocument").get();
    }

    for(int i = 0; i <= 10; i++) {
      assertEquals("sampledocument", (String) client.get("doc:" + i));
    }

    Boolean response = client.flush().get();
    assertFalse(response);

    for(int i = 0; i <= 10; i++) {
      assertEquals("sampledocument", (String) client.get("doc:" + i));
    }

    client.shutdown();
  }

}
