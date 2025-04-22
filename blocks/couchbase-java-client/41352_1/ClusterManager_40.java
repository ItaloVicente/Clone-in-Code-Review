package com.couchbase.client.java.cluster;

import rx.Observable;

public interface AsyncClusterManager {

    Observable<ClusterInfo> info();

    Observable<BucketSettings> getBuckets();
    Observable<BucketSettings> getBucket(String name);
    Observable<Boolean> hasBucket(String name);
    Observable<BucketSettings> insertBucket(BucketSettings settings);
    Observable<BucketSettings> updateBucket(BucketSettings settings);
    Observable<Boolean> removeBucket(String name);

}
