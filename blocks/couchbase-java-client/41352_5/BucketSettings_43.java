package com.couchbase.client.java.cluster;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import rx.Observable;

@InterfaceStability.Committed
@InterfaceAudience.Public
public interface AsyncClusterManager {

    Observable<ClusterInfo> info();

    Observable<BucketSettings> getBuckets();

    Observable<BucketSettings> getBucket(String name);

    Observable<Boolean> hasBucket(String name);

    Observable<BucketSettings> insertBucket(BucketSettings settings);

    Observable<BucketSettings> updateBucket(BucketSettings settings);

    Observable<Boolean> removeBucket(String name);

}
