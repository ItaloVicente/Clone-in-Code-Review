package com.couchbase.client.java;

import com.couchbase.client.java.util.TestProperties;
import org.junit.Test;

public class ConnectionTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfBucketIsNull() {
        Cluster cluster = CouchbaseCluster.create(TestProperties.seedNode());
        cluster.openBucket(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfBucketIsEmpty() {
        Cluster cluster = CouchbaseCluster.create(TestProperties.seedNode());
        cluster.openBucket("");
    }
}
