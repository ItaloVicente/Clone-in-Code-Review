package com.couchbase.client.core.cluster;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.couchbase.client.core.BucketClosedException;
import com.couchbase.client.core.message.cluster.CloseBucketRequest;
import com.couchbase.client.core.message.kv.GetRequest;
import com.couchbase.client.core.util.ClusterDependentTest;

public class BucketClosedTest extends ClusterDependentTest {

  @Test(expected = BucketClosedException.class)
  public void shouldFailFastOnRequestOnClosedBucket() {
    cluster().send(new CloseBucketRequest(bucket())).toBlocking().single();

    cluster().send(new GetRequest("someid", bucket())).toBlocking().first();
    fail();
  }

}
