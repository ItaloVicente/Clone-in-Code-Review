package com.couchbase.client.java.cluster;

import com.couchbase.client.java.bucket.BucketType;

public interface BucketSettings {

    String name();

    BucketType type();

    int quota();

    int port();

    String password();

    int replicas();

    boolean indexReplicas();

    boolean enableFlush();

}
