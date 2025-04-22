package com.couchbase.client.java;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.couchbase.client.java.error.BucketClosedException;
import com.couchbase.client.java.util.ClusterDependentTest;
public class BucketClosingTest extends ClusterDependentTest {

  @Test(expected = BucketClosedException.class)
  public void shouldPreventSyncCloseBucketThenSyncGet() {
    Bucket bucket = cluster().openBucket(bucketName(), password());
    bucket.close();

    bucket.get("someid");
    fail();
  }

  @Test(expected = BucketClosedException.class)
  public void shouldPreventSyncCloseBucketThenAsyncGet() {
    Bucket bucket = cluster().openBucket(bucketName(), password());
    bucket.close();

    bucket.async().get("someid").toBlocking().first();
    fail();
  }

  @Test(expected = BucketClosedException.class)
  public void shouldPreventAsyncClosedBucketThenGet() throws InterruptedException {
    Bucket bucket = cluster().openBucket(bucketName(), password());
    bucket.async().close().toBlocking().single();

    bucket.get("someid");
    fail();
  }

}
